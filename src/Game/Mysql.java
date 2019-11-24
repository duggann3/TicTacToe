package Game;

import java.sql.*;

/**
 Mysql class contains methods that interact with
 the tictactoe.mysql data source.
 */
public class Mysql {

    public String player;
    public int score;

    /**
     * Constructor takes in player name as a String
     * @param player
     */
    public Mysql(String player){
        this.player = player;
    }

    /**
     * Connection gets a connection to the tictactoe.mysql data source
     * @return Connection object
     */
    public Connection connect(){
        try {
            return DriverManager.getConnection("jdbc:sqlite:/Users/nick.duggan/workspace/COMP20300/gui_testing/src/tictactoe.mysql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sets the variable "score" to the score from the database
     * for the player specified in the constructor.
     * <p>
     * If the player does not exist in the database
     * then it sets "score" to -1
     * @throws SQLException
     */
    public void setScoreFromDB() throws SQLException {
        PreparedStatement stmt = connect().prepareStatement("select Score from Game where Player = ?;");
        stmt.setObject(1,player);
        ResultSet rs = stmt.executeQuery();
        if(rs.isClosed()){
            score = -1;
            stmt.close();
        }
        else {
            score = rs.getInt(1);
            stmt.close();
        }
    }

    /**
     * Inserts a new row in the database with a new player name and a score of 0.
     * Sets the variable "score" to 0.
     */
    public void addNewPlayer() {
        try {
            PreparedStatement stmt = connect().prepareStatement("INSERT INTO Game (Player, Score) VALUES (?, 0);");
            stmt.setObject(1,player);
            stmt.executeUpdate();
            stmt.close();
            score = 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the row in the database where player = TESTINGPLAYER.
     * This is used to delete the player after a unit test.
     */
    public void deletePlayer(){
        try {
            PreparedStatement stmt = connect().prepareStatement("DELETE FROM Game WHERE Player = 'TESTINGPLAYER';");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the row for the player specified in the constructor with a new score.
     * The updated score is current score +1.
     */
    public void updateScore(){
        score += 1;
        try {
            PreparedStatement stmt = connect().prepareStatement("UPDATE Game SET Score = ? where Player = ?;");

            stmt.setObject(1,score);
            stmt.setObject(2,player);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * First this sets the "score" variable by calling setScoreFromDB()
     * which will set the variable from the score for player in the database.
     * <p>
     * If the player does not exist then addNewPlayer()
     * is called to add a new row for the player and set the score to 0.
     * <p>
     * Retuns the score after it has been set.
     * @return score
     */
    public int getScore(){
        try {
            setScoreFromDB();
            if(score == -1){
                addNewPlayer();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

}
