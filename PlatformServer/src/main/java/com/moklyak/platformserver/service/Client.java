package com.moklyak.platformserver.service;

import lombok.Data;

import java.net.URI;

@Data
public class Client {
    // ip
    String accessIP;
    String accessPort;
    Double maxClockSpeed;
    Integer threadCount;
    Double bandwidth;
    boolean busy;

    public URI getURI(){
        URI r = URI.create("http://" + accessIP + ":" + accessPort + "/");
        return r;
    }
}
