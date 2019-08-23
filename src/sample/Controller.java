package sample;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import java.io.IOException;


public class Controller {

    private GameScreen gameScreen = new GameScreen();
    private boolean running = true;
    @FXML
    private Button b00, b01, b02, b10, b11, b12, b20, b21, b22, gameButtonMain, gameButtonRestart;
    @FXML
    private Button play_play, play_comp, buttonEasy, buttonMedium, buttonHard;
    @FXML
    private Label winner;


    /*Game buttons*/
    @FXML
    public void handleActionButton() {
        char[][] symbols = gameScreen.getSymbols();
        char empty = " ".charAt(0);
        if (running) {
            /*of button clicked and array is empty (without a symbol O,X)*/
            if (b00.isArmed() && symbols[0][0] == empty) {
                changeButtonStyle(b00); // print the symbol X or O
                gameScreen.setSymbols(0, 0, true); //set symbol X or O in matrix
            } else if (b01.isArmed() && symbols[0][1] == empty) {
                changeButtonStyle(b01);
                gameScreen.setSymbols(0, 1, true);
            } else if (b02.isArmed() && symbols[0][2] == empty) {
                changeButtonStyle(b02);
                gameScreen.setSymbols(0, 2, true);
            } else if (b10.isArmed() && symbols[1][0] == empty) {
                changeButtonStyle(b10);
                gameScreen.setSymbols(1, 0, true);
            } else if (b11.isArmed() && symbols[1][1] == empty) {
                changeButtonStyle(b11);
                gameScreen.setSymbols(1, 1, true);
            } else if (b12.isArmed() && symbols[1][2] == empty) {
                changeButtonStyle(b12);
                gameScreen.setSymbols(1, 2, true);
            } else if (b20.isArmed() && symbols[2][0] == empty) {
                changeButtonStyle(b20);
                gameScreen.setSymbols(2, 0, true);
            } else if (b21.isArmed() && symbols[2][1] == empty) {
                changeButtonStyle(b21);
                gameScreen.setSymbols(2, 1, true);
            } else if (b22.isArmed() && symbols[2][2] == empty) {
                changeButtonStyle(b22);
                gameScreen.setSymbols(2, 2, true);
            }
            printWinner(gameScreen.gameState());
        }
        System.out.println(gameScreen.getDifficult());
        if (gameScreen.getWhoPlay() == 1 && running && gameScreen.getTypePlay() == 2 && gameScreen.getDifficult()==1) {
            int aux = gameScreen.makeMoveEasy();
            changeButtonStyle(aux); // print the symbol X or O
            System.out.println(aux);
        }else if (gameScreen.getWhoPlay() == 1 && running && gameScreen.getTypePlay() == 2 && gameScreen.getDifficult()==2) {
            int aux = gameScreen.makeMoveMedium();
            changeButtonStyle(aux); // print the symbol X or O
            System.out.println(aux);
        }else if (gameScreen.getWhoPlay() == 1 && running && gameScreen.getTypePlay() == 2 && gameScreen.getDifficult()==3) {
            Move aux = gameScreen.makeMoveHard(gameScreen.getSymbols(), gameScreen.IASymbol);
            gameScreen.setSymbols(aux.getyCoordinates(), aux.getxCoordinates(), true);
            changeButtonStyle(aux.getyCoordinates()*10 + aux.getxCoordinates()); // print the symbol X or O
            System.out.println(aux);
        }
        printWinner(gameScreen.gameState());
    }

    /*Menu buttons*/
    @FXML
    public void handleMenu() {
        if (play_play.isArmed()) {
            gameScreen.setTypePlay(1);
            try {
                System.out.println();
                Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Tic Tac Toe");
                primaryStage.getIcons().add(new Image("icon.png"));
                primaryStage.setScene(new Scene(root, 550, 600));
                primaryStage.show();

                // get a handle to the stage
                Stage stage2 = (Stage) play_play.getScene().getWindow();
                // do what you have to do
                stage2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (play_comp.isArmed()) {
            gameScreen.setTypePlay(2);
            try {
                System.out.println();
                Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Tic Tac Toe");
                primaryStage.getIcons().add(new Image("icon.png"));
                primaryStage.setScene(new Scene(root, 550, 600));
                primaryStage.show();

                // get a handle to the stage
                Stage stage2 = (Stage) play_play.getScene().getWindow();
                // do what you have to do
                stage2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    public void handleMenuSec(){
        if(buttonEasy.isFocused()){
            gameScreen.setDifficult(1);
        }else if(buttonMedium.isFocused()){
            gameScreen.setDifficult(2);
        }else if(buttonHard.isFocused()){
            gameScreen.setDifficult(3);
        }

        try {
            System.out.println();
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.getIcons().add(new Image("icon.png"));
            primaryStage.setScene(new Scene(root, 550, 600));
            primaryStage.show();

            // get a handle to the stage
            Stage stage2 = (Stage) buttonMedium.getScene().getWindow();
            // do what you have to do
            stage2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*Restart button*/
    @FXML
    public void handleRestart() {
       resetGame();


    }

    /*Main Menu buttons*/
    @FXML
    public void handleMainMenu() {
        System.out.println("lol");
         try {
            System.out.println();
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Tic Tac Toe");
            primaryStage.getIcons().add(new Image("icon.png"));
            primaryStage.setScene(new Scene(root, 550, 600));
            primaryStage.show();

            // get a handle to the stage
            Stage stage2 = (Stage) gameButtonMain.getScene().getWindow();
            // do what you have to do
            stage2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /*chage the background image of 9 buttons, O or X*/
    public void changeButtonStyle(Button button) {
        if (gameScreen.getWhoPlay() == 0) {
            button.setStyle("-fx-background-image: url('x2.png');");
        } else if (gameScreen.getWhoPlay() == 1) {
            button.setStyle("-fx-background-image: url('o2.png');");
        }

    }

    public void changeButtonStyle(int aux) {
        switch (aux) {
            case 0:
                b00.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 1:
                b01.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 2:
                b02.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 10:
                b10.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 11:
                b11.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 12:
                b12.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 20:
                b20.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 21:
                b21.setStyle("-fx-background-image: url('o2.png');");
                break;
            case 22:
                b22.setStyle("-fx-background-image: url('o2.png');");
                break;
        }


    }

    public void printWinner(int whoWin) {
        this.running = gameScreen.gameState() == 0;
        if (whoWin == 2 && gameScreen.getTypePlay() == 1) {
            winner.setText("X wins");
        } else if (whoWin == 3 && gameScreen.getTypePlay() == 1) {
            winner.setText("O wins");
        } else if (whoWin == 2 && gameScreen.getTypePlay() == 2) {
            winner.setText("You win");
        } else if (whoWin == 3 && gameScreen.getTypePlay() == 2) {
            winner.setText("Computer wins");
        } else if(whoWin == 1 ){
            winner.setText("It's a draw");
        }
        if(whoWin != 0)
            gameButtonRestart.setVisible(true);
    }

    public void resetGame() {
        b00.setStyle("-fx-background-image: url('null.jpg');");
        b01.setStyle("-fx-background-image: url('null.jpg');");
        b02.setStyle("-fx-background-image: url('null.jpg');");
        b10.setStyle("-fx-background-image: url('null.jpg');");
        b11.setStyle("-fx-background-image: url('null.jpg');");
        b12.setStyle("-fx-background-image: url('null.jpg');");
        b20.setStyle("-fx-background-image: url('null.jpg');");
        b21.setStyle("-fx-background-image: url('null.jpg');");
        b22.setStyle("-fx-background-image: url('null.jpg');");
        running = true;
        gameScreen.resetGame();
        winner.setText("");
        gameButtonRestart.setVisible(false);

    }

}
