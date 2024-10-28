package com.emadSolutions.workout_tracker.DTOs;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL) // Include only non-null fields in JSON

public class UserDataDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Integer age;
    private Float height;
    private Float weight;

}
