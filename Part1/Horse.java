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
    private char symbol;
    private int distance;
    private boolean flag; //if true shows x char else
    private double confidence;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = horseConfidence;
        this.flag = false;
        checkConfidence();
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
    
    public int getDistanceTravelled()
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

    public void moveForward()
    {
        this.distance++;
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