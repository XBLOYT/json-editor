package com.xbl.jsoneditor.domain;

public enum JsonType {
    OBJECT,
    ARRAY,
    STRING,
    NUMBER,
    BOOLEAN,
    NULL;


    public boolean isContainer(){
        return this == OBJECT || this == ARRAY;
    }

    public boolean isValue(){
        return this == STRING || this == NUMBER || this == BOOLEAN || this == NULL;
    }

}
