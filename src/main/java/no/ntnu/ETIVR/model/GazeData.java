package no.ntnu.ETIVR.model;

import javax.persistence.*;
import java.lang.ref.Reference;

@Embeddable
public class GazeData {

    @Column(name = "referencePositionId")
    private long referencePositionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_position_location_id", insertable = false)
    private ReferencePosition referencePosition;

    private int fixations;

    private float fixationDuration;


    public GazeData() {
    }

    /**
     * Make an instance of GazeData
     * @param fixations the amount of fixations.
     * @param fixationDuration the fixation duration
     * @param referencePosition the reference position.
     */
    public GazeData(int fixations, float fixationDuration, ReferencePosition referencePosition) {
        checkIfObjectIsNull(referencePosition, "reference position");
        checkFloat(fixationDuration, "fixation duration");
        checkFloat(fixations, "fixations");
        this.fixations = fixations;
        this.fixationDuration = fixationDuration;
        this.referencePosition = referencePosition;
    }

    /**
     * Checks if a float is above zero.
     * @param numberToCheck the number to check.
     * @param error the error.
     */
    private void checkFloat(float numberToCheck, String error){
        if(numberToCheck < 0){
            throw new IllegalArgumentException("The " + error + " cannot be below zero");
        }
    }

    /**
     * Get location ID
     * @return location ID
     */
    public long getReferencePositionId() {
        return referencePositionId;
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

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
