package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

import no.ntnu.ETIVR.model.AdaptiveFeedback;
import no.ntnu.ETIVR.model.TrackableType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller for trackable objects
 */
@RestController
@RequestMapping("/trackableObject")
public class TrackableObjectController {

    //private final TrackableObjectsService trackableObjectsService;

    public TrackableObjectController() {
        //TrackableObjectsService trackableObjectsService
        //this.trackableObjectsService = trackableObjectsService;
    }

//    /**
//     * Get all available data
//     * @return list of trackable objects
//     */
//    @GetMapping
//    public List<TrackableObject> getAll() {return trackableObjectsService.getAll();}
//
//    /**
//     * Get a specific trackable object by name
//     * @param name String
//     * @return Trackable object with the given name, or status 404
//     */
//    @GetMapping("/id")
//    public ResponseEntity<TrackableObject> getOne(@PathVariable String name) {
//        ResponseEntity<TrackableObject> response;
//        TrackableObject trackableObject = trackableObjectsService.findTrackableObjectByName(name);
//        if (trackableObject != null) {
//            response = new ResponseEntity<>(trackableObject, HttpStatus.OK);
//        } else {
//            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return  response;
//    }
//
//    /**
//     * Delete trackable object
//     * @param trackableObject trackable object to be deleted
//     * @return HTTP response OK if deleted, HTTP response NOT FOUND if not found
//     */
//    @DeleteMapping
//    public ResponseEntity<String> delete(@PathVariable TrackableObject trackableObject) {
//        ResponseEntity<String> response;
//        if (trackableObjectsService.deleteTrackableObject(trackableObject)) {
//            response = new ResponseEntity<>(HttpStatus.OK);
//        }
//        else {
//            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } return response;
//    }


    /**
     * Add a trackable object
     * @param trackableObject trackable object to be added
     * @return HTTP response OK if added, HTTP response BAD REQUEST if not
     */
    @PutMapping
    public ResponseEntity<String> add(@RequestBody String body) throws JsonProcessingException {
        ResponseEntity<String> response;
        System.out.println(body);
        //System.out.println(makeFeedback(body));
        System.out.println("pog");
        //trackableObjectsService.addNewTrackableObject(trackableObject)
        if ("" != null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } return response;

    }

    private AdaptiveFeedback makeFeedback(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, AdaptiveFeedback.class);
    }

    public static void main(String[] args) {
        HashMap<TrackableType, Float> maps = new HashMap<>();
        maps.put(TrackableType.CUBE, 10f);
        System.out.println(maps);
    }



}
