package com.moklyak.platformserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Configuration
@EnableScheduling
public class ClientService {

    private List<Client> clients = new CopyOnWriteArrayList<>();

    // need to rework to a normal job
    @Bean
    ClientJob job(){
        return new ClientJob();
    }

    private class ClientJob {
        private ClientJob(){}
        private final Logger log = LoggerFactory.getLogger(ClientJob.class);
        @Scheduled(fixedRate = 5000)
        public void run() {
            for (Client client : clients){
                if (!ClientService.this.healthCheck(client)){
                    //try {
                        clients.remove(client);
                    //} catch (ConcurrentModificationException ex){

                    //}
                    log.info(client.toString());
                }
            }
        }
    }

    // true if healthy
    private boolean healthCheck(Client client) {
        //do healthcheck
        return false;
    }

    public boolean addClient(Client client){
        return clients.add(client);
    }
}
