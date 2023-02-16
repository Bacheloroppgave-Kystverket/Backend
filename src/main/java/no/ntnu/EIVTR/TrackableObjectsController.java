package no.ntnu.EIVTR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TrackableObjectsController {
    @Autowired
    TrackableObjectsService trackableObjectsService;

    @GetMapping
    public List<TrackableObjects> getAll() {return trackableObjectsService.getAll();}

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

    @PostMapping
    public ResponseEntity<String> add(@RequestBody TrackableObjects trackableObjects) {
        ResponseEntity<String> response;
        if (trackableObjectsService.addNewTrackableObject(trackableObjects)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } return response;
    }

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
