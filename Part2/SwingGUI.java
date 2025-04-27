import java.util.HashMap;
import javax.swing.*;

public class SwingGUI {
    public static void main(String[] args) {
        HashMap<Character, HorseInfo> horsesCreate = new HashMap<>();
        int[] horsesCustomise = new int[3];
        //<<A, name1>, {1, 1, 1}>, <<B, name2>, {2, 2, 2}>, ...
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


        // Add panels to tabs
        tabbedPane.addTab("Horse Options", panel1);
        tabbedPane.addTab("Race Options", panel2);
        tabbedPane.addTab("Statistics", panel3);


        //for panel1
        JTextField inputSymbolField = new JTextField(10);
        JTextField inputNameField = new JTextField(10);
        JButton horseCreation = new JButton("Enter");

        //panel2
        JButton redirectPanel1 = new JButton("make a horse");
        JButton horseCustomisation = new JButton("Enter");
        String[] selectedOption = {""};

        //panel1
        panel1.add(new JLabel("Enter Horse Character:"));
        panel1.add(inputSymbolField);
        panel1.add(new JLabel("Enter The Horses name:"));
        panel1.add(inputNameField);
        panel1.add(horseCreation);

        horseCreation.addActionListener(e -> {
            String symbol = inputSymbolField.getText().trim();
            String name = inputNameField.getText().trim();
            inputSymbolField.setText("");
            inputNameField.setText("");
            panel1.removeAll();
            
            if (symbol.length() != 1 || name.equals("")) {
                // If the user didn't enter exactly one character
                panel1.add(new JLabel("Please enter a single character:"));
                panel1.add(inputSymbolField);
                panel1.add(new JLabel("Enter The Horses name again:"));
                panel1.add(inputNameField);
                panel1.add(horseCreation);
            }
            else {
                char chosenChar = symbol.charAt(0);
                
                if (horsesCreate.containsKey(chosenChar)) {
                    panel1.add(new JLabel("The character " + chosenChar + " is unavailable."));
                    panel1.add(horseCreation);
                }else {
                    HorseInfo ref = new HorseInfo(name);
                    horsesCreate.put(chosenChar, ref);
                    panel1.add(new JLabel("<html>You have picked: " +chosenChar+"<br>Do you want to add another horse.</html>"));
                    panel1.add(horseCreation);
                    System.out.println(horsesCreate.get(chosenChar));
                    int once = 0;

                    //replaces text in panel2
                    if (once == 0) {
                        //replace breeds with horsesCreate.getkey() all 
                        Character[] breeds = horsesCreate.keySet();
                        JComboBox<String> horseID = new JComboBox<>(breeds);
                        once++;
                        panel2.removeAll();
                        panel2.add(new JLabel("Which Horse do you want to customise"));
                        horseID.addActionListener(f -> {
                            selectedOption[0] = (String) horseID.getSelectedItem();
                        });
                        panel2.add(horseID);
                        panel2.add(horseCustomisation);
                        //button brings it to the horses customisation, can change breed hoof and saddle
                        //if breed == null (stats[0] == null) then you can set it if != cannot set breed 
                    }
                }
            }
            
            panel1.revalidate();
            panel1.repaint();
        });


        //for panel2
        JTextField breed = new JTextField(10);
        JTextField hoof = new JTextField(10);
        JTextField saddle = new JTextField(10);
        String[] breeds = {"Thoroughbred", "Arabian", "Quarter Horse"};
        JComboBox<String> horseID = new JComboBox<>(breeds);

        if (horsesCreate.size() == 0) {
            panel2.add(new JLabel("Please make a horse first"));
            panel2.add(redirectPanel1);
            panel2.revalidate();
            panel2.repaint();
        }

        redirectPanel1.addActionListener(e -> {
            tabbedPane.setSelectedIndex(0);
        });

        horseCustomisation.addActionListener(e -> {
            horsesCreate.get(selectedOption[0]);
            panel2.add(new JLabel("Enter Horse:"));
            panel2.add(breed);
            panel2.add(new JLabel("Enter The Horses name:"));
            panel2.add(hoof);
            panel2.add(new JLabel("Enter The Horses name:"));
            panel2.add(saddle);
            panel2.add(horseCustomisation);
        });

        /*panel2.add(new JLabel("Enter Horse:"));
        panel2.add(breed);
        panel2.add(new JLabel("Enter The Horses name:"));
        panel2.add(hoof);
        panel2.add(new JLabel("Enter The Horses name:"));
        panel2.add(saddle);
        panel2.add(horseCustomisation);*/

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }
}
