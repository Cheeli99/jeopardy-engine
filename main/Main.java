package main;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the Main class which contains the main method
 * that controls the entire program
 * 
 * @author Evan McCarthy
 * @version 1.0.0
 * @since 2022.03.10
 *
 */

public class Main extends JPanel {

	public JFrame mainJFrame;
	private GridBagConstraints gbc;
    protected JLabel scoreLabel1;
    protected JLabel scoreLabel2;
    protected JLabel scoreLabel3;
    protected JLabel teamLabel1;
    protected JLabel teamLabel2;
    protected JLabel teamLabel3;

    public JSONArray questionList; //Array for the questions/answers
    public ArrayList<String> teamList = new ArrayList<String>(); //Team array
    public int currentTeam = 1;

    public Teams team1Score = new Teams(); //Keeps track of team 1s score
    public Teams team2Score = new Teams(); //Keeps track of team 2s score
    public Teams team3Score = new Teams(); //Keeps track of team 3s score
    public Teams teamPicker = new Teams(); //Keeps track of current team

    public int team1 = 1;
    public int team2 = 2;
    public int team3 = 3;
    public int buttonCount; // Number of buttons answered

    int incorrectCounter = 0; //Keeps track of how many questions are incorrectly answered in a row.

    final static boolean shouldFill = true;

	/** Creates the GUI shown inside the frame's content pane. */
    public Main(JFrame mainJFrame) {

        super(new GridBagLayout());

        //Lay out the main panel.
        this.mainJFrame = mainJFrame;
        setPreferredSize(new Dimension(1280, 720));

        ImageIcon logoicon = new ImageIcon("src/img/circle.png");
        Image logo = logoicon.getImage();
        mainJFrame.setIconImage(logo);
		
		this.setVisible(true);

        gbc = new GridBagConstraints(); 
		if (shouldFill) {
            //natural height, maximum width
            gbc.fill = GridBagConstraints.BOTH;
            }
		gbc.insets = new Insets(15,15,15,15);

        fileReader();
        openingDialog();

        javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 18)));

        int index1 = 0; //index for categories in json file
        /*
         * Categories Labels
         */
        for (int i = 0; i < 5; i++) {

            String category = questionList.getJSONObject(index1).getString("category");
            JLabel categoryLabels = new JLabel(category);
            categoryLabels.setFont(new Font("Arial", Font.BOLD, 20));
            categoryLabels.setVerticalTextPosition(JLabel.CENTER);
            categoryLabels.setHorizontalTextPosition(JLabel.CENTER);
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 1 + i ; // column = 1
            gbc.gridy = 0; // row = 1
            gbc.gridheight = 1; //spans 2 rows
            gbc.gridwidth = 1; //span 2 columns
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            this.add(categoryLabels, gbc);

            index1 = index1 + 5;
        }

        /*
         * Categories
         */
         // Column 1
        for (int i = 0; i < 5; i++) {

            int index = 0 + i;

            String score = questionList.getJSONObject(index).getString("score");

            JButton categoryColumn1 = new JButton("$" + score);
            categoryColumn1.setToolTipText("");
            categoryColumn1.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 1; // column = 1
            gbc.gridy = 1 + i; // row = 1 + i
            gbc.gridheight = 1; //spans 1 row
            gbc.gridwidth = 1; //span 1 column
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            this.add(categoryColumn1, gbc);

            categoryColumn1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    String question = questionList.getJSONObject(index).getString("question");
                    String correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                    String userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                    "Question", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null,
                    null,
                    "Enter text");

                    if (userAnswer == null) {
                        // Makes userAnswer not null so it won't crash the program
                        userAnswer = "";

                        checkAnswer(userAnswer, correctAnswer, score, index);
                        categoryColumn1.setEnabled(false);
        
                        return;
                    }
            
                    checkAnswer(userAnswer, correctAnswer, score, index);
                    categoryColumn1.setEnabled(false);
                }
            }); 
        }
        
        // Column 2
        for (int i = 0; i < 5; i++) {

            int index = 5 + i;

            String score = questionList.getJSONObject(index).getString("score");

            JButton categoryColumn2 = new JButton("$" + score);
            categoryColumn2.setToolTipText("");
            categoryColumn2.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 2; // column = 2
            gbc.gridy = 1 + i; // row = 1 + i
            gbc.gridheight = 1; //spans 1 row
            gbc.gridwidth = 1; //span 1 column
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            this.add(categoryColumn2, gbc);

            categoryColumn2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    String question = questionList.getJSONObject(index).getString("question");
                    String correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                    String userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                    "Question", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null,
                    null,
                    "Enter text");

                    if (userAnswer == null) {
                        // Makes userAnswer not null so it won't crash the program
                        userAnswer = "";

                        checkAnswer(userAnswer, correctAnswer, score, index);
                        categoryColumn2.setEnabled(false);
        
                        return;
                    }
            
                    checkAnswer(userAnswer, correctAnswer, score, index);
                    categoryColumn2.setEnabled(false);
                }
            });
        }

        // Column 3
        for (int i = 0; i < 5; i++) {

            int index = 10 + i;

            String score = questionList.getJSONObject(index).getString("score");

            JButton categoryColumn3 = new JButton("$" + score);
            categoryColumn3.setToolTipText("");
            categoryColumn3.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 3; // column = 3
            gbc.gridy = 1 + i; // row = 1 + i
            gbc.gridheight = 1; //spans 1 row
            gbc.gridwidth = 1; //span 1 column
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            this.add(categoryColumn3, gbc);

            categoryColumn3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    String question = questionList.getJSONObject(index).getString("question");
                    String correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                    String userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                    "Question", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null,
                    null,
                    "Enter text");

                    if (userAnswer == null) {
                        // Makes userAnswer not null so it won't crash the program
                        userAnswer = "";

                        checkAnswer(userAnswer, correctAnswer, score, index);
                        categoryColumn3.setEnabled(false);
        
                        return;
                    }
            
                    checkAnswer(userAnswer, correctAnswer, score, index);
                    categoryColumn3.setEnabled(false);
                }
            });
        }

        // Column 4
        for (int i = 0; i < 5; i++) {

            int index = 15 + i;

            String score = questionList.getJSONObject(index).getString("score");

            JButton categoryColumn4 = new JButton("$" + score);
            categoryColumn4.setToolTipText("");
            categoryColumn4.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 4; // column = 4
            gbc.gridy = 1 + i; // row = 1 + i
            gbc.gridheight = 1; //spans 1 row
            gbc.gridwidth = 1; //span 1 column
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            this.add(categoryColumn4, gbc);

            categoryColumn4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    String question = questionList.getJSONObject(index).getString("question");
                    String correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                    String userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                    "Question", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null,
                    null,
                    "Enter text");

                    if (userAnswer == null) {
                        // Makes userAnswer not null so it won't crash the program
                        userAnswer = "";

                        checkAnswer(userAnswer, correctAnswer, score, index);
                        categoryColumn4.setEnabled(false);
        
                        return;
                    }
            
                    checkAnswer(userAnswer, correctAnswer, score, index);
                    categoryColumn4.setEnabled(false);
                }
            });
        }

        // Column 5
        for (int i = 0; i < 5; i++) {

            int index = 20 + i;

            String score = questionList.getJSONObject(index).getString("score");

            JButton categoryColumn5 = new JButton("$" + score);
            categoryColumn5.setToolTipText("");
            categoryColumn5.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;
            gbc.gridx = 5; // column = 5
            gbc.gridy = 1 + i; // row = 1 + i
            gbc.gridheight = 1; //spans 1 row
            gbc.gridwidth = 1; //span 1 column
            gbc.ipadx = 0; 
            gbc.ipady = 0;
            this.add(categoryColumn5, gbc);

            categoryColumn5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    
                    String question = questionList.getJSONObject(index).getString("question");
                    String correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                    String userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                    "Question", 
                    JOptionPane.PLAIN_MESSAGE, 
                    null,
                    null,
                    "Enter text");

                    if (userAnswer == null) {
                        // Makes userAnswer not null so it won't crash the program
                        userAnswer = "";

                        checkAnswer(userAnswer, correctAnswer, score, index);
                        categoryColumn5.setEnabled(false);
        
                        return;
                    }
            
                    checkAnswer(userAnswer, correctAnswer, score, index);
                    categoryColumn5.setEnabled(false);
                }
            });
        }

        /*
         * Team Labels
         */
        teamLabel1 = new JLabel(teamList.get(0));
        teamLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        teamLabel1.setForeground(Color.GREEN);
        teamLabel1.setVerticalTextPosition(JLabel.CENTER);
        teamLabel1.setHorizontalTextPosition(JLabel.CENTER); 
        gbc.gridx = 2; // column = 2
        gbc.gridy = 6; // row = 6
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(teamLabel1, gbc);

        teamLabel2 = new JLabel(teamList.get(1));
        teamLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        teamLabel2.setVerticalTextPosition(JLabel.CENTER);
        teamLabel2.setHorizontalTextPosition(JLabel.CENTER); 
        gbc.gridx = 3; // column = 3
        gbc.gridy = 6; // row = 6
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(teamLabel2, gbc);

        teamLabel3 = new JLabel(teamList.get(2));
        teamLabel3.setFont(new Font("Arial", Font.BOLD, 20));
        teamLabel3.setVerticalTextPosition(JLabel.CENTER);
        teamLabel3.setHorizontalTextPosition(JLabel.CENTER); 
        gbc.gridx = 4; // column = 4
        gbc.gridy = 6; // row = 6
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(teamLabel3, gbc);
        
        /*
         * Score Labels
         */
        scoreLabel1 = new JLabel("$0");
        scoreLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel1.setVerticalTextPosition(JLabel.CENTER);
        scoreLabel1.setHorizontalTextPosition(JLabel.CENTER);  
        gbc.gridx = 2; // column = 2
        gbc.gridy = 7; // row = 7
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(scoreLabel1, gbc);

        scoreLabel2 = new JLabel("$0");
        scoreLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel2.setVerticalTextPosition(JLabel.CENTER);
        scoreLabel2.setHorizontalTextPosition(JLabel.CENTER);  
        gbc.gridx = 3; // column = 3
        gbc.gridy = 7; // row = 7
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(scoreLabel2, gbc);

        scoreLabel3 = new JLabel("$0");
        scoreLabel3.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel3.setVerticalTextPosition(JLabel.CENTER);
        scoreLabel3.setHorizontalTextPosition(JLabel.CENTER);  
        gbc.gridx = 4; // column = 4
        gbc.gridy = 7; // row = 7
        gbc.gridheight = 1; //spans 1 row
        gbc.gridwidth = 1; //span 1 column
        gbc.ipadx = 0; 
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(scoreLabel3, gbc);
    } // END OF CONSTRUCTOR

    /**
     * Prompts the user to input a name for each team
     * 
     * @param none
     * @return teamList - a array containing the names of the teams
     */
    private ArrayList<String> openingDialog() {

        for (int i = 1; i < 4; i++) {
            javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 18)));
            String s = (String)JOptionPane.showInputDialog(
                                null,
                                "Please enter Team " + i + "'s name...",
                                "Choose your team name!",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");

            if ((s != null) && (s.length() > 0)) {
                JOptionPane.showMessageDialog(null, "Team " + i + " is now named " + s);
                teamList.add(s);
                
            } else {
                //If you're here, the return value was null/empty.
                JOptionPane.showMessageDialog(null, "Please enter a name!");

                //TODO
                teamList.clear();
                openingDialog();
            }

        }
        return teamList;
    }

    /**
     * Takes data from a json and puts it in an JSONArray. User
     * can choose between the two sets of questions.
     * 
     * @param none
     * @return questionList - the list of questions taken from a json file
     */
    protected JSONArray fileReader () {
        javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.BOLD, 18)));
        Object[] possibilities = {"Game 1", "Game 2"};
        String s = (String)JOptionPane.showInputDialog(
                    null,
                    "Do you want to play Game 1 or Game 2?",
                    "Pick an option",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "Game 1");

        if (s == "Game 1") {

            try {
                File myObj = new File("main/questions.json");
                Scanner myReader = new Scanner(myObj);
                String str = new String();
                while (myReader.hasNext())
                    str += myReader.nextLine();
                myReader.close();
    
                JSONObject obj = new JSONObject(str);
                questionList = obj.getJSONArray("results");
    
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred. File not found.");
                e.printStackTrace();
            }
            
        } else if (s == "Game 2") {

            try {
                File myObj = new File("main/questions2.json");
                Scanner myReader = new Scanner(myObj);
                String str = new String();
                while (myReader.hasNext())
                    str += myReader.nextLine();
                myReader.close();
    
                JSONObject obj = new JSONObject(str);
                questionList = obj.getJSONArray("results");
    
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred. File not found.");
                e.printStackTrace();
            }
            
        } else {
            System.exit(0);
        }
        
        return questionList;
    }

    /**
     * Determines whether the user's answer is correct or not. It also takes the currentTeam,
     * determines whether team1, team2 or team3 is at play and then increments currentTeam by
     * one.
     * 
     * @param userAnswer - the user's answer as a string
     * @param correctAnswer - the correct answer as a string
     * @param score - the score as a string
     * @return currentTeam - the team that is currently at play
     */
    public int checkAnswer(String userAnswer, String correctAnswer, String score, int index) {
        currentTeam = teamPicker.pickTeam(teamList); // Finds the current team

        SwingUtilities.invokeLater(updateGUI);

        if (currentTeam == team1) {
            if (userAnswer.toLowerCase().contains(correctAnswer.toLowerCase())) {
                // Show correct message dialog
                JOptionPane.showMessageDialog(
                    null, 
                    "Correct!", 
                    "Result", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                incorrectCounter = 0;
                int val = team1Score.convert(score);
                team1Score.setWinnings(val);

                countButtons();

                SwingUtilities.invokeLater(updateGUI);

                endingScreen();
                
            } else {
                incorrectCounter++;
                // Show wrong message dialog
                if (incorrectCounter == 3) {
                    JOptionPane.showMessageDialog(
                    null,
                    "Incorrect. The answer was " + correctAnswer,
                    "Result",
                    JOptionPane.WARNING_MESSAGE);

                    incorrectCounter = 0;
                    currentTeam = teamPicker.pickTeam(teamList);
                    SwingUtilities.invokeLater(updateGUI);

                    countButtons();
                    
                    endingScreen();
                    
                    return currentTeam;
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Incorrect. " + teamList.get(1) + "'s turn.",
                        "Result",
                        JOptionPane.WARNING_MESSAGE);
                    }
                String question = questionList.getJSONObject(index).getString("question");
                correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                "Question", 
                JOptionPane.PLAIN_MESSAGE, 
                null,
                null,
                "Enter text");

                if (userAnswer == null) {
                    // Makes userAnswer not null so it won't crash the program
                    userAnswer = "";

                    checkAnswer(userAnswer, correctAnswer, score, index);
    
                    return currentTeam;
                }

                checkAnswer(userAnswer, correctAnswer, score, index);
            }
        } else if (currentTeam == team2) {
            if (userAnswer.toLowerCase().contains(correctAnswer.toLowerCase())) {
                // Show correct message dialog
                JOptionPane.showMessageDialog(
                    null, 
                    "Correct!", 
                    "Result", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                incorrectCounter = 0;
                int val = team2Score.convert(score);
                team2Score.setWinnings(val);

                countButtons();

                SwingUtilities.invokeLater(updateGUI);

                endingScreen();
                
            } else {
                incorrectCounter++;
                System.out.println("incorrect counter " + incorrectCounter);
                // Show wrong message dialog
                if (incorrectCounter == 3) {
                    JOptionPane.showMessageDialog(
                    null,
                    "Incorrect. The answer was " + correctAnswer,
                    "Result",
                    JOptionPane.WARNING_MESSAGE);

                    incorrectCounter = 0;
                    currentTeam = teamPicker.pickTeam(teamList);
                    System.out.println("current team " + currentTeam);
                    SwingUtilities.invokeLater(updateGUI);

                    countButtons();

                    endingScreen();

                    return currentTeam;
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Incorrect. " + teamList.get(2) + "'s turn.",
                        "Result",
                        JOptionPane.WARNING_MESSAGE);
                    }
                String question = questionList.getJSONObject(index).getString("question");
                correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                "Question", 
                JOptionPane.PLAIN_MESSAGE, 
                null,
                null,
                "Enter text");

                if (userAnswer == null) {
                    // Makes userAnswer not null so it won't crash the program
                    userAnswer = "";

                    checkAnswer(userAnswer, correctAnswer, score, index);
    
                    return currentTeam;
                }

                checkAnswer(userAnswer, correctAnswer, score, index);
            }
        } else if (currentTeam == team3) {
            if (userAnswer.toLowerCase().contains(correctAnswer.toLowerCase())) {
                // Show correct message dialog
                JOptionPane.showMessageDialog(
                    null, 
                    "Correct!", 
                    "Result", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                incorrectCounter = 0;
                int val = team3Score.convert(score);
                team3Score.setWinnings(val);

                countButtons();

                SwingUtilities.invokeLater(updateGUI);

                endingScreen();
                
            } else {
                incorrectCounter++;
                System.out.println("incorrect counter " + incorrectCounter);
                // Show wrong message dialog
                if (incorrectCounter == 3) {
                    JOptionPane.showMessageDialog(
                    null,
                    "Incorrect. The answer was " + correctAnswer,
                    "Result",
                    JOptionPane.WARNING_MESSAGE);

                    incorrectCounter = 0;
                    currentTeam = teamPicker.pickTeam(teamList); // Finds the current team; // Finds the current team
                    System.out.println("current team " + currentTeam);
                    SwingUtilities.invokeLater(updateGUI);

                    countButtons();
    
                    endingScreen();

                    return currentTeam;
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Incorrect. " + teamList.get(0) + "'s turn.",
                        "Result",
                        JOptionPane.WARNING_MESSAGE);
                    }
                String question = questionList.getJSONObject(index).getString("question");
                correctAnswer = questionList.getJSONObject(index).getString("correct_answer");

                userAnswer = (String)JOptionPane.showInputDialog(null, question, 
                "Question", 
                JOptionPane.PLAIN_MESSAGE, 
                null,
                null,
                "Enter text");

                if (userAnswer == null) {
                    // Makes userAnswer not null so it won't crash the program
                    userAnswer = "";

                    checkAnswer(userAnswer, correctAnswer, score, index);
    
                    return currentTeam;
                }

                checkAnswer(userAnswer, correctAnswer, score, index);
            }
        }
        return currentTeam;
    }

    public void endingScreen() {
        if (buttonCount == 25) {
            int n = JOptionPane.showConfirmDialog(
            null,
            "Would you like to play again?",
            "Continue?",
            JOptionPane.YES_NO_OPTION);

            if (n == JOptionPane.YES_OPTION) {
                buttonCount = 0;
                restartProgram();
                return;
            } else if (n == JOptionPane.NO_OPTION) {
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null,
                "Error. Please try again.",
                "Error!",
                JOptionPane.ERROR_MESSAGE);
            }
              
        } else {
            return;
        }
    }

    /**
     * Counts the number of buttons that have been answered
     * 
     * @param none
     * @return buttonCount
     */
    public int countButtons() {
        buttonCount++;   

        return buttonCount;
    }

    private void restartProgram() {

        mainJFrame.dispose();
        //Create and set up the window.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame mainJFrame = new JFrame("Datamania");
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Main newContentPane = new Main(mainJFrame);
        newContentPane.setOpaque(true); //content panes must be opaque
        mainJFrame.setContentPane(newContentPane);
        mainJFrame.setResizable(true);

        //Display the window.
        mainJFrame.pack();
        mainJFrame.setVisible(true);
    }

    Runnable updateGUI = new Runnable() {
        public void run() {
            if (currentTeam == 1){
                scoreLabel1.setText("$" + team1Score.getWinnings());
                teamLabel1.setForeground(Color.BLACK);
                teamLabel2.setForeground(Color.GREEN);
                teamLabel3.setForeground(Color.BLACK);
            } else if (currentTeam == 2) {
                scoreLabel2.setText("$" + team2Score.getWinnings());
                teamLabel1.setForeground(Color.BLACK);
                teamLabel2.setForeground(Color.BLACK);
                teamLabel3.setForeground(Color.GREEN);
            } else if (currentTeam == 3) {
                scoreLabel3.setText("$" + team3Score.getWinnings());
                teamLabel1.setForeground(Color.GREEN);
                teamLabel2.setForeground(Color.BLACK);
                teamLabel3.setForeground(Color.BLACK);
            } 
        }     
    };

	public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
} // END OF Main Class
