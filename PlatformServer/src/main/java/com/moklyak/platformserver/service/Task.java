package com.moklyak.platformserver.service;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
    Instant startInstant;
    String id;
    String ip;
    String[] args;
    Session s;
}
