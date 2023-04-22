package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;

import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for trackable objects
 */
@RestController
@RequestMapping("/trackableObject")
@CrossOrigin
public class TrackableObjectController {

    private final TrackableObjectsService trackableObjectsService;

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
