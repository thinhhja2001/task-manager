package com.example.taskmanagement.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskManagingRequest {
    String title;
    String description;
    Boolean completed;
}
