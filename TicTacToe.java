import java.util.Scanner;
import java.util.ArrayList;

public class TicTacToe {

    public static void main(String[] args) {
        TicTacGame GameObject ;
        Scanner UserInput = new Scanner(System.in);
        boolean GameLoop = true;
        TicTacGame.BoardState CurrentGameState = TicTacGame.BoardState.INPROGRESS;
        if (args.length == 0)
        {
            GameObject = new TicTacGame();
        }
        else if (args.length == 2)
        {
            GameObject = new TicTacGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
        else
        {
            GameObject = new TicTacGame(Integer.parseInt(args[0]), Integer.parseInt(args[1]) , Integer.parseInt(args[2]));
        }
        while (GameLoop)
        {
            GameObject.printGrid();
            int currentPlayer = GameObject.GetCurrentPlayer();
            System.out.println("Player "+ currentPlayer +"'s Turn, Please choose an Empty cell: ");
            int CellNumber = UserInput.nextInt();
            CurrentGameState = GameObject.play(CellNumber);

            if (CurrentGameState != TicTacGame.BoardState.INPROGRESS)
                GameLoop = false;

        }

        if (CurrentGameState == TicTacGame.BoardState.WIN_X) {
            GameObject.printGrid();
            System.out.println("Player 1 Wins");
        }
        else if (CurrentGameState == TicTacGame.BoardState.WIN_O) {
            GameObject.printGrid();
            System.out.println("Player 2 Wins");
        }
        else if (CurrentGameState == TicTacGame.BoardState.DRAW) {
            GameObject.printGrid();
            System.out.println("DRAW");
        }


    }


}
class TicTacGame
{
    public enum CellState
    {
        O,
        X,
        Empty
    }

    public enum BoardState
    {
        INPROGRESS,
        DRAW,
        WIN_X,
        WIN_O
    }
    private int MovesCount;
    private BoardState state = BoardState.INPROGRESS;
    private int currentPlayer;
    private int n;
    private int m;
    private int k;
    private CellState[] Grid;
    public TicTacGame()
    {
        this.currentPlayer = 1 ;
        this.n=3;
        this.m=3;
        this.k=3;
        this.MovesCount = this.n * this.m;
        this.InitializeBoard();

    }
    public TicTacGame(int row,int columns)
    {
        this.currentPlayer = 1 ;
        this.n=row;
        this.m = columns;
        this.k=3;
        this.MovesCount = this.n * this.m;
        this.InitializeBoard();
    }
    public TicTacGame(int row,int columns, int WinCondition)
    {
        this.currentPlayer = 1 ;
        this.n = row;
        this.m = columns;
        this.k = WinCondition;
        this.MovesCount = this.n * this.m;
        this.InitializeBoard();
    }
    private void InitializeBoard()
    {
        this.Grid = new CellState[this.n*this.m];
        for (int i = 0 ; i < this.n ; i++){
            for (int j=0 ; j < this.m ; j++){
                this.Grid[i * this.m + j] = CellState.Empty;


            }
        }

    }
    public void printGrid()
    {
        for (int i = 0 ; i < this.n ; i++){
            String temp = "";
            for (int j=0 ; j < this.m ; j++){

                if (this.Grid[i * this.m + j] == CellState.Empty)
                {
                    temp += ("| " + (i * this.m + j) + " |");

                }
                else if (this.Grid[i * this.m + j] == CellState.O)
                {
                    temp += ("| O |");
                }
                else
                {
                    temp += ("| X |");
                }

            }
            System.out.println(temp);
        }

    }
    public BoardState UpperDiagonal(CellState [] temp)
    {
        ArrayList<CellState> UpperDiagonalCheck = new ArrayList<>();

        for (int k = 0; k < this.n; k++)
        {
            int i = k;
            int j = 0;

            while (!(i < 0 || i >= this.m || j >= this.m || j < 0))
            {
                UpperDiagonalCheck.add(temp[i*this.m+j]);
                i++;
                j++;
            }
            if(UpperDiagonalCheck.size() < this.k || this.checker(UpperDiagonalCheck) == BoardState.INPROGRESS) {
                UpperDiagonalCheck.clear();
            }
            else
            {
                return this.checker(UpperDiagonalCheck);
            }
        }

        for (int k = 1 ; k < this.m ; k++)
        {
            int i = 0;
            int j = k;

            while (!(i < 0 || i >= this.n || j >= this.m || j < 0))
            {
                UpperDiagonalCheck.add(temp[i*this.m+j]);
                i++;
                j++;
            }
            if(UpperDiagonalCheck.size() < this.k || this.checker(UpperDiagonalCheck) == BoardState.INPROGRESS) {
                UpperDiagonalCheck.clear();
            }
            else
            {
                return this.checker(UpperDiagonalCheck);
            }

        }
        return BoardState.INPROGRESS;
    }

    public BoardState LowerDiagonal(CellState [] temp) {
        ArrayList<CellState> LowerDiagonalCheck = new ArrayList<>();
        for (int k = 0; k < this.n; k++) {
            int i = k;
            int j = 0;

            while (!(i < 0 || i >= this.n || j >= this.m || j < 0)) {
                LowerDiagonalCheck.add(temp[i*this.m+j]);
                i--;
                j++;
            }
            if(LowerDiagonalCheck.size() < this.k || this.checker(LowerDiagonalCheck) == BoardState.INPROGRESS) {
                LowerDiagonalCheck.clear();
            }
            else
            {
                return this.checker(LowerDiagonalCheck);
            }

        }

        for (int k = 1; k < this.m; k++) {
            int i = this.n - 1;
            int j = k;

            while (!(i < 0 || i >= this.n || j >= this.m || j < 0)) {
                LowerDiagonalCheck.add(temp[i*this.m+j]);
                i--;
                j++;
            }

            if(LowerDiagonalCheck.size() < this.k || this.checker(LowerDiagonalCheck) == BoardState.INPROGRESS) {
                LowerDiagonalCheck.clear();
            }
            else
            {
                return this.checker(LowerDiagonalCheck);
            }

        }
        return BoardState.INPROGRESS;
    }

    public BoardState Row(CellState []Arr)
    {
        ArrayList<CellState> RowCheck = new ArrayList<>();
        for (int i = 0 ; i < this.n; i++) {
            for (int j = 0; j < this.m; j++){
                RowCheck.add(Arr[i * this.m + j]);
            }
            if(this.checker(RowCheck) == BoardState.INPROGRESS) {
                RowCheck.clear();
            }
            else
            {
                return this.checker(RowCheck);
            }
        }
        return BoardState.INPROGRESS;
    }

    public BoardState Col(CellState []Arr)
    {
        ArrayList<CellState> ColCheck = new ArrayList<>();
        for (int i = 0 ; i < this.n; i++) {
            for (int j = 0; j < this.m; j++){
                ColCheck.add(Arr[j * this.m + i]);

            }
            if(this.checker(ColCheck) == BoardState.INPROGRESS) {
                ColCheck.clear();
            }
            else
            {
                return this.checker(ColCheck);
            }

        }
        return BoardState.INPROGRESS;
    }
    private BoardState checker(ArrayList<CellState>temp)
    {
        for(int i = 0; i<temp.size() - this.k+1;i++) {
            CellState tempo = temp.get(i);
            for(int j =1 ; j<this.k ; j++)
            {
                if(temp.get(i+j) != tempo || tempo == CellState.Empty)
                {
                    break;
                }
                else if (j == this.k - 1)
                {
                    if (tempo == CellState.O)
                        return BoardState.WIN_O;
                    else
                        return BoardState.WIN_X;
                }
            }
        }
        return BoardState.INPROGRESS;
    }
    public int GetCurrentPlayer()
    {
        return this.currentPlayer;
    }
    private void nextPlayer()
    {
        if(this.currentPlayer == 1)
        {
            this.currentPlayer = 2;
        }
        else
        {
            this.currentPlayer = 1;
        }
    }

    public BoardState play(int x) {
        if (this.Grid[x] == CellState.Empty) {
            if (this.currentPlayer == 1) {
                this.Grid[x] = CellState.X;
                this.MovesCount -=1;
            } else {
                this.Grid[x] = CellState.O;
                this.MovesCount -=1;
            }
        }
        if (this.state == BoardState.INPROGRESS)
            this.state = this.Row(this.Grid);
        if (this.state == BoardState.INPROGRESS)
            this.state = this.Col(this.Grid);
        if (this.state == BoardState.INPROGRESS)
            this.state = this.LowerDiagonal(this.Grid);
        if (this.state == BoardState.INPROGRESS)
            this.state = this.UpperDiagonal(this.Grid);

        if (this.state == BoardState.INPROGRESS && this.MovesCount == 0)
            this.state = BoardState.DRAW;

        else if (this.state == BoardState.INPROGRESS)
            this.nextPlayer();

        return this.state;

    }
}