package no.ntnu.ETIVR.model;


public class GazeData {
    private String locationID;
    private int fixations;
    private float fixationDuration;

    /**
     * Make an instance of GazeData
     * @param locationID location ID
     */
    public GazeData(String locationID) {
        this.locationID = locationID;
    }

    /**
     * Increment fixation
     */
    public void incrementFixation() {
        fixations++;
    }

    /**
     * Adding delta time to fixation duration
     */
    public void addTime() {
        long time = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        int changeInTime = (int) (time-currentTime);
        fixationDuration += changeInTime;
    }

    /**
     * Get location ID
     * @return location ID
     */
    public String getLocationID() {
        return locationID;
    }

    /**
     * Get fixations
     * @return fixation
     */
    public int getFixations() {
        return fixations;
    }

    /**
     * Get fixation duration
     * @return fixation duration
     */
    public float getFixationDuration() {
        return fixationDuration;
    }
}
