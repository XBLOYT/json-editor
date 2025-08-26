package com.xbl.jsoneditor.domain;

import java.math.BigDecimal;
import java.util.Objects;

import scala.collection.immutable.List;
import scala.collection.mutable.LinkedHashMap;

public class JsonNode {
    //Structure
    private long id;
    private JsonNode parent;
    private JsonType type;
    private LinkedHashMap<String, JsonNode> objectChildren;
    private List<JsonNode> arrayChildren;

    //Content
    private String key;
    private String stringValue;
    private BigDecimal numberValue;
    private Boolean booleanValue;
    
    //Init
    public JsonNode(long id){
        this.id = id;

    }

    //Getters
    public <T> T getValue(Class<T> expected){
        Objects.requireNonNull(expected, "expected");

        switch (this.type) {
            case STRING: {
                if (!expected.isAssignableFrom(String.class)) {
                    throw new IllegalArgumentException(
                        "Value type mismatch: node is STRING; requested " + expected.getSimpleName());
                }
                if (this.stringValue == null) {
                    throw new IllegalStateException("Invariant broken: STRING node without value");
                }
                return expected.cast(this.stringValue);
            }
            case NUMBER: {
                if (!expected.isAssignableFrom(BigDecimal.class)) {
                    throw new IllegalArgumentException(
                        "Value type mismatch: node is NUMBER; requested " + expected.getSimpleName());
                }
                if (this.numberValue == null) {
                    throw new IllegalStateException("Invariant broken: NUMBER node without value");
                }
                return expected.cast(this.numberValue);
            }
            case BOOLEAN: {
                if (!expected.isAssignableFrom(Boolean.class)) {
                    throw new IllegalArgumentException(
                        "Value type mismatch: node is BOOLEAN; requested " + expected.getSimpleName());
                }
                if (this.booleanValue == null) {
                    throw new IllegalStateException("Invariant broken: BOOLEAN node without value");
                }
                return expected.cast(this.booleanValue);
            }
            case NULL:
                // Un node NULL no té valor: retornem null (sigui quin sigui 'expected').
                return null;

            case OBJECT:
            case ARRAY:
                // Els contenidors no tenen valor escalar
                throw new IllegalStateException("Container node (" + this.type + ") has no scalar value");

            default:
                throw new IllegalStateException("Unknown JsonType: " + this.type);
        }
    }

    public String getKey(){ 
        return key;
    }

    public boolean isNull(){
        if(type.isValue() && type == JsonType.NULL){
            return true;
        } else{
            return false;
        }
    }

    public LinkedHashMap<String, JsonNode> getObjectChildren(){
        return objectChildren;
    }

    public List<JsonNode> getArrayChildren(){
        return arrayChildren;
    }

    //Setters


}
