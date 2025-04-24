import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.Random;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse[] laneHorse;//var containing all horses
    private int numberOfLanes;//allows more lanes
    private int season;//random season
    private double weatherChance;//chance of a weather event depends on season 
    private int weather;//final weather event


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     * @param lanes the number of lanes of the racetrack
     */
    public Race(int distance, int lanes)
    {
        // initialise instance variables
        raceLength = distance;
        laneHorse = new Horse[lanes];
        numberOfLanes = lanes;
        weather();
        for (int i = 0; i < lanes; i++) {
            laneHorse[i] = null;
        }
    }


    /**
     * randomises the season and weather
     * the weather depends on the season e.g
     * if season is jan, feb or dec its more likely to be icy or dry
     * if season is mar, oct or nov its more likely to be muddy
     * else its more likely to be dry and impossible to be icy
     * 
     * 
     */
    private void weather(){
        season = (int)(Math.random() * 12) + 1;
        weatherChance = (Math.random() * 30);
        //higher chance to be icy
        if (season < 3 || season > 10) {
            odds(7,11,30);
        }
        //higher chance to be muddy
        else if (season < 4 || season > 8){
            odds(5,25,30);
        }
        //higher chance to be dry
        else{
            odds(21,30,0);//icy odds impossible
        }
    }


    /**
     * sets weather var depending on odds
     * 
     * @param dryOdds the odds that weather is dry
     * @param muddyOdds odds that weather is rainy
     * @param icyOdds odds that weather is icy
     */
    private void odds(int dryOdds, int muddyOdds, int icyOdds){
        if (weatherChance < dryOdds) {
           weather = 1; 
        }
        else if (weatherChance < muddyOdds) {
            weather = 2;
        }
        else if (weatherChance < icyOdds) {
            weather = 3;
        }
        System.out.println(season);
        System.out.println(weather);
    }


    /**
     *changes the confidence of all horses once the program has started
     * 
     * 
     * @param theHorse current horses confidence will change
     */
    private void confidenceChange(Horse theHorse){
        //weather effects
        //dry makes the move normally and the risk of falling decreases a lot, confidence increase
        switch (weather) {
            case 1:
                theHorse.setConfidence(Math.round((theHorse.getConfidence()+.1) * 100.0) / 100.0);
                break;
            case 2:
                theHorse.setConfidence(Math.round((theHorse.getConfidence()-.1) * 100.0) / 100.0);
                break;
            default:
                theHorse.setConfidence(Math.round((theHorse.getConfidence()-.2) * 100.0) / 100.0);
                break;
        }
    }
    

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {

        if (laneNumber > numberOfLanes) {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
        else{
            laneHorse[laneNumber-1] = theHorse;
            confidenceChange(theHorse);
        }
    }
    

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        ArrayList<Horse> winners = new ArrayList<Horse>();//holds the winners of the race
        int nullHorses = laneHorse.length;//contains the max possible null horses
        
        //reset all the lanes (all horses not fallen and back to 0). 
        for (Horse horse : laneHorse) {
            if (horse != null) {
                horse.goBackToStart();
                nullHorses--;//if its not null counter goes down
            }
        }
                      
        while (!finished && nullHorses < laneHorse.length-1)
        {
            //move each horse
            for (Horse horse : laneHorse) {
                moveHorse(horse);
            }
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            for (Horse horse : laneHorse) {
                if (raceWonBy(horse))
                {
                    finished = true;
                    winners.add(horse);
                }
            }
            
            if (horsesFallen(laneHorse)){
                finished = true;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
        }
        
        if (finished){
            if (winners.size() == 0){
                ArrayList<Horse> furthest = new ArrayList<Horse>();

                for (int i = 0; i < laneHorse.length; i++) {
                    if (laneHorse[i] != null){
                        furthest.add(laneHorse[i]);
                        break;
                    }
                }

                //finds furthest travelled horse
                for (int i = 1; i < laneHorse.length; i++) {
                    Horse currentHorse = laneHorse[i];
                    int furthestDistance = furthest.get(0).getDistanceTravelled();
                    if (currentHorse != null){
                        if (currentHorse.getDistanceTravelled() == furthestDistance){
                            furthest.add(laneHorse[i]);
                        }
                        else if (currentHorse.getDistanceTravelled() > furthestDistance) {
                            furthest.clear();
                            furthest.add(laneHorse[i]);
                        }
                    }
                }

                System.out.println("All Horses have fallen");

                if (furthest.size() == 1) {
                    System.out.println(furthest.get(0).getName() + " has made it the furthest and therefore wins");
                }
                else{
                    System.out.print("It's a tie! Horses that made it the furthest: ");
                    connective(furthest);
                }
            }
            else if (winners.size() == 1) {
                System.out.print("And the winner is... "+winners.get(0).getName()+" !");
            }
            else{
                System.out.print("And its a Tie between... ");
                connective(winners);
            }
        }
        else{
            System.out.println("Please enter more than 1 horse");
        }
    }


    /**
     *prints out which horses have tied
     *prints name, name or name and name depending 
     * 
     * 
     * @param tying arraylist holding the horses that have "won" the race
     */
    private void connective(ArrayList<Horse> tying){
        for (int i = 0; i < tying.size(); i++) {
            System.out.print(tying.get(i).getName());
            if (i < tying.size() - 2) {
                System.out.print(", ");
            }
            else if (i == tying.size() - 2){
                System.out.print(" and ");
            }
        }
        System.out.println("!");
    }


    /**
     *checks if all horses have fallen
     *returns false if there are horses still up
     *if all but one horse has fell the horse wins
     * 
     * 
     * @param all array containing all the horses in the race
     */
    private boolean horsesFallen(Horse[] all){
        int fallenAmount = 0;

        for (Horse horse : all) {
            if (horse == null || horse.hasFallen()) {
                fallenAmount++;
            }
        }

        return (fallenAmount == all.length);
    }
    
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (theHorse != null && !theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                if (weather == 1) {
                    theHorse.moveForward();
                }
                else if (weather == 2 && Math.random() <= 0.75) {
                    theHorse.moveForward();
                }
                else{
                    theHorse.moveForward();
                    theHorse.moveForward();
                }
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }

            if (theHorse.getDistanceTravelled() == raceLength)
            {
                theHorse.setConfidence(Math.round((theHorse.getConfidence()+.1) * 100.0) / 100.0);
            }
        }
    }
       
    
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse != null && theHorse.getDistanceTravelled() >= raceLength && !theHorse.hasFallen())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    

    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        for (Horse horse : laneHorse) {
            printLane(horse);
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    

    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore;
        if (theHorse != null){
            if (theHorse.getDistanceTravelled() > raceLength) {
                spacesBefore = raceLength;
            }
            else{
                spacesBefore = theHorse.getDistanceTravelled();
            }
            int spacesAfter = raceLength - theHorse.getDistanceTravelled();
            
            //print a | for the beginning of the lane
            System.out.print('|');
            
            //print the spaces before the horse
            multiplePrint(' ',spacesBefore);
            
            //if the horse has fallen then print dead
            //else print the horse's symbol
            if(theHorse.hasFallen())
            {
                System.out.print('\u2322');
            }
            else
            {
                System.out.print(theHorse.getSymbol());
            }
            
            //print the spaces after the horse
            multiplePrint(' ',spacesAfter);
            
            //print the | for the end of the track
            System.out.print("| "+theHorse.getName().toUpperCase()+" (Current confidence "+theHorse.getConfidence()+")");
        }
        else{
            System.out.print('|');
            spacesBefore = raceLength+1;
            multiplePrint(' ',spacesBefore);
            System.out.print("| ");
        }
    }
        

    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}
