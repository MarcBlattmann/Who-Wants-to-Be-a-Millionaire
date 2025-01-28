package org.example.droplets;

public class prize {
    int points;
    private final int[] prizeList = new int[]{0, 100, 200, 300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000, 1000000};

    public prize(int points) {
        this.points = points;
    }

    public int generate(){
        return prizeList[points];
    }
}
