package com.emadSolutions.workout_tracker.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON

public class SuccessResponse<T> {

    private ResponseStatus status ;
    private String message ;
    private T data; // Generic field
    private List<T> listOfData;

    public SuccessResponse(ResponseStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public SuccessResponse(ResponseStatus status, T data) {
        this.status = status;
        this.data = data;
    }
    public SuccessResponse(ResponseStatus status, List<T> data) {
        this.status = status;
        this.listOfData = data;
    }



    public enum ResponseStatus {
        success,
        failed
    }
}
