public class RaceTest {
    public static void main(String[] args) {
        int distance = 10;
        testNormalRace(distance);
        System.out.println("");
    }

    //testing the race using with 3 horses
    public static void testNormalRace(int distance) {
        System.out.println("=== Test: Normal Race ===");
        Race race = new Race(distance);
        Horse h1 = new Horse('A', "Name1", 1);
        Horse h2 = new Horse('B', "Name2", 0.85);
        Horse h3 = new Horse('C', "Name3", 0.7);

        race.addHorse(h1, 1);
        race.addHorse(h2, 2);
        race.addHorse(h3, 3);

        race.startRace();  // should show a proper race run
    }
}