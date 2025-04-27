//just a temporary holder for the horse class
class HorseInfo {
    String name;
    int[] stats = new int[3];//breed, hoof, saddle

    public HorseInfo(String name){
        this.name = name;   
    }

    public void setBreed(int breed){
        this.stats[0] = breed;
    }
    
    /**
     * changes the stats using for loop
     * 
     * 
     */
    public void set2Stats(int[] stats){
        for (int i = 1; i < stats.length; i++) {
            if (stats[i] > 0) {
                this.stats[i] = stats[i];
            }
            //this in method because error turning attributes to 0 when customising
            else{
                this.stats[i] = 1;
            }
        }
    }

    public String getName(){
        return this.name;
    }

    public int[] getStats(){
        return this.stats;
    }
}