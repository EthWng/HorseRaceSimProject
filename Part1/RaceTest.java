public class RaceTest {
    public static void main(String[] args) {
        int distance = 10;
        testNormalRace(distance);
        System.out.println("");
        testMissingHorse1(distance);
        testMissingHorse2(distance);
        test1Horse(distance);
    }

    //testing the race using with 3 horses
    public static void testNormalRace(int distance) {
        System.out.println("=== Test: Normal Race ===");
        Race race = new Race(distance, 5);
        Horse h1 = new Horse('A', "Name1", 1);
        Horse h2 = new Horse('B', "Name2", 0.85);
        Horse h3 = new Horse('C', "Name3", 0.7);

        race.addHorse(h1, 1);
        race.addHorse(h2, 2);
        race.addHorse(h3, 3);

        race.startRace();  // should show a proper race run
    }

    //testing if it works if missing 1 horse at end
    public static void testMissingHorse1(int distance) {
        System.out.println("=== Test: Missing Race ===");
        Race race = new Race(distance, 2);
        Horse h1 = new Horse('A', "Name1", 0.9);
        Horse h2 = new Horse('B', "Name2", 0.85);

        race.addHorse(h1, 1);
        race.addHorse(h2, 2);

        race.startRace();  
    }

    //testing if it works if missing 1 horse at start
    public static void testMissingHorse2(int distance) {
        System.out.println("=== Test: Missing Race ===");
        Race race = new Race(distance, 1);
        Horse h2 = new Horse('A', "Name1", 0.9);
        Horse h3 = new Horse('C', "Name3", 0.7);

        race.addHorse(h2, 2);
        race.addHorse(h3, 3);

        race.startRace();
    }

    //testing if it works if theres only 1 horse 
    public static void test1Horse(int distance) {
        System.out.println("=== Test: Missing2 Race ===");
        Race race = new Race(distance, 4);
        Horse h1 = new Horse('A', "Name1", 0.9);

        race.addHorse(h1, 1);

        race.startRace();
    }
}