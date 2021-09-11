package com.karasuno.quizapi.rest;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private int status;

    private String message;

    private long timeStamp;
}
