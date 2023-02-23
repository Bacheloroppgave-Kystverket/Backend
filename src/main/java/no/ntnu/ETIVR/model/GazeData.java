package no.ntnu.ETIVR.model;


public class GazeData {
    private String locationID;
    private int fixations;
    private float fixationDuration;

    public GazeData(String locationID) {
        this.locationID = locationID;
    }

    public void incrementFixation() {
        fixations++;
    }

    public void addTime() {
        long time = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        int changeInTime = (int) (time-currentTime);
        fixationDuration += changeInTime;
    }

    public String getLocationID() {
        return locationID;
    }

    public int getFixations() {
        return fixations;
    }

    public float getFixationDuration() {
        return fixationDuration;
    }
}
