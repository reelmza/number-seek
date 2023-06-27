import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

public class UI extends JFrame {
    private JPanel headerBox;
    private JPanel breifBox;
    private JPanel bodyBox;
    private JPanel infoBox;

    GameFunctions functions = new GameFunctions();

    public UI() {
        initializeUI();
    }

    private void initializeUI() {
        functions.generateNewAnswer();
        setTitle("Number Seek");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 200));

        // Create the headerBox panel
        headerBox = new JPanel();
        headerBox.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 10));

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.BLUE);

        JLabel attemptsLabel = new JLabel("Attempts Left: " + functions.getAttempts());

        headerBox.add(scoreLabel);
        headerBox.add(attemptsLabel);

        // Create the bodyBox panel
        bodyBox = new JPanel();
        bodyBox.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JTextField textField = new JTextField(10);
        textField.setPreferredSize(new Dimension(10, 30));

        JButton button = new JButton("Check");
        button.setPreferredSize(new Dimension(80, 30));

        bodyBox.add(textField);
        bodyBox.add(button);

        // Create the infoBox panel
        infoBox = new JPanel();
        infoBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel infoLabel = new JLabel("Try again");
        infoLabel.setForeground(Color.RED);
        infoLabel.setVisible(false);
        infoBox.add(infoLabel);

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                infoLabel.setVisible(false);

                // Handle user entry
                try {
                    int input = Integer.parseInt(textField.getText());

                    if (input != functions.getAnswer()) {
                        // When Game attempts is exhausted
                        if (Integer.parseInt(functions.getAttempts()) == 1) {
                            try {
                                FileReader fileReader = new FileReader("./src/highscore.txt");

                                int i;
                                String highScore = "";
                                while ((i = fileReader.read()) != -1) {
                                    // System.out.print((char) i);
                                    highScore = highScore + (char) i;
                                }

                                // Add line in console
                                System.out.println("");

                                // format high score string
                                highScore = highScore.replace("High Score: ", "");

                                if (Integer.parseInt(highScore) >= Integer.parseInt(functions.getScore())) {
                                    System.out.println("Score record not broken.");
                                } else {
                                    FileWriter fileWriter = new FileWriter("./src/highscore.txt");
                                    fileWriter.write("High Score: " + functions.getScore());
                                    fileWriter.close();

                                    System.out.println("New score record set.");

                                }

                                System.out.println("Current High Score: " + highScore);
                                System.out.println("Your Score: " + functions.getScore());

                                System.out.println("");
                                fileReader.close();

                            } catch (Exception fileReadError) {
                                System.out.println("File doesn't exit, file will be created");

                                // If file doesn't exist create one
                                try {
                                    FileWriter fileWriter = new FileWriter("./src/highscore.txt");
                                    fileWriter.write("High Score: " + functions.getScore());
                                    fileWriter.close();
                                } catch (Exception fileWireError) {
                                    System.out.println("Can not save high score.");
                                }
                            }

                            // Reset game environments
                            functions.resetScore();
                            functions.resetAttempts();

                            attemptsLabel.setText("Attempts Left: " + functions.getAttempts());
                            scoreLabel.setText("Score: " + functions.getScore());
                            functions.generateNewAnswer();

                            infoBox.setVisible(true);
                        } else {
                            infoBox.setVisible(true);
                            infoLabel.setText("Wrong Answer");

                            functions.decreaseAttempts();
                            attemptsLabel.setText("Attempts Left: " + functions.getAttempts());

                            infoLabel.setText("Try Again");
                            infoLabel.setVisible(true);
                        }

                    } else {
                        functions.increaseScore();

                        functions.resetAttempts();
                        attemptsLabel.setText("Attempts Left: " + functions.getAttempts());

                        functions.generateNewAnswer();

                        scoreLabel.setText("Score: " + functions.getScore());
                    }

                } catch (Exception wrongEntry) {
                    infoBox.setVisible(true);
                }
            }
        });

        // Create game bried panel
        breifBox = new JPanel();
        JLabel breifLabel = new JLabel("Guess a number between 1-30");
        breifBox.add(breifLabel);

        // Create a main panel to hold the stacked panels
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(headerBox);
        mainPanel.add(breifBox);
        mainPanel.add(bodyBox);
        mainPanel.add(infoBox);

        // Add the main panel to the frame
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // setPreferredSize(new Dimension(350, 160));
        pack();
        setResizable(false);
        setVisible(true);
    }
}
