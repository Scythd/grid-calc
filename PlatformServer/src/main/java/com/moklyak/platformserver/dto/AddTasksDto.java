package com.moklyak.platformserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddTasksDto {
    String id;
    List<String[]> tasks;
}
