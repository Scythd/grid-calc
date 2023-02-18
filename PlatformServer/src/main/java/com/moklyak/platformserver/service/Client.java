package com.moklyak.platformserver.service;

import lombok.Data;

@Data
public class Client {
    // ip
    String accessIP;
    String accessPort;
    Double maxClockSpeed;
    Integer threadCount;
    Double bandwidth;
}
