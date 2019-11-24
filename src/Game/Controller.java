package Game;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;


/**
 * Class that contains methods to control the events
 * triggerd from the user interface.
 */
public class Controller {
    public Button c1r1;
    public Button c2r1;
    public Button c3r1;
    public Button c1r2;
    public Button c2r2;
    public Button c3r2;
    public Button c1r3;
    public Button c2r3;
    public Button c3r3;
    public VBox v1;
    public Text t1;
    public Text t2;
    public Text t3;
    public TextField player1Name;
    public TextField player2Name;
    public Button player1Button;
    public Button player2Button;
    private Mysql p1sql;
    private Mysql p2sql;
    public Text player1Score;
    public Text player2Score;
    public Text instruction;
    public String player1;
    public String player2;
    public Text newScore;
    private boolean playerX = true;


    // Set up X and O images
    private Image oImage = new Image(getClass().getResourceAsStream("Images/o_image.png"), 180.00, 180.00, true,false);
    private Image xImage = new Image(getClass().getResourceAsStream("Images/x_image.png"), 180.00, 180.00, true,false);

    // Collect buttons clicked for X and O
    private ArrayList<Button> buttonsX = new ArrayList<>();
    private ArrayList<Button> buttonsO = new ArrayList<>();

    /**
     * Gets player1's name from the textbox and creates an instance of
     * Mysql with the players name.
     * Sets the player1 variable to the string entered in the player1Name text box
     * This method is called when the player1Button is clicked.
     * Disables the text box and the button so they can not be clicked again.
     */
    public void getPlayer1Score(){
        if(!player1Name.getText().equals("")){
            p1sql = new Mysql(player1Name.getText());
            player1 = player1Name.getText();
            player1Name.setDisable(true);
            player1Button.setDisable(true);
        }
    }

    /**
     * Gets player2's name from the textbox and creates an instance of
     * Mysql with the players name.
     * Sets the player2 variable to the string entered in the player2Name text box
     * This method is called when the player2Button is clicked.
     * Disables the text box and the button so they can not be clicked again.
     */
    public void getPlayer2Score(){
        if(!player2Name.getText().equals("")){
            p2sql = new Mysql(player2Name.getText());
            player2 = player2Name.getText();
            player2Name.setDisable(true);
            player2Button.setDisable(true);

        }
    }

    /**
     * Checks if player1 and player2 are not set or if they are set to X and O.
     * An anonymous gane can be played without having to enter player names.
     * An Anonymous gane is triggered when a user clicks within the grid without entering player names.
     * @return True or False
     */
    public boolean isAnonymousGame(){
        return (player1 == null || player2 == null ||
                player1.equals("X") || player2.equals("O"));
    }

    /**
     * Disables all player textbox and buttons.
     * Sets the instruction to Anonymous Game.
     * Sets the player variables to X and O
     */
    public void setupAnonymousGame(){
        player2Name.setDisable(true);
        player2Button.setDisable(true);
        player1Name.setDisable(true);
        player1Button.setDisable(true);
        instruction.setText("Anonymous Game");
        player1 = "X";
        player2 = "O";
    }

    /**
     * This is called every time a button within the grid is clicked.
     * Checks if it is an anonymous game or not.
     * An Anonymous gane is triggered when a user clicks within the grid without entering player names.
     * If it is an anonymous game it it sets the game to be anonymous by running setupAnonymousGame().
     * If names are entered then it sets the text with the players
     * total score from previous games by pulling the score from the database.
     * <p>
     * Sets the graphic for the button clicked to the xImage or oImage based on who's turn it is.
     * Swaps turn by setting the PlayerX variable to true or false after each button click.
     * Checks if there is a winner after every click by calling checkWhoWon();
     * @param mouseEvent
     */
    public void mouseClick(MouseEvent mouseEvent) {
        Object mouseClick = mouseEvent.getSource();
        Button button = ((Button) mouseClick);
        if(isAnonymousGame()){
            setupAnonymousGame();
        }
        else{
            player2Score.setText(player2+"'s SCORE: "+p2sql.getScore());
            player1Score.setText(player1+"'s SCORE: "+p1sql.getScore());
        }

        if(playerX){
            t3.setText(player2+"'s Turn");
            button.setGraphic(new ImageView(xImage));
            button.setDisable(true);
            buttonsX.add(button);
            this.playerX = false;
            t2.setText("");

        }
        else {
            t2.setText(player1+"'s Turn");
            button.setGraphic(new ImageView(oImage));
            button.setDisable(true);
            buttonsO.add(button);
            this.playerX = true;
            t3.setText("");
        }
        checkWhoWon();
    }

    /**
     * Creates an ArrayList of buttons located in row 1 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getRow1(){
        ArrayList<Button> row1 = new ArrayList<>();
        row1.add(c1r1);
        row1.add(c2r1);
        row1.add(c3r1);
        return row1;
    }

    /**
     * Creates an ArrayList of buttons located in row 2 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getRow2(){
        ArrayList<Button> row2 = new ArrayList<>();
        row2.add(c1r2);
        row2.add(c2r2);
        row2.add(c3r2);
        return row2;
    }

    /**
     * Creates an ArrayList of buttons located in row 3 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getRow3(){
        ArrayList<Button> row3 = new ArrayList<>();
        row3.add(c1r3);
        row3.add(c2r3);
        row3.add(c3r3);
        return row3;
    }

    /**
     * Creates an ArrayList of buttons located in column 1 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getCol1(){
        ArrayList<Button> col1 = new ArrayList<>();
        col1.add(c1r1);
        col1.add(c1r2);
        col1.add(c1r3);
        return col1;
    }

    /**
     * Creates an ArrayList of buttons located in column 2 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getCol2(){
        ArrayList<Button> col2 = new ArrayList<>();
        col2.add(c2r1);
        col2.add(c2r2);
        col2.add(c2r3);
        return col2;
    }

    /**
     * Creates an ArrayList of buttons located in column 3 of the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getCol3(){
        ArrayList<Button> col3 = new ArrayList<>();
        col3.add(c3r1);
        col3.add(c3r2);
        col3.add(c3r3);
        return col3;
    }

    /**
     * Creates an ArrayList of buttons located in a diagonal from bottom left to top right in the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getDiagRight(){
        ArrayList<Button> diagRight = new ArrayList<>();
        diagRight.add(c1r3);
        diagRight.add(c2r2);
        diagRight.add(c3r1);
        return diagRight;
    }

    /**
     * Creates an ArrayList of buttons located in a diagonal from bottom right to top left in the grid.
     * Used in isXtheWinner() and isOtheWinner to check if the buttonsX or buttonsO
     * ArrayLists contain all of these buttons.
     * @return ArrayList of Buttons.
     */
    public ArrayList<Button> getDiagLeft(){
        ArrayList<Button> diagLeft = new ArrayList<>();
        diagLeft.add(c3r3);
        diagLeft.add(c2r2);
        diagLeft.add(c1r1);
        return diagLeft;
    }

    /**
     * Checks if the ArrayList buttonsX contains any of the win conditions.
     * @return True or False
     */
    private boolean isXtheWinner(){
        return buttonsX.containsAll(getRow1()) ||
                buttonsX.containsAll(getRow2()) ||
                buttonsX.containsAll(getRow3()) ||
                buttonsX.containsAll(getCol1()) ||
                buttonsX.containsAll(getCol2()) ||
                buttonsX.containsAll(getCol3()) ||
                buttonsX.containsAll(getDiagRight()) ||
                buttonsX.containsAll(getDiagLeft());
    }

    /**
     * Checks if the ArrayList buttonsO contains any of the win conditions.
     * @return True or False
     */
    private boolean isOtheWinner(){
        return buttonsO.containsAll(getRow1()) ||
                buttonsO.containsAll(getRow2()) ||
                buttonsO.containsAll(getRow3()) ||
                buttonsO.containsAll(getCol1()) ||
                buttonsO.containsAll(getCol2()) ||
                buttonsO.containsAll(getCol3()) ||
                buttonsO.containsAll(getDiagRight()) ||
                buttonsO.containsAll(getDiagLeft());
    }


    /**
     * This is called in mouseClick so is checked any
     * time a button within the grid is clicked.
     * Checks if x or o is the winner.
     * Disables all buttons to END the game.
     * Sets text to show who won.
     * If the players are not X and O then update the score for the winning player in the database.
     * Set text with the updated score from the database.
     * <p>
     * If the size of the ArrayList buttonsX is 5 and the game is still in progress then
     * player1 has taken 5 turns without winning so it sets test to DRAW.
     */
    private void checkWhoWon() {
        if (isXtheWinner()) {

            disableAllButtons(true);
            t2.setText("");
            t3.setText("");
            t1.setText(player1+" WINS");
            if((!player1.equals("X") && !player2.equals("O"))){
                p1sql.updateScore();
                player1Score.setText("");
                player2Score.setText("");
                newScore.setText("New Score: "+p1sql.getScore());

            }
        }

        if (isOtheWinner()) {

            disableAllButtons(true);
            t2.setText("");
            t3.setText("");

            t1.setText(player2+" WINS");
            if(!player1.equals("X") && !player2.equals("O")){
                p2sql.updateScore();
                player1Score.setText("");
                player2Score.setText("");
                newScore.setText("New Score: "+p2sql.getScore());

            }
        }

        if (buttonsX.size() == 5 && t1.getText().equals("Game In Progress")){
            t2.setText("");
            t3.setText("");
            t1.setText("DRAW");
        }

    }

    /**
     * Disables or enables all buttons on the grid based on the parameter.
     * @param bool
     */
    private void disableAllButtons(Boolean bool){
        c1r1.setDisable(bool);
        c1r1.setDisable(bool);
        c2r1.setDisable(bool);
        c3r1.setDisable(bool);
        c1r2.setDisable(bool);
        c2r2.setDisable(bool);
        c3r2.setDisable(bool);
        c1r3.setDisable(bool);
        c2r3.setDisable(bool);
        c3r3.setDisable(bool);
    }

}