import Game.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.*;


/**
 * Class to run unit tests against the TicTacToe application
 */
public class TicTacToeTest extends ApplicationTest {

    /**
     * Loads the UI to run tests against.
     * Loads the file TicTacToe.fxml.
     * Sets the scene with the loaded fxml file.
     * Shows the stage.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Controller.class.getResource("TicTacToe.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * setUp for unit tests.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * tears down the UI after every test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * This is used in multiple tests to write names and press enter for both players.
     */
    public void enterPlayers(){
        clickOn("#player1Name");
        write("Nick");
        clickOn("#player1Button");
        clickOn("#player2Name");
        write("Adam");
        clickOn("#player2Button");
    }

    /**
     * This is used in multiple tests to get the player1's score from the text set in the UI.
     * @return score as Integer
     */
    public int getPlayer1Score(){
        Text player1Score = (Text) GuiTest.find("#player1Score");
        Pattern pattern = Pattern.compile("(?<=: ).*");
        Matcher matcher = pattern.matcher(player1Score.getText());
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    /**
     * This is used in multiple tests to get the player2's score from the text set in the UI.
     * @return score as Integer
     */
    public int getPlayer2Score(){
        Text player2Score = (Text) GuiTest.find("#player2Score");
        Pattern pattern = Pattern.compile("(?<=: ).*");
        Matcher matcher = pattern.matcher(player2Score.getText());
        matcher.find();
        return Integer.parseInt(matcher.group());

    }

    /**
     * Used in multiple tests.
     * Sets up a scenario where X Wins.
     */
    public void xWinsSetUp(){
        clickOn("#c1r1");
        clickOn("#c2r1");
        clickOn("#c1r2");
        clickOn("#c2r2");
        clickOn("#c1r3");
    }

    /**
     * Used in multiple tests.
     * Sets up a scenario where O Wins.
     */
    public void oWinsSetUp(){
        clickOn("#c1r1");
        clickOn("#c2r1");
        clickOn("#c1r2");
        clickOn("#c2r2");
        clickOn("#c3r3");
        clickOn("#c2r3");
    }

    /**
     * Tests that a Graphic is displayed when a button in the grid is clicked.
     */
    @Test
    public void buttonsDisplayGraphicWhenClicked(){
        Image xImage = new Image(getClass().getResourceAsStream("Game/Images/x_image.png"), 180.00, 180.00, true,false);
        ImageView imageView = new ImageView(xImage);
        Button button = (Button) GuiTest.find("#c1r1");
        clickOn("#c1r1");
        assertEquals(button.getGraphic().getStyleClass(), imageView.getStyleClass());
    }

    /**
     * Tests that setupAnonymousGame disables player textboxes and buttons and sets
     * text to Anonymous Game.
     */
    @Test
    public void setupAnonymousGameTest(){
        Text instruction = (Text) GuiTest.find("#instruction");
        TextField player1name = (TextField) GuiTest.find("#player1Name");
        TextField player2name = (TextField) GuiTest.find("#player2Name");
        Button player1button = (Button) GuiTest.find("#player1Button");
        Button player2button = (Button) GuiTest.find("#player2Button");
        clickOn("#c1r1");
        assertTrue(player1name.isDisabled());
        assertTrue(player2name.isDisabled());
        assertTrue(player1button.isDisabled());
        assertTrue(player2button.isDisabled());
        assertEquals(instruction.getText(), "Anonymous Game");
    }

    /**
     * Tests that scores appear in the UI when player names are entered.
     */
    @Test
    public void scoresArePopulatedForRealPlayersTest(){
        enterPlayers();
        clickOn("#c1r1");
        assertTrue(getPlayer1Score() >= 0);
        assertTrue(getPlayer2Score() >= 0);
    }

    /**
     * Tests that player turns are swapped on each click of a button inside the grid.
     */
    @Test
    public void turnsAreSwappedTest(){
        Text xturn = (Text) GuiTest.find("#t2");
        Text oturn = (Text) GuiTest.find("#t3");

        clickOn("#c1r1");
        assertEquals(oturn.getText(), "O's Turn");
        assertEquals(xturn.getText(), "");
        clickOn("#c2r1");
        assertEquals(xturn.getText(), "X's Turn");
        assertEquals(oturn.getText(), "");
    }

    /**
     * Tests that a button in the grid is disabled after it is clicked once.
     */
    @Test
    public void buttonCanOnlyBePressedOnceTest(){
        Button button = (Button) GuiTest.find("#c1r1");
        clickOn("#c1r1");
        assertTrue(button.isDisabled());
    }


    /**
     * Tests that all buttons are disabled when someone wins so the game ends.
     */
    @Test
    public void gameEndsTest(){
        // After winning, check that an unpressed button is disabled.
        xWinsSetUp();
        Button button = (Button) GuiTest.find("#c3r1");
        assertTrue(button.isDisabled());
    }

    /**
     * Tests that the correct text appears when X wins.
     */
    @Test
    public void xWinsTest(){
        xWinsSetUp();
        Text result = (Text) GuiTest.find("#t1");
        assertEquals(result.getText(), "X WINS");
    }

    /**
     * Tests that the correct text appears when O wins.
     */
    @Test
    public void oWinsTest(){
        oWinsSetUp();
        Text result = (Text) GuiTest.find("#t1");
        assertEquals(result.getText(), "O WINS");
    }

    /**
     * Tests that the correct text appears when player1 wins.
     * The text should include the entered name.
     */
    @Test
    public void player1WinsTest(){
        enterPlayers();
        xWinsSetUp();
        Text result = (Text) GuiTest.find("#t1");
        assertEquals(result.getText(), "Nick WINS");
    }

    /**
     * Tests that the correct text appears when player2 wins.
     * The text should include the entered name.
     */
    @Test
    public void player2WinsTest(){
        enterPlayers();
        oWinsSetUp();
        Text result = (Text) GuiTest.find("#t1");
        assertEquals(result.getText(), "Adam WINS");
    }

    /**
     * Tests that the updated score for a winner is current score +1 and that the text is set with new score.
     */
    @Test
    public void playersScoreIsUpdatedAfterWinning(){
        Text newscore = (Text) GuiTest.find("#newScore");

        // Record current score and win game for player1
        enterPlayers();
        clickOn("#c1r1");
        int currentScore = getPlayer1Score();
        clickOn("#c2r1");
        clickOn("#c1r2");
        clickOn("#c2r2");
        clickOn("#c1r3");

        // Find score at end of new score string
        Pattern pattern = Pattern.compile("(?<=: ).*");
        Matcher matcher = pattern.matcher(newscore.getText());
        matcher.find();
        int updatedScore = Integer.parseInt(matcher.group());

        // Check that the number displayed for new score is 1 more than the score before winning.
        assertEquals(updatedScore, currentScore + 1);
    }

    /**
     * Tests that the players textboxes and buttons can only be entered / clicked once.
     * They should be disabled after one use.
     */
    @Test
    public void nameCanBeEnteredOnceOnlyTest(){
        TextField player1name = (TextField) GuiTest.find("#player1Name");
        TextField player2name = (TextField) GuiTest.find("#player2Name");
        Button player1button = (Button) GuiTest.find("#player1Button");
        Button player2button = (Button) GuiTest.find("#player2Button");

        enterPlayers();

        assertTrue(player1name.isDisabled());
        assertTrue(player2name.isDisabled());
        assertTrue(player1button.isDisabled());
        assertTrue(player2button.isDisabled());
    }

    /**
     * Test that the the text box t1 displays DRAW when the gane is a draw.
     */
    @Test
    public void gameIsaDrawTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r1");
        clickOn("#c2r1");
        clickOn("#c3r1");
        clickOn("#c2r2");
        clickOn("#c2r3");
        clickOn("#c1r2");
        clickOn("#c3r2");
        clickOn("#c3r3");
        clickOn("#c1r3");
        assertEquals(text.getText(), "DRAW");
    }

    /**
     * Tests that when a player clicks on all buttons in row 1 that the player wins the game.
     */
    @Test
    public void row1WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r1");
        clickOn("#c1r2");
        clickOn("#c2r1");
        clickOn("#c2r2");
        clickOn("#c3r1");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in row 2 that the player wins the game.
     */
    @Test
    public void row2WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r2");
        clickOn("#c1r3");
        clickOn("#c2r2");
        clickOn("#c2r3");
        clickOn("#c3r2");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in row 3 that the player wins the game.
     */
    @Test
    public void row3WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r3");
        clickOn("#c1r2");
        clickOn("#c2r3");
        clickOn("#c2r2");
        clickOn("#c3r3");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in column 1 that the player wins the game.
     */
    @Test
    public void col1WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r1");
        clickOn("#c2r1");
        clickOn("#c1r2");
        clickOn("#c2r2");
        clickOn("#c1r3");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in column 2 that the player wins the game.
     */
    @Test
    public void col2WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c2r1");
        clickOn("#c1r1");
        clickOn("#c2r2");
        clickOn("#c1r2");
        clickOn("#c2r3");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in column 3 that the player wins the game.
     */
    @Test
    public void col3WinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c3r1");
        clickOn("#c1r1");
        clickOn("#c3r2");
        clickOn("#c1r2");
        clickOn("#c3r3");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in a diaganol line from bottom left to top right that the player wins the game.
     */
    @Test
    public void DiagRightWinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c1r3");
        clickOn("#c1r2");
        clickOn("#c2r2");
        clickOn("#c1r1");
        clickOn("#c3r1");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when a player clicks on all buttons in a diaganol line from bottom right to top left that the player wins the game.
     */
    @Test
    public void DiagLeftWinTest(){
        Text text = (Text) GuiTest.find("#t1");
        clickOn("#c3r3");
        clickOn("#c3r2");
        clickOn("#c2r2");
        clickOn("#c3r1");
        clickOn("#c1r1");
        assertEquals(text.getText(), "X WINS");
    }

    /**
     * Tests that when player1 and player2 are set to real names that isAnonymousGame() returns false.
     */
    @Test
    public void nonAnonymousGameTest(){
        Controller tic = new Controller();
        tic.player1 = "Nick";
        tic.player2 = "Adam";
        assertFalse(tic.isAnonymousGame());
    }

    /**
     * Tests that when player1 and player2 are null that isAnonymousGame() returns true.
     */
    @Test
    public void anonymousGameTest(){
        Controller tic = new Controller();
        tic.player1 = null;
        tic.player2 = null;
        assertTrue(tic.isAnonymousGame());
    }

    /**
     * Tests that getRow1() contains the buttons from row 1.
     */
    @Test
    public void getRow1test(){
        Controller tic = new Controller();
        ArrayList<Button> row1buttons = new ArrayList<>(Arrays.asList(tic.c1r1,tic.c2r1,tic.c3r1));
        assertEquals(tic.getRow1(),row1buttons);
    }

    /**
     * Tests that getRow2() contains the buttons from row 2.
     */
    @Test
    public void getRow2test(){
        Controller tic = new Controller();
        ArrayList<Button> row2buttons = new ArrayList<>(Arrays.asList(tic.c1r2,tic.c2r2,tic.c3r2));
        assertEquals(tic.getRow2(),row2buttons);
    }

    /**
     * Tests that getRow3() contains the buttons from row 3.
     */
    @Test
    public void getRow3test(){
        Controller tic = new Controller();
        ArrayList<Button> row3buttons = new ArrayList<>(Arrays.asList(tic.c1r3,tic.c2r3,tic.c3r3));
        assertEquals(tic.getRow3(),row3buttons);
    }

    /**
     * Tests that getCol1() contains the buttons from column 1
     */
    @Test
    public void getCol1test(){
        Controller tic = new Controller();
        ArrayList<Button> col1buttons = new ArrayList<>(Arrays.asList(tic.c1r1,tic.c1r2,tic.c1r3));
        assertEquals(tic.getCol1(),col1buttons);
    }

    /**
     * Tests that getCol2() contains the buttons from column 2
     */
    @Test
    public void getCol2test(){
        Controller tic = new Controller();
        ArrayList<Button> col2buttons = new ArrayList<>(Arrays.asList(tic.c2r1,tic.c2r2,tic.c2r3));
        assertEquals(tic.getCol2(),col2buttons);
    }

    /**
     * Tests that getCol3() contains the buttons from column 3
     */
    @Test
    public void getCol3test(){
        Controller tic = new Controller();
        ArrayList<Button> col3buttons = new ArrayList<>(Arrays.asList(tic.c3r1,tic.c3r2,tic.c3r3));
        assertEquals(tic.getCol3(),col3buttons);
    }

    /**
     * Tests that getDiagRight actually contains the buttons in a diagonal line from bottom left to top right.
     */
    @Test
    public void getDiagRighttest(){
        Controller tic = new Controller();
        ArrayList<Button> DiagRightbuttons = new ArrayList<>(Arrays.asList(tic.c1r3,tic.c2r2,tic.c3r1));
        assertEquals(tic.getDiagRight(),DiagRightbuttons);
    }

    /**
     * Tests that getDiagRight actually contains the buttons in a diagonal line from bottom right to top left.
     */
    @Test
    public void getDiagLefttest(){
        Controller tic = new Controller();
        ArrayList<Button> DiagLeftbuttons = new ArrayList<>(Arrays.asList(tic.c3r3,tic.c2r2,tic.c1r1));
        assertEquals(tic.getDiagLeft(),DiagLeftbuttons);
    }
}