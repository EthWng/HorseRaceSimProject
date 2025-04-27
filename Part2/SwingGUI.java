import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class SwingGUI {
    public static void main(String[] args) {
        HashMap<Character, HorseInfo> horsesCreate = new HashMap<>();//the char that the horse uses points to its HorseInfo class
        HashMap<Character, Horse> horses = new HashMap<>();//the char that the horse uses points to its Horse class
        final HorseInfo[] horseArray = {new HorseInfo(null)};//used so different .addactionlisteners can use it 

        // Create main window
        JFrame frame = new JFrame("Horse Race");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create three panels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));


        //names the panels
        tabbedPane.addTab("Horse Options", panel1);
        tabbedPane.addTab("Race Options", panel2);
        tabbedPane.addTab("Current Horse Stats", panel3);
        tabbedPane.addTab("Race options", panel4);


        //vars for panel1
        JTextField inputSymbolField = new JTextField(1);
        JTextField inputNameField = new JTextField(10);
        JButton horseCreation = new JButton("Enter");

        //vars for panel2
        JButton redirectPanel1 = new JButton("make a horse");
        JButton horseCustomisation = new JButton("Enter");
        Character[] selectedOption = {' '};
        int[] horsesAttributes = {1, 1, 1};
        Horse horse = new Horse('T', "temp", horsesAttributes);//used to display speed, stamina and cooldown

        //adds to panel1
        panel1.add(new JLabel("Enter Horse Character:"));
        panel1.add(inputSymbolField);
        panel1.add(new JLabel("Enter The Horses name:"));
        panel1.add(inputNameField);
        panel1.add(horseCreation);

        //if horsecreation button pressed this function runs
        horseCreation.addActionListener(e -> {
            String symbol = inputSymbolField.getText().trim();//removes white space before and after text
            String name = inputNameField.getText().trim();
            inputSymbolField.setText("");//if the user presses the button after creating a horse
            inputNameField.setText("");
            panel1.removeAll();//removes all previous text for a clean window
            
            //the symbol for the horse needs to be 1 character
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
                
                //if theres a horse that occupies the character picked
                if (horsesCreate.containsKey(chosenChar)) {
                    panel1.add(new JLabel("The character " + chosenChar + " is unavailable."));
                    panel1.add(horseCreation);
                }
                //else it creates the horse
                else {
                    HorseInfo ref = new HorseInfo(name);
                    horsesCreate.put(chosenChar, ref);
                    panel1.add(new JLabel("<html>You have picked: " +chosenChar+"<br>Do you want to add another horse.</html>"));
                    panel1.add(horseCreation);
                    System.out.println(horsesCreate.get(chosenChar));
                    int once = 0;

                    //replaces text in panel2
                    //if the user has entered a horse the new text will appear 
                    if (once == 0) {
                        //replace breeds with horsesCreate.getkey() all 
                        once++;
                        panel2.removeAll();
                        panel2.add(new JLabel("Which Horse do you want to customise"));
                        selectId(horsesCreate, selectedOption, panel2);
                        panel2.add(horseCustomisation);
                        //button brings it to the horses customisation, can change breed hoof and saddle
                        //if breed == null (stats[0] == null) then you can set it if != cannot set breed 
                    }
                }
            }
            
            // Updates the panel's layout after adding or removing components
            // Redraws the panel to show any visual changes
            repaint(panel1);
        });


        //for panel2
        JButton horsePick = new JButton("Enter");
        JButton redirectStats = new JButton("Show stats");

        //default text that appears when the user opens program
        //change when creating files that hold info about horses
        panel2.add(new JLabel("Please make a horse first"));
        panel2.add(redirectPanel1);
        repaint(panel2);

        //the button just redirects the user to panel1
        redirectPanel1.addActionListener(e -> {
            tabbedPane.setSelectedIndex(0);
        });

        //panel 3
        JButton back = new JButton("go Back");

        back.addActionListener(e ->{
            tabbedPane.setSelectedIndex(1);
        });

        //panel2
        //if button pressed it allows the user to customise the horse they picked
        horseCustomisation.addActionListener(e -> {
            String[] placeholder = {""};
            //HorseInfo theHorse = horsesCreate.get(selectedOption[0]);
            horseArray[0] = horsesCreate.get(selectedOption[0]);//gets the reference of class user picked
            panel2.removeAll();
            //if the horse doesnt have a set breed the are allowed to customise it 
            if (horseArray[0].getStats()[0] == 0) {
                //creates a dropdown of the breeds
                String[] breeds = {"Thoroughbred", "Arabian", "Quarter Horse"};
                JComboBox<String> breed = new JComboBox<>(breeds);
                panel2.add(new JLabel("Enter Horse Breed:"));
                horseArray[0].setBreed(horsesAttributes[0]);
                //when user presses on a new breed the breed turns to an int for horse class to use later
                breed.addActionListener(f -> {
                    placeholder[0] = (String) breed.getSelectedItem();
                    switch (placeholder[0]) {
                        case "Thoroughbred":
                            horsesAttributes[0] = 1;
                            break;
                        case "Arabian":
                            horsesAttributes[0] = 2;
                            break;
                        case "Quarter Horse":
                            horsesAttributes[0] = 3;
                            break;
                    }
                    horse.setBreed(horsesAttributes[0]);
                    //sets the breed every time user selects new breed
                    horseArray[0].setBreed(horsesAttributes[0]);
                    dynamicHorseUpdates(panel3, horse, back);
                });
                panel2.add(breed);//adds the drop down
            }
            horseArray[0].set2Stats(horsesAttributes);//initial stats for a horse
            panel2.add(new JLabel("Enter The Horses hoof:"));
            customise(panel2, panel3, horse, horseArray[0], horsesAttributes, 1, back);
            panel2.add(new JLabel("Enter The Horses saddle:"));
            customise(panel2, panel3, horse, horseArray[0], horsesAttributes, 2, back);
            panel2.add(horsePick);
            panel2.add(redirectStats);
            repaint(panel2);
        });

        redirectStats.addActionListener(e -> {
            dynamicHorseUpdates(panel3, horse, back);
            tabbedPane.setSelectedIndex(2);
        });

        //when the user is done with customising the horse they create a new instance of horse
        horsePick.addActionListener(e -> {
            char key = ' ';
            for (Map.Entry<Character, HorseInfo> entry : horsesCreate.entrySet()) {
                if (entry.getValue().equals(horseArray[0])) {
                    key = entry.getKey();
                    break;
                }
            }
            //if no horse with character key it creates a new instance of horse
            if (horses.get(key) == null) {
                Horse temp = new Horse(key, horseArray[0].getName(), horseArray[0].getStats()); 
                horses.put(key, temp);
            }
            //else it edits the existing horse
            else{
                horses.get(key).setHS(horseArray[0].getStats()[1], horseArray[0].getStats()[2]);
            }

            //testing purposes
            System.out.println(Arrays.toString(horseArray[0].getStats()));
            System.out.println(horses.get(key));
            System.out.println(horses.get(key).getSpeed());
            //

            panel2.removeAll();
            panel2.add(new JLabel("customise another horse"));
            selectId(horsesCreate, selectedOption, panel2);
            panel2.add(horseCustomisation);
            repaint(panel2);
        });

        //panel4 should have a menu to add lanes and change race length
        //when a lane appears the user can then add the specific horse to the lane
        //when the horse is added they are removed from the arraylist or smthing
        //then below have one that adds the horses 
        //then a button to start the race
        //dynamic updates

        // Add the tabbed pane to the frame
        frame.add(tabbedPane);

        frame.setVisible(true);
    }

    /**
     * method for program to create a dropdown of all the horses user has created
     * 
     * @param horsesCreate used to get all the keys and put them in the dropdown
     * @param selectedOption used to hold the value of the key picked
     * @param panel2 the panel the dropdowns being added to
     */
    private static void selectId(HashMap<Character, HorseInfo> horsesCreate, Character[] selectedOption, JPanel panel2){
        Character[] breeds = horsesCreate.keySet().toArray(new Character[0]);
        selectedOption[0] = horsesCreate.keySet().iterator().next();//makes sure its not null
        JComboBox<Character> horseID = new JComboBox<>(breeds);
        horseID.addActionListener(f -> {
            selectedOption[0] = (Character) horseID.getSelectedItem();
        });
        panel2.add(horseID);
    }

    /**
     *creates dropdown to pick the hoof and saddle of horse
     * 
     * @param theHorse used to pass to the setattributes method
     * @param horsesAttributes array that holds the new changes
     * @param whichAttrbute determines if saddle or hoof is being changed
     */
    private static void customise(JPanel panel2, JPanel panel3, Horse horse, HorseInfo theHorse, int[] horsesAttributes, int whichAttrbute, JButton back){
        Integer[] attributes = {1,2};
        JComboBox<Integer> attribute = new JComboBox<>(attributes);

        attribute.addActionListener(f -> {
            int number = (int) attribute.getSelectedItem();
            setAttributes(whichAttrbute, horsesAttributes, number, theHorse);
            horse.setHS(horsesAttributes[1], horsesAttributes[2]);//doesnt update for some reason
            dynamicHorseUpdates(panel3, horse, back);
        });
        panel2.add(attribute);
    }

    /**
     *sets the new stats
     * 
     * 
     * @param number is the new stat
     */
    private static void setAttributes(int whichAttrbute, int[] horsesAttributes, int number, HorseInfo theHorse){
        if (whichAttrbute == 1){
            horsesAttributes[whichAttrbute] = number;
        }
        else{
            horsesAttributes[2] = number;
        }
        theHorse.set2Stats(horsesAttributes);
    }

    /**
     *dynamically updates panel3 depending on which attributes the user selects
     *just display speed, stamina and the horses cooldown as the user changes the customisation
     *so i need to pass the breed, hoof and saddle
     *or i can obtain it by accessing the class by using .get methods
     * 
     * 
     * @param back button that redirects use to panel2
     */
    private static void dynamicHorseUpdates(JPanel panel3, Horse horse, JButton back){
        panel3.removeAll();
        double temp = Math.round(horse.getSpeed() * 100.0) / 100.0;
        panel3.add(new JLabel("current speed is "+ temp));
        temp = Math.round(horse.getStamina() * 100.0) / 100.0;
        panel3.add(new JLabel("current stamina is "+temp));
        temp = Math.round(horse.getCooldown() * 100.0) / 100.0;
        panel3.add(new JLabel("current cooldown is "+temp));
        temp = 0.25;
        calculateFall(temp, horse);
        panel3.add(new JLabel("minimum falling chance "+ temp*100 + "%"));
        temp = 0.01 + 0.07;
        panel3.add(new JLabel("maximum falling chance "+ temp*100 + "%"));
        panel3.add(back);
        repaint(panel3);
        //just display speed, stamina and the horses cooldown as the user changes the customisation
        //so i need to pass the breed, hoof and saddle
        //or i can obtain it by accessing the class by using .get methods
    }

    private static void calculateFall(double temp, Horse horse){
        if (horse.getHoof() == 1) {
            temp *= 1.35;
        }
        else{
            temp *= .85;
        }
    }

    private static void repaint(JPanel panel){
        panel.revalidate();
        panel.repaint();
    }
}
