package no.ntnu.ETIVR.controllers;


import java.util.List;

import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for trackable objects
 */
@RestController
@RequestMapping("/trackableObject")
@CrossOrigin
public class TrackableObjectController {

    private final TrackableObjectsService trackableObjectsService;

    /**
     * Makes an instance of trackable objects service class
     * @param trackableObjectsService TrackableObjectsService
     */
    public TrackableObjectController(TrackableObjectsService trackableObjectsService) {
        this.trackableObjectsService = trackableObjectsService;
    }

    /**
     * Get all available data
     * @return list of trackable objects
     */
    @GetMapping
    public List<TrackableObject> getAll() {
        return trackableObjectsService.getAllTrackableObjects();
    }
}
