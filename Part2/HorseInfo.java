class HorseInfo {
    String name;
    int[] stats = new int[3];//breed, hoof, saddle

    public HorseInfo(String name){
        this.name = name;   
    }

    public void setStats(int[] stats){
        this.stats = stats;
    }

    public String getName(){
        return this.name;
    }

    /**
     * returns the stats of a specific part of the horse
     * e.g the int of the breed, the hoof or the saddle
     * 
     * @param i the index 
     */
    public int getStats(int i){
        return this.stats[i];
    }
}