import net.sf.javabdd.BDD;
import net.sf.javabdd.JFactory;
import net.sf.javabdd.BDDFactory;

public class Solver {

    private int size;
    private int[][] board;

    private BDD True;
    private BDD False;
    private BDD solutionBdd;
    private BDD[][] bddBoard;

    public void initializeBoard(int size)
    {
        // Init factory and variables
        BDDFactory factory = JFactory.init(2000000, 200000);
        factory.setVarNum(size * size);

        // Init size and board
        this.size = size;
        this.board = new int[size][size];
        this.bddBoard = new BDD[size][size];

        // Init bdds
        this.True = factory.one();
        this.False = factory.zero();
        this.solutionBdd = True;

        // Assign variable bdds in the bddBoard
        for (int col = 0; col < size; col++)
        {
            for (int row = 0; row < size; row++)
            {
                bddBoard[col][row] = factory.ithVar(row * size + col);
            }
        }

        // Setup rules
        createRules();

        // Update board (Has to be done for size = 6)
        updateBoard();
    }

    private void createRules()
    {
        // Creating every rule for the solutionBdd
        nQueenRule();
        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                rowRule(col, row);
                diaRule(col, row);
                columnRule(col, row);
            }
        }
    }

    private void nQueenRule()
    {
        // Rule enforcing exactly one queen on every row for a valid solution
        for (int col = 0; col < size; col++)
        {
            BDD orBdd = False;
            for (int row = 0; row < size; row++)
            {
                orBdd = orBdd.apply(bddBoard[col][row], BDDFactory.or);
            }
            solutionBdd = solutionBdd.apply(orBdd, BDDFactory.and);
        }
    }

    private void rowRule(final int col, final int row)
    {
        // BDD looking at the current cell (row, col) if true -> false if false -> true
        BDD orBdd = False;
        orBdd = orBdd.apply(bddBoard[col][row].not(), BDDFactory.or);

        // Setting up BDD for rest of the cells in the row using "not-and" to ensure all are false
        BDD andBdd = True;
        for (int tempCol = 0; tempCol < size; tempCol++)
        {
            if (col == tempCol)
                continue; // Since we've already created the orBdd
            andBdd = andBdd.apply(bddBoard[tempCol][row].not(), BDDFactory.and);
        }

        // Merge the two above bdds with "or" to have one bdd representing the row rule for current cell
        orBdd = orBdd.apply(andBdd, BDDFactory.or);

        // Add the rule for the current cell to the solutionBdd
        solutionBdd = solutionBdd.apply(orBdd, BDDFactory.and);
    }

    private void columnRule(final int col, final int row)
    {
        // Follow the same template as rowRule()
        BDD orBdd = False;
        orBdd = orBdd.apply(bddBoard[col][row].not(), BDDFactory.or);
        BDD andBdd = True;
        for (int tempRow = 0; tempRow < size; tempRow++)
        {
            if (row == tempRow)
                continue;
            andBdd = andBdd.apply(bddBoard[col][tempRow].not(), BDDFactory.and);
        }
        orBdd = orBdd.apply(andBdd, BDDFactory.or);
        solutionBdd = solutionBdd.apply(orBdd, BDDFactory.and);
    }

    private void diaRule(final int col, final int row)
    {
        // BDD looking at the current cell (row, col) if true -> false if false -> true
        BDD orBdd = False;
        orBdd = orBdd.apply(bddBoard[col][row].not(), BDDFactory.or);

        //Find the relevant cells and make a rule, that they have to be the opposite of the current cell
        BDD andBdd = True;
        int tempCol = col;
        int tempRow = row;

        // go left-up first
        while (true)
        {
            tempCol--;
            tempRow--;
            if (tempCol < 0 || tempRow < 0)
                break;
            andBdd = andBdd.apply(bddBoard[tempCol][tempRow].not(), BDDFactory.and);
        }

        //reset to original values:
        tempCol = col;
        tempRow = row;

        // go left-down
        while (true)
        {
            tempCol--;
            tempRow++;
            if (tempCol < 0 || tempRow > size - 1)
                break;
            andBdd = andBdd.apply(bddBoard[tempCol][tempRow].not(), BDDFactory.and);
        }

        //reset to original values:
        tempCol = col;
        tempRow = row;

        // go right-up
        while (true)
        {
            tempCol++;
            tempRow--;
            if (tempCol > size - 1 || tempRow < 0)
                break;
            andBdd = andBdd.apply(bddBoard[tempCol][tempRow].not(), BDDFactory.and);
        }

        //reset to original values:
        tempCol = col;
        tempRow = row;

        // go right-down
        while (true)
        {
            tempCol++;
            tempRow++;
            if (tempCol > size - 1 || tempRow > size - 1)
                break;
            andBdd = andBdd.apply(bddBoard[tempCol][tempRow].not(), BDDFactory.and);
        }

        // Merge the two above bdds with "or" to have one bdd representing the row rule for current cell
        orBdd = orBdd.apply(andBdd, BDDFactory.or);

        // Add the rule for the current cell to the solutionBdd
        solutionBdd = solutionBdd.apply(orBdd, BDDFactory.and);
    }

    private void updateBoard()
    {
        // Checking for which cells placing a queen would be an illegal move
        // ignoring cell already containing a queen
        for (int col = 0; col < size; col++)
        {
            for (int row = 0; row < size; row++)
            {
                if (isInvalidCell(col, row) && board[col][row] == 0)
                    board[col][row] = -1;
            }
        }
    }

    private boolean isInvalidCell(final int col, final int row)
    {
        // Add a queen at (col, row) in the bdd
        BDD temp = solutionBdd.restrict(bddBoard[col][row]);
        // Then check if the recently placed queen made the bdd false
        return temp.isZero();
    }

    public void insertQueen(final int col, final int row)
    {
        // If queen is present in cell or if cell is invalid -> return
        if (board[col][row] == -1 || board[col][row] == 1)
            return;

        // Else add a queen to the board at the location (col, row)
        board[col][row] = 1;

        // Then add the queen to the solutionBdd by restricting the specific "ithvar" from the bddBoard
        solutionBdd = solutionBdd.restrict(bddBoard[col][row]);

        // Finally update the board according to the updated solutionBdd
        updateBoard();
        if (solutionBdd.pathCount() == 1)
            placeRemaining();
    }

    private void placeRemaining()
    {
        for (int col = 0; col < size; col++)
        {
            for (int row = 0; row < size; row++)
            {
                if (board[col][row] == 0)
                    board[col][row] = 1;
            }
        }
    }

    public int[][] getBoard()
    {
        return board;
    }

}
