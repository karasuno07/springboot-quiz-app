package com.karasuno.quizapi.rest;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataResponse {

    private int statusCode;
    private Object content;
}
