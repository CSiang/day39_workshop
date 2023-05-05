package day39_workshop.server.Models;

import java.io.Serializable;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Comment implements Serializable {
    
    private String charId;
    private String comment;

    public String getCharId() {
        return charId;
    }
    public void setCharId(String charId) {
        this.charId = charId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Comment [charId=" + charId + ", comment=" + comment + "]";
    }

    public static Comment create(Document d){

        Comment c = new Comment();
        c.setCharId(d.getObjectId("charId").toString());
        c.setComment(d.getString("comment"));

        return c;
    }

    public JsonObject toJson(){
        
        return Json.createObjectBuilder()
                   .add("charId", getCharId())
                   .add("comment", getComment())
                   .build();
    }
    

}
