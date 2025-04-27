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
    private double raceLength;
    private int saddle;
    private int hoof;
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
    public Horse(char horseSymbol, String horseName, int breed, int hoof, int saddle)
    {
        this.symbol = horseSymbol;
        this.name = horseName;
        this.confidence = 0.3;
        this.flag = false;
        this.breed = breed;
        setHoof(hoof);
        setSaddle(saddle); 
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

    public void setRaceLength(double raceLength){
        this.raceLength = raceLength;
        setSpeed();
    }

    public int getHoof(){
        return this.hoof;
    }

    /***
     * makes horse move slower but increases stamina
     * saddle 1 reduces speed by *.90 increases stamina by *1.15
     * saddle 2 reduces speed by *.85 increases stamina by *1.3
     * 
     *
     */
    public void setSaddle(int saddle){
        this.saddle = saddle;
        setSpeed();
    }

    /***
     * makes horse move faste/slower but reduces/increases stability
     * hoof 1 makes horse move faster *1.2 but more unstable *1.35 confidence decreases .02
     * hoof 2 makes horse move slower *0.85 but more stable *0.75 confidence increase by .03
     * 
     *
     */
    public void setHoof(int hoof){
        this.hoof = hoof;
        setSpeed();
    }

    /***
     * sets the horses speed depending on breed
     * sets the horses stamina depending on speed and initial confidence
     * 
     * 
     */
    public void setSpeed(){
        if (this.breed == 1){
            this.speed = .75;
            setConfidence(this.confidence+.08);
        }
        else if (this.breed == 1){
            this.speed = 1;
            setConfidence(this.confidence+.1);
        }
        else{
            this.speed = 1.5;
            setConfidence(this.confidence+.15);
        }


        this.stamina = (this.raceLength / (this.speed*2*this.confidence));

        //hoof can only be 1 or 2
        if (this.hoof == 1) {
            this.speed *= 1.2;
            setConfidence(this.confidence -= .05);
        }
        else{
            this.speed *= .85;
            setConfidence(this.confidence += .04);
        }

        //saddle can only be 1 or 2
        if (this.saddle == 1) {
            this.speed *=.9;
            this.stamina *= 1.15;
            setConfidence(this.confidence += .03);
        }
        else{
            this.speed *=.85;
            this.stamina *= 1.3;
            setConfidence(this.confidence += .04);
        }

        this.staminaLose = this.stamina;
        this.cooldown = (this.speed * 1.5);
        this.cooldownLose = this.cooldown;
    }  
}