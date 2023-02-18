package com.moklyak.platformserver.controller;


import com.moklyak.platformserver.dto.ClientDTO;
import com.moklyak.platformserver.service.Client;
import com.moklyak.platformserver.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<?> addClient(HttpServletRequest httpRequest, @RequestBody ClientDTO client){
        Client c = new Client();
        c.setBandwidth(client.getBandwidth());
        c.setThreadCount(client.getThreadCount());
        c.setMaxClockSpeed(client.getMaxClockSpeed());
        c.setAccessIP(httpRequest.getRemoteAddr());
        c.setAccessPort(String.valueOf(httpRequest.getRemotePort()));
        clientService.addClient(c);
        return  ResponseEntity.ok().build();
    }

}
