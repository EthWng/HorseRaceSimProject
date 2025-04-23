import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

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


    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance, int lanes)
    {
        // initialise instance variables
        raceLength = distance;
        laneHorse = new Horse[lanes];
        numberOfLanes = lanes;
        for (int i = 0; i < lanes; i++) {
            laneHorse[i] = null;
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
        String connective;//variable changed depending on amount of winners
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
                if ( raceWonBy(horse))
                {
                    finished = true;
                    for (Horse winner : laneHorse) {
                        if (raceWonBy(winner)) {
                            winners.add(winner);
                        } 
                    }
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
                    for (int i = 0; i < furthest.size(); i++) {
                        System.out.print(furthest.get(i).getName());
                        if (i < furthest.size() - 2) {
                            System.out.print(", ");
                        }
                        else if (i == furthest.size() - 2){
                            System.out.print(" and ");
                        }
                    }
                    System.out.println("!");
                }
            }
            else if (winners.size() == 1){
                System.out.println("And the Winner is... " +winners.get(0).getName()+ " !");
            }
            else if(winners.size() == 2){
                System.out.println("And its a Tie between... "+winners.get(0).getName()+" And "+winners.get(1).getName()+ " !");
            }
            else if(winners.size() == 3){
                System.out.println("And its a Tie between... "+winners.get(0).getName()+", "+winners.get(1).getName()+ " And "+winners.get(2).getName());
            }
        }
        else{
            System.out.println("Please enter more than 1 horse");
        }
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
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
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
        if (theHorse != null && theHorse.getDistanceTravelled() == raceLength)
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
        if (theHorse != null){
            int spacesBefore = theHorse.getDistanceTravelled();
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
            int spacesBefore = raceLength+1;
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
