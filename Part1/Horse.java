import java.util.Scanner;

/**
 * The Horse class represents a horse in the race.
 * It stores the horse's name and position, and has methods to move the horse and check its status.
 * 
 * @author Ethan Wong 
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private int breed; //one of 3, constant
    private static String[] breeds = {"Thoroughbred", "Arabian", "Quarter Horse"};
    private char symbol;
    private double distance;
    private boolean flag; //if true shows x char else
    private double confidence;
    private double speed;
    private double stamina;//constant
    private double staminaLose;
    private double cooldown;//constant
    private double cooldownLose;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence, int breed)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.flag = false;
        checkConfidence();
        this.breed = breed;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        this.flag = true;
        this.confidence = Math.round((this.confidence-.1) * 100.0) / 100.0;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public double getDistanceTravelled()
    {
        return this.distance;
    }
    
    public String getName()
    {
        return this.name;        
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        this.distance = 0;
    }
    
    public boolean hasFallen()
    {
        return this.flag;
    }

    /**
     * horses movement depends on its speed and stamina
     * 
     * 
     * 
     */
    public void moveForward()
    {
        //horses lose stamina while moving
        if (this.staminaLose > 0){
            this.distance += this.speed;
            this.staminaLose-=1;
        }
        //if horseshave no stamina then the horse will move slower and regain its stamina
        else{
            this.distance += this.speed/2.5;
            this.cooldownLose-=2.5;
            if (this.cooldownLose <= 0){
                this.staminaLose = this.stamina;
                this.cooldownLose = this.cooldown;
            }
        }
    }

    public void setConfidence(double newConfidence)
    {
        //lowest confidence
        if (newConfidence <= 0) {
            this.confidence = 0.1; 
        }
        //highest confidence
        else if (newConfidence > 1) {
            this.confidence = 1;
        }
        else{
            this.confidence = newConfidence;
        }
        this.confidence = Math.round(this.confidence * 100.0) / 100.0;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }

    /**
     * sets the horses speed depending on breed
     * sets the horses stamina depending on speed and initial confidence
     * 
     * 
     */
    public void setSpeed(double raceLength){
        for (String breed: breeds){
            if (breeds[this.breed].equals(breed)){
                this.speed = .75;
                setConfidence(this.confidence+.08);
            }
            else if (breeds[this.breed].equals(breed)){
                this.speed = 1;
                setConfidence(this.confidence+.1);
            }
            else{
                this.speed = 1.5;
                setConfidence(this.confidence+.15);
            }
        }
        this.stamina = (raceLength / (this.speed*2*this.confidence));
        this.staminaLose = this.stamina;
        this.cooldown = (this.speed * 1.5);
        this.cooldownLose = this.cooldown;
    }

    /**
     * checks if the horses confidence is within range 
     * range being 0-1
     * uses try catch to prevent errors
     * 
     */
    private void checkConfidence(){
        Scanner scanner = new Scanner(System.in);
        while (getConfidence() > 1 || getConfidence() < 0) {

            System.out.println("please enter another confidence probability within the range 0-1");
            String newConfidence = scanner.nextLine();

            try {
                setConfidence(Double.parseDouble(newConfidence));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a probability in a decimal format E.g 0.1");
            }
        }
    }   
}