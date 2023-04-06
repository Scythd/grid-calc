package com.moklyak.spreader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@AllArgsConstructor
public class SetCallbackDto {
    String id;
    String callbackURL;
}
