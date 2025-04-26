import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.*;

public class SwingGUI {
    public static void main(String[] args) {
        ArrayList<Character> horses = new ArrayList<>();
        // Create main window
        JFrame frame = new JFrame("Horse Race");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create two panels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.setLayout(new FlowLayout());

        JTextField inputField = new JTextField(10); // 10 columns wide

        // Create a button
        JButton submitButton = new JButton("Submit");

        // When button is clicked, print the text
        submitButton.addActionListener(e -> {
            String userInput = inputField.getText();
            System.out.println("User typed: " + userInput);
        });


        // Add panels to tabs
        tabbedPane.addTab("Statistics", panel1);
        tabbedPane.addTab("Betting", panel2);
        tabbedPane.addTab("Horse Options", panel3);
        tabbedPane.addTab("Race Options", panel4);

        panel3.add(new JLabel("Enter Horse Character:"));
        panel3.add(inputField);
        panel3.add(submitButton);

        submitButton.addActionListener(e -> {
            String userText = inputField.getText().trim();
            panel3.removeAll();
        
            if (userText.length() != 1) {
                // If the user didn't enter exactly one character
                panel3.add(new JLabel("Please enter a single character:"));
                panel3.add(inputField);
                panel3.add(submitButton);
            } else {
                char chosenChar = userText.charAt(0);
                boolean alreadyTaken = false;
        
                for (char horse : horses) {
                    if (horse == chosenChar) {
                        alreadyTaken = true;
                        break;
                    }
                }
        
                if (alreadyTaken) {
                    panel3.add(new JLabel("The character " + chosenChar + " is unavailable."));
                    panel3.add(inputField);
                    panel3.add(submitButton);
                } else {
                    horses.add(chosenChar);
                    panel3.add(new JLabel("You have picked: " + chosenChar));
                    // You can continue or move to next step
                    panel3.add(new JLabel(""));
                    panel3.add(new JLabel("Do you want to add another horse."));
                    panel3.add(submitButton);
                }
            }
        
            panel3.revalidate();
            panel3.repaint();
        });


        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}
