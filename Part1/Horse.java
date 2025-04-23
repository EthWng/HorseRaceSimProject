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
    }
    
    //Other methods of class Horse
    public void fall()
    {
        
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
        
    }
    
    public boolean hasFallen()
    {
        
    }

    public void moveForward()
    {
        
    }

    public void setConfidence(double newConfidence)
    {
        this.confidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
}