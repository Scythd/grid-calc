package com.moklyak.client.dto;

import lombok.Data;

@Data
public class ClientDTO {
    Double maxClockSpeed;
    Integer threadCount;
    Double bandwidth;
    int port;
}
