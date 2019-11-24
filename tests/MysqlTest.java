import org.junit.Test;
import Game.*;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Contains tests for the Mysql class
 */
public class MysqlTest {


    /**
     * Tests that connection is not equal to null.
     */
    @Test
    public void testconnection() {
        Mysql mysql = new Mysql("Nick");
        Connection con = mysql.connect();
        assertNotEquals(con, null);
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests that the variable score is set from the database.
     * if score id greater than or equal to 0 then it has been set
     * from the database.
     * Otherwise it would be -1.
     */
    @Test
    public void testScoreIsUpdatedFromDB(){
        Mysql mysql = new Mysql("Nick");
        try {
            mysql.setScoreFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(mysql.score >=0);
    }

    /**
     * Tests that the variable score is set to -1 when it can not be set from the database.
     */
    @Test
    public void testScoreIsMinus1WhenNameIsNotInDB(){
        Mysql mysql = new Mysql("Doesn't Exist");
        try {
            mysql.setScoreFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(mysql.score, -1);
    }

    /**
     * Tests that GetScore returns score for an Existing player.
     * Score for a player in the database with the name Nick is greater than 0.
     * So this test will pass if the score returned is greater than 0
     */
    @Test
    public void testGetScoreForExistingPlayer(){
        Mysql mysql = new Mysql("Nick");
        mysql.getScore();
        assertTrue(mysql.score > 0);
    }

    /**
     * Tests that the score returned for a non existing player = 0.
     */
    @Test
    public void testGetScoreForNonExistingPlayer(){
        Mysql mysql = new Mysql("TESTINGPLAYER");
        mysql.getScore();
        assertEquals(mysql.score, 0);
        mysql.deletePlayer();
    }

}