package com.amigos.awsuploadimage.exceptions;

import java.util.List;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message){
        super(message);
    }

    private List<String> myStrings;

    public DuplicateRecordException(List<String> s) {
        myStrings = s;
    }

    public List<String> getMyStrings() {
        return myStrings;
    }

    public String getMessage() {
        return "There are some file name is existed: " + String.join(",", myStrings);
    }

}
