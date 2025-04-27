class HorseInfo {
    String name;
    int[] stats = new int[3];//breed, hoof, saddle

    public HorseInfo(String name){
        this.name = name;   
    }

    public void setBreed(int breed){
        this.stats[0] = breed;
    }
    
    public void set2Stats(int[] stats){
        for (int i = 1; i < stats.length; i++) {
            if (stats[i] > 0) {
                this.stats[i] = stats[i];
            }
            else{
                this.stats[i] = 1;
            }
        }
    }

    public String getName(){
        return this.name;
    }

    /**
     * returns the stats of a specific part of the horse
     * e.g the int of the breed, the hoof or the saddle
     * 
     * 
     */
    public int[] getStats(){
        return this.stats;
    }
}