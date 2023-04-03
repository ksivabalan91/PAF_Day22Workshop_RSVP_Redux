package paf.week1.day22workshoprsvpredux;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Utils {
    public static JsonArray toJsonArray(Object object){        
        ObjectMapper objmapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objmapper.setDateFormat(df);
        try {
            String jsonString = objmapper.writeValueAsString(object);
            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            return jsonReader.readArray();
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            JsonArray jsonArr = Json.createArrayBuilder().add("Object notmapped correctly").build();
            return jsonArr;
        }
    }
    
    public static JsonObject toJsonObject(Object object){
        ObjectMapper objmapper = new ObjectMapper();        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        objmapper.setDateFormat(df);
        try {
            String jsonString = objmapper.writeValueAsString(object);
            JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
            return jsonReader.readObject();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            JsonObject jsonObject = Json.createObjectBuilder().add("message", "Object not mapped correctly").build();
            return jsonObject;
        }
    }
}
