package no.ntnu.ETIVR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API Controller for trackable objects
 */
@RestController
public class TrackableObjectsController {
    @Autowired
    TrackableObjectsService trackableObjectsService;

    /**
     * Get all available data
     * @return list of trackable objects
     */
    @GetMapping
    public List<TrackableObjects> getAll() {return trackableObjectsService.getAll();}

    /**
     * Get a specific trackable object by name
     * @param name String
     * @return Trackable object with the given name, or status 404
     */
    @GetMapping("/id")
    public ResponseEntity<TrackableObjects> getOne(@PathVariable String name) {
        ResponseEntity<TrackableObjects> response;
        TrackableObjects trackableObjects = trackableObjectsService.findTrackableObjectByName(name);
        if (trackableObjects != null) {
            response = new ResponseEntity<>(trackableObjects, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  response;
    }

    /**
     * Add a trackable object
     * @param trackableObjects trackable object to be added
     * @return HTTP response OK if added, HTTP response BAD REQUEST if not
     */
    @PostMapping
    public ResponseEntity<String> add(@RequestBody TrackableObjects trackableObjects) {
        ResponseEntity<String> response;
        if (trackableObjectsService.addNewTrackableObject(trackableObjects)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } return response;
    }

    /**
     * Delete trackable object
     * @param trackableObjects trackable object to be deleted
     * @return HTTP response OK if deleted, HTTP response NOT FOUND if not found
     */
    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable TrackableObjects trackableObjects) {
        ResponseEntity<String> response;
        if (trackableObjectsService.deleteTrackableObject(trackableObjects)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } return response;
    }

}
