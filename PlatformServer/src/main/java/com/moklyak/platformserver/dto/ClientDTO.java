package com.moklyak.platformserver.dto;

import lombok.Data;

@Data
public class ClientDTO {
    Double maxClockSpeed;
    Integer threadCount;
    Double bandwidth;
    int port;
}
