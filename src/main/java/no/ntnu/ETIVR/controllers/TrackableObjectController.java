package no.ntnu.ETIVR.controllers;

import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for trackable objects
 */
@RestController
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
    @PostMapping
    public ResponseEntity<String> add(@RequestBody TrackableObject trackableObject) {
        ResponseEntity<String> response;
        System.out.println(trackableObject);
        //trackableObjectsService.addNewTrackableObject(trackableObject)
        if (trackableObject != null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } return response;

    }



}
