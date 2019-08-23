package sample;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameScreen {
    private char[][] symbols;
    private int numX;
    private int numO;

    private int whoPlay;    /* * 0-> FIRST PLAYER * 1-> SECOND PLAYER * */
    private static int difficult ;
    private static int typePlay; //   0->Nothing      1->play vs play     2->play vs comp
    public String IASymbol;
    public String enemySymbol;

    private static Scanner scanner = new Scanner(System.in);

    /*  CONSTRUCTURE*/
    public GameScreen() {
        this.symbols = new char[3][3];
        this.resetGame();

    }

    /*      RESET VARIABLES AND SET " " TO ARRAY OF SYMBOLS     */
    public void resetGame() {
        numX = 0;
        numO = 0;
        whoPlay = 0; // I start first
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                this.symbols[i][j] = " ".charAt(0);
        }
    }



    /*      MAKE A MOVE EASY (RANDOM)   */
    public int makeMoveEasy() {
        /*1 to 3*/
        int r, c;

        Random random = new Random();
        /*
         * (Max - Min)
         * */
        while (true) {
            r = random.nextInt(3);
            c = random.nextInt(3);
            System.out.println("r: "+r + "  c: "+ c );
            if (this.isEmpty(c, r))
                break;
        }
        this.setSymbols(c, r, true);
        return c*10+r;
    }

    /*      MAKE A MOVE EASY (RANDOM)   */
    public int makeMoveMedium() {
        int winMove=this.winMove();

        if (winMove != -1) // if can't win
            return winMove;

        int enemyWinMove = this.enemyWinMove();
        if (enemyWinMove != -1) // if can't combat the enemy{
            return enemyWinMove;

        int moveEasy=this.makeMoveEasy();//random move
        return moveEasy;


    }

    public Move makeMoveHard(char[][] board, String player) {
        Move minimaxResult = new Move();
        int bestResult = -11;
        /*
         * 0-> Y, i
         * 1-> X, j
         * */
        int[][] availableSpaces= emptyIndexies(board);
        if(availableSpaces.length == 8 && board[1][1] == " ".charAt(0)){
            minimaxResult.setSimulationResult(10);
            int[] aux= winning(board, IASymbol);
            minimaxResult.setxCoordinates(1);
            minimaxResult.setyCoordinates(1);
            return minimaxResult;
        }

        if(winning(board, IASymbol) != null ){
            minimaxResult.setSimulationResult(10);
            int[] aux= winning(board, IASymbol);
            minimaxResult.setxCoordinates(aux[1]);
            minimaxResult.setyCoordinates(aux[0]);
            return minimaxResult;
        }else if(winning(board, enemySymbol) != null){
            minimaxResult.setSimulationResult(-10);
            int[] aux= winning(board, enemySymbol);
            minimaxResult.setxCoordinates(aux[1]);
            minimaxResult.setyCoordinates(aux[0]);
            return minimaxResult;
        }else if(availableSpaces.length == 0){
            minimaxResult.setSimulationResult(0);
            minimaxResult.setIsDraw(true);
            return minimaxResult;
        }
        List<Move> moves = new ArrayList<>();
        for(int i=0; i<availableSpaces.length; i++){

            Move currentResult = new Move();
            currentResult.setxCoordinates(availableSpaces[i][1]);
            currentResult.setyCoordinates(availableSpaces[i][0]);

            // set the empty spot to the current player
            board[availableSpaces[i][0]][availableSpaces[i][1]] = player.charAt(0);

            //if collect the score resulted from calling minimax on the opponent of the current player
            String aux="X";
            if(player.equals(IASymbol)){
                aux= enemySymbol;
            }else if(player.equals(enemySymbol)){
                aux= IASymbol;
            }
            Move currentResultAux= makeMoveHard(board, aux);
            currentResult.setSimulationResult(currentResultAux.getSimulationResult());

            moves.add(currentResult);
            board[availableSpaces[i][0]][availableSpaces[i][1]] = " ".charAt(0);
        }

        int bestMove=0;
        if(player.equals(IASymbol)){
            int bestScore=-10000;
            for(int i=0; i<moves.size(); i++){

                if(moves.get(i).getSimulationResult() > bestScore){
                    bestScore =  moves.get(i).getSimulationResult();
                    bestMove = i;
                }
            }
        }else{
            int bestScore= 10000;
            for(int i=0; i<moves.size(); i++){
                if(moves.get(i).getSimulationResult() < bestScore){
                    bestScore =  moves.get(i).getSimulationResult();
                    bestMove = i;
                }
            }

        }

        return moves.get(bestMove);
    }

    /* return -1 if can't win */
    public int winMove() {
        String winV, winH, mySymbol;
        int r = -1;
        int c = -1;
        boolean set = false;
        if (whoPlay == 0)
            mySymbol = "X";
        else if (whoPlay == 1)
            mySymbol = "O";
        else
            mySymbol = "error";


        for (int i = 0; i < 3; i++) {
            /*horizontal*/
            winH = "" + this.symbols[i][0] + "" + this.symbols[i][1] + "" + this.symbols[i][2];
            /*vertical*/
            winV = "" + this.symbols[0][i] + "" + this.symbols[1][i] + "" + this.symbols[2][i];

            /*  HORIZONTAL  */
            if (winH.equals(" " + mySymbol + mySymbol)) {
                c = i;
                r = 0;
                set = true;
                break;
            } else if (winH.equals(mySymbol + " " + mySymbol)) {
                c = i;
                r = 1;
                set = true;
                break;
            } else if (winH.equals(mySymbol + mySymbol + " ")) {
                c = i;
                r = 2;
                set = true;
                break;
                /*  VERTICAL  */
            } else if (winV.equals(" " + mySymbol + mySymbol)) {
                c = 0;
                r = i;
                set = true;
                break;
                /*  "X X"  */
            } else if (winV.equals(mySymbol + " " + mySymbol)) {
                c = 1;
                r = i;
                set = true;
                break;
                /*  "XX "  */
            } else if (winV.equals(mySymbol + mySymbol + " ")) {
                c = 2;
                r = i;
                set = true;
                break;
            }
        }
        /*Diagonal*/
        String winD = "" + this.symbols[0][0] + "" + this.symbols[1][1] + "" + this.symbols[2][2] + "" + this.symbols[0][2] + "" + this.symbols[1][1] + "" + this.symbols[2][0];

        if (winD.substring(0, 3).equals(mySymbol + mySymbol + " ")) {
            r = 2;
            c = 2;
            set = true;
        } else if (winD.substring(0, 3).equals(mySymbol + " " + mySymbol)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(0, 3).equals(" " + mySymbol + mySymbol)) {
            r = 0;
            c = 0;
            set = true;
        } else if (winD.substring(3, 6).equals(mySymbol + mySymbol + " ")) {
            r = 0;
            c = 2;
            set = true;
        } else if (winD.substring(3, 6).equals(mySymbol + " " + mySymbol)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(3, 6).equals(" " + mySymbol + mySymbol)) {
            r = 2;
            c = 0;
            set = true;
        }

        this.setSymbols(c, r, set);
        if(set)
            return c*10+r;
        else
            return -1;
    }

    public int enemyWinMove() {
        String winV, winH, enemySymbol;
        int r = -1;
        int c = -1;
        boolean set = false;
        if (whoPlay == 0)
            enemySymbol = "O";
        else if (whoPlay == 1)
            enemySymbol = "X";
        else
            enemySymbol = "error";


        for (int i = 0; i < 3; i++) {
            /*horizontal*/
            winH = "" + this.symbols[i][0] + "" + this.symbols[i][1] + "" + this.symbols[i][2];
            /*vertical*/
            winV = "" + this.symbols[0][i] + "" + this.symbols[1][i] + "" + this.symbols[2][i];

            /*  HORIZONTAL  */
            if (winH.equals(" " + enemySymbol + enemySymbol)) {
                c = i;
                r = 0;
                set = true;
                break;
            } else if (winH.equals(enemySymbol + " " + enemySymbol)) {
                c = i;
                r = 1;
                set = true;
                break;
            } else if (winH.equals(enemySymbol + enemySymbol + " ")) {
                c = i;
                r = 2;
                set = true;
                break;
                /*  VERTICAL  */
            } else if (winV.equals(" " + enemySymbol + enemySymbol)) {
                c = 0;
                r = i;
                set = true;
                break;
                /*  "X X"  */
            } else if (winV.equals(enemySymbol + " " + enemySymbol)) {
                c = 1;
                r = i;
                set = true;
                break;
                /*  "XX "  */
            } else if (winV.equals(enemySymbol + enemySymbol + " ")) {
                c = 2;
                r = i;
                set = true;
                break;
            }
        }
        /*Diagonal*/
        String winD = "" + this.symbols[0][0] + "" + this.symbols[1][1] + "" + this.symbols[2][2] + "" + this.symbols[0][2] + "" + this.symbols[1][1] + "" + this.symbols[2][0];

        if (winD.substring(0, 3).equals(enemySymbol + enemySymbol + " ")) {
            r = 2;
            c = 2;
            set = true;
        } else if (winD.substring(0, 3).equals(enemySymbol + " " + enemySymbol)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(0, 3).equals(" " + enemySymbol + enemySymbol)) {
            r = 0;
            c = 0;
            set = true;
        } else if (winD.substring(3, 6).equals(enemySymbol + enemySymbol + " ")) {
            r = 0;
            c = 2;
            set = true;
        } else if (winD.substring(3, 6).equals(enemySymbol + " " + enemySymbol)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(3, 6).equals(" " + enemySymbol + enemySymbol)) {
            r = 2;
            c = 0;
            set = true;
        }

        this.setSymbols(c, r, set);
        if(set)
            return c*10+r;
        return -1;
    }

    public int[] winning(char[][] board, String symbolPlay) {
        String winV, winH;
        int r = -1;
        int c = -1;
        boolean set = false;


        for (int i = 0; i < 3; i++) {
            /*horizontal*/
            winH = "" + this.symbols[i][0] + "" + this.symbols[i][1] + "" + this.symbols[i][2];
            /*vertical*/
            winV = "" + this.symbols[0][i] + "" + this.symbols[1][i] + "" + this.symbols[2][i];

            /*  HORIZONTAL  */
            if (winH.equals(" " + symbolPlay + symbolPlay)) {
                c = i;
                r = 0;
                set = true;
                break;
            } else if (winH.equals(symbolPlay + " " + symbolPlay)) {
                c = i;
                r = 1;
                set = true;
                break;
            } else if (winH.equals(symbolPlay + symbolPlay + " ")) {
                c = i;
                r = 2;
                set = true;
                break;
                /*  VERTICAL  */
            } else if (winV.equals(" " + symbolPlay + symbolPlay)) {
                c = 0;
                r = i;
                set = true;
                break;
                /*  "X X"  */
            } else if (winV.equals(symbolPlay + " " + symbolPlay)) {
                c = 1;
                r = i;
                set = true;
                break;
                /*  "XX "  */
            } else if (winV.equals(symbolPlay + symbolPlay + " ")) {
                c = 2;
                r = i;
                set = true;
                break;
            }
        }
        /*Diagonal*/
        String winD = "" + this.symbols[0][0] + "" + this.symbols[1][1] + "" + this.symbols[2][2] + "" + this.symbols[0][2] + "" + this.symbols[1][1] + "" + this.symbols[2][0];

        if (winD.substring(0, 3).equals(symbolPlay + symbolPlay + " ")) {
            r = 2;
            c = 2;
            set = true;
        } else if (winD.substring(0, 3).equals(symbolPlay + " " + symbolPlay)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(0, 3).equals(" " + symbolPlay + symbolPlay)) {
            r = 0;
            c = 0;
            set = true;
        } else if (winD.substring(3, 6).equals(symbolPlay + symbolPlay + " ")) {
            r = 0;
            c = 2;
            set = true;
        } else if (winD.substring(3, 6).equals(symbolPlay + " " + symbolPlay)) {
            r = 1;
            c = 1;
            set = true;
        } else if (winD.substring(3, 6).equals(" " + symbolPlay + symbolPlay)) {
            r = 2;
            c = 0;
            set = true;
        }
        if(r!=-1 && c!= -1){
            int[] coord = new int[2];
            coord[1]=r;
            coord[0]=c;
            return coord;
        }else
            return null;
    }

    public int[][] emptyIndexies(char[][] board) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == " ".charAt(0)) {
                    count++;
                }

            }
        }
        int[][] array = new int[count][2];
        count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == " ".charAt(0)) {
                    array[count][0] = i;
                    array[count][1] = j;
                    count++;
                }

            }
        }
        return array;
    }

    /*      CHECK IF IS EMPTY   (X,Y)
     *       RETURN: TRUE / FALSE    */
    public boolean isEmpty(int c, int r) {
        return this.symbols[c][r] == " ".charAt(0);
    }

    public void setSymbols(int c, int r, boolean aux) {
        if (aux) {
            if (whoPlay == 0) {
                this.symbols[c][r] = "X".charAt(0);
                numX++;
            } else if (whoPlay == 1) {
                this.symbols[c][r] = "O".charAt(0);
                numO++;
            }
            this.changeWhoPlay();//change turn
        }
    }

    /*      CHECK IF GAME IS FINISHED AND PRINT
    *       0-> not finished
    *       1-> tie
    *       2-> X wins
    *       3-> O wins
    * */
    public int gameState() {
        boolean xWins = false;
        boolean oWins = false;
        String winV, winH, winD;
        /*Diagonal*/
        winD = "" + this.symbols[0][0] + "" + this.symbols[1][1] + "" + this.symbols[2][2] + "" + this.symbols[0][2] + "" + this.symbols[1][1] + "" + this.symbols[2][0];

        for (int i = 0; i < 3; i++) {
            /*vertical*/
            winH = "" + this.symbols[i][0] + "" + this.symbols[i][1] + "" + this.symbols[i][2];
            /*horizontal*/
            winV = "" + this.symbols[0][i] + "" + this.symbols[1][i] + "" + this.symbols[2][i];

            if (winV.equals("XXX") || winH.equals("XXX") || winD.substring(0, 3).equals("XXX") || winD.substring(3, 6).equals("XXX"))
                xWins = true;
            else if (winV.equals("OOO") || winH.equals("OOO") || winD.substring(0, 3).equals("OOO") || winD.substring(3, 6).equals("OOO"))
                oWins = true;

        }

        /* Print game state */
        if (xWins && oWins || (Math.abs(numX - numO) > 1))
            return -1;
        else if (xWins) {
            System.out.println("X wins");
            return 2;
        }
        else if (oWins) {
            System.out.println("O wins");
            return 3;
        }
        else if ((numO + numX) != 9) {
            return 0;
        } else{
            System.out.println("Draw");
            return 1;
        }


    }

    /*      CHANGE THE VALUE OF WHOPLAY VARIABLE*/
    public void changeWhoPlay() {
        if (whoPlay == 0)
            whoPlay = 1;
        else if (whoPlay == 1)
            whoPlay = 0;
    }

    public char[][] getSymbols() {
        return symbols;
    }

    public int getWhoPlay() {
        return whoPlay;
    }

    public void setTypePlay(int typePlay) {
        this.typePlay = typePlay;
    }

    public int getTypePlay() {
        return typePlay;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getDifficult() {
        return difficult;
    }
}

