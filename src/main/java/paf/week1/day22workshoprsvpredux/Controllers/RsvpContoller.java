package paf.week1.day22workshoprsvpredux.Controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import paf.week1.day22workshoprsvpredux.Utils;
import paf.week1.day22workshoprsvpredux.Models.Rsvp;
import paf.week1.day22workshoprsvpredux.Services.RsvpService;

@RestController
@RequestMapping("/api")
public class RsvpContoller {

    @Autowired
    private RsvpService rsvpSvc;

    private Logger logger = LoggerFactory.getLogger(RsvpContoller.class);
    // private Logger logger = Logger.getLogger(getClass());


    @GetMapping(path="/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRsvp(){
        logger.info("Calling rsvpSvc.getAllRsvp");
        List<Rsvp> rsvps= rsvpSvc.getAllRsvp();
        logger.info("rsvpSvc query complete");
        logger.info("Converting to Json array");
        JsonArray jsonArr = Utils.toJsonArray(rsvps);                
        return ResponseEntity.ok().body(jsonArr.toString());
    }

    @GetMapping(path="/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvpByName(@RequestParam("q") String name){
        logger.info("Calling rsvpSvc.getRsvpByName");
        List<Rsvp> rsvps= rsvpSvc.getRsvpByName(name);
        if(!rsvps.isEmpty()){
            JsonArray jsonArr = Utils.toJsonArray(rsvps);                
            return ResponseEntity.ok().body(jsonArr.toString());
        }else{
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "no rsvps found").build();
            return ResponseEntity.status(404).body(jsonObject.toString());
        }
    }
    
    @PostMapping(path="/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertRsvp(@ModelAttribute Rsvp rsvp){
        logger.info("Calling rsvpSvc.upsertRsvp");
        int insertedRows = rsvpSvc.upsertRsvp(rsvp);
        
        if(insertedRows>0){
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "Successfully inserted %d entries".formatted(insertedRows)).build();
            return ResponseEntity.status(201).body(jsonObject.toString());
        }else{
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "insert failed").build();
            return ResponseEntity.badRequest().body(jsonObject.toString());
        }        
    }
    @PutMapping(path="/rsvp/{email}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertRsvp(@PathVariable("email") String email, @ModelAttribute Rsvp rsvp){
        rsvp.setEmail(email);

        logger.info("Calling rsvpSvc.updateRsvp");
        int insertedRows = rsvpSvc.updateRsvp(rsvp);

        if(insertedRows>0){
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "Successfully updated %d entries".formatted(insertedRows)).build();
            return ResponseEntity.status(201).body(jsonObject.toString());
        }else{
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "update failed, no such email").build();
            return ResponseEntity.badRequest().body(jsonObject.toString());
        }        
    }
    @GetMapping(path="/rsvps/count")
    public ResponseEntity<String> getRsvpCount(){
        int rsvpCount = rsvpSvc.getRsvpCount();        
        JsonObject jsonObject = Json.createObjectBuilder().add("message", "There are %d in RSVPs total".formatted(rsvpCount)).build();        
        return ResponseEntity.status(201).body(jsonObject.toString());
    }
}
