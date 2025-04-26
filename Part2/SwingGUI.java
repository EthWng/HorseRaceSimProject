import java.util.HashMap;
import javax.swing.*;

public class SwingGUI {
    public static void main(String[] args) {
        HashMap<Character, String> horses = new HashMap<>();
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

        JTextField inputSymbolField = new JTextField(10);
        JTextField inputNameField = new JTextField(10);

        // Create a button
        JButton submitButton = new JButton("Enter");


        // Add panels to tabs
        tabbedPane.addTab("Horse Options", panel1);
        tabbedPane.addTab("Race Options", panel2);
        tabbedPane.addTab("Statistics", panel3);

        panel1.add(new JLabel("Enter Horse Character:"));
        panel1.add(inputSymbolField);
        panel1.add(new JLabel("Enter The Horses name:"));
        panel1.add(inputNameField);
        panel1.add(submitButton);

        submitButton.addActionListener(e -> {
            String symbol = inputSymbolField.getText().trim();
            String name = inputNameField.getText().trim();
            inputSymbolField.setText("");
            panel1.removeAll();
            
            if (symbol.length() != 1) {
                // If the user didn't enter exactly one character
                panel1.add(new JLabel("Please enter a single character:"));
                panel1.add(inputSymbolField);
                panel1.add(new JLabel("Enter The Horses name again:"));
                panel1.add(inputNameField);
                panel1.add(submitButton);
            }
            else {
                char chosenChar = symbol.charAt(0);
                
                if (horses.containsKey(chosenChar)) {
                    panel1.add(new JLabel("The character " + chosenChar + " is unavailable."));
                    panel1.add(inputSymbolField);
                    panel1.add(submitButton);
                }else {
                    horses.put(chosenChar, name);
                    panel1.add(new JLabel("<html>You have picked: " +chosenChar+"<br>Do you want to add another horse.</html>"));
                    panel1.add(submitButton);
                    System.out.println(horses.get(chosenChar));
                }
            }
            
            panel1.revalidate();
            panel1.repaint();
        });


        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}
