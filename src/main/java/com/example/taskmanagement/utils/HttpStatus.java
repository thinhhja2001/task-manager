package com.example.taskmanagement.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class HttpStatus {
    public int statusCode;
    public String message;

    public static HttpStatus userExisted = new HttpStatus(409, "User existed");
    public static HttpStatus incorrectCredential = new HttpStatus(402, "Incorrect credential");
    public static HttpStatus userNotFound = new HttpStatus(403, "User not found");
    public static HttpStatus notFound = new HttpStatus(404, "Resource not found");
    public static HttpStatus success = new HttpStatus(200, "Success");
    public static HttpStatus internalServerError = new HttpStatus(500, "Internal Server Error");
}
