package com.moklyak.platformserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetCallbackDto {
    String id;
    String callbackURL;
}
