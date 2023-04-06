package com.moklyak.spreader.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
public class AddTasksDto {
    String id;
    List<String[]> tasks;
}
