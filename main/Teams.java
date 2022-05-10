package main;

import java.util.ArrayList;

/**
 * This is the Teams class. It holds the logic relating to Teams.
 * 
 * @author Evan McCarthy
 * @version 1.0.0
 * @since 2022.03.10
 *
 */

public class Teams {

    private int winnings; //Teams score
    private int currentTeam;

    /**
     * Constructor for objects of class Player
     */
    public Teams() {
        
    }

    /**
     * Keeps track of the current team.
     * 
     * @param teamList the names of the teams held in an arraylist
     * @return currentTeam - index of the current team
     */
    public int pickTeam(ArrayList<String> teamList) {

        if (currentTeam < teamList.size())
            currentTeam++;
        else
            currentTeam = 1;

        return currentTeam;
    }

    /**
     * Adds or removes parsed value from player's total winnings
     * 
     * @param  val = value of question answered
     * @return none
     */
    public void setWinnings(int val) {
            winnings += val;
        }
        
    /**
     * Returns player's net winnings
     * 
     * @param  none
     * @return winnings = net winnings of player
     */
    public int getWinnings() {
            return winnings;
    }

    /**
     * Takes the score and converts it from a String to
     * an integer
     * 
     * @param score - the score value from the json file as a string
     * @return val - value of the score as an integer
     */
    public int convert(String score) {

        int val = 0;

        // Convert the String
        try {
            val = Integer.parseInt(score);
        }
        catch (NumberFormatException e) {
            // This is thrown when the String
            // contains characters other than digits
            System.out.println("Invalid String");
        }

        return val;
    }
    
} // END OF TEAMS CLASSS
