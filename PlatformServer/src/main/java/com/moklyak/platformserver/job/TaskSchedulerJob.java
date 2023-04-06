package com.moklyak.platformserver.job;



import com.moklyak.platformserver.dto.InputDTO;
import com.moklyak.platformserver.feign.EvaluationFeignTemplate;
import com.moklyak.platformserver.service.Client;
import com.moklyak.platformserver.service.ClientService;
import com.moklyak.platformserver.service.Session;
import com.moklyak.platformserver.service.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TaskSchedulerJob {

    private final Logger log = LoggerFactory.getLogger(TaskSchedulerJob.class);

    Map<String, Task> tasks = new ConcurrentHashMap<>();
    @Autowired
    ClientService clientService;

    @Autowired
    EvaluationFeignTemplate evaluationFeignTemplate;
    Queue<Session> sessionsToComplete = new ArrayDeque<>();
    @Value("${aplication.timeout}")
    private long TIMEOUT;

    @Scheduled(fixedRate = 1000)
    public void run() {
        if (!sessionsToComplete.isEmpty()){
            Session s = sessionsToComplete.poll();

            List<Client> clients = clientService.getFreeClients();
            for (Client c : clients) {
                c.setBusy(true);
                InputDTO dto = new InputDTO();
                File jarFile = new File(s.getTempStoredFilePath().toUri());
                dto.setJarFile(jarFile);
                dto.setId(UUID.randomUUID().toString());

                String[] tt = s.getTaskQueue().poll();
                if (tt == null) return;
                StringBuilder sb = new StringBuilder();
                for (String ss : tt) {
                    sb.append(ss);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                dto.setArgs(sb.toString());
                Task t = new Task(Instant.now(), dto.getId(), c.getAccessIP(), tt, s);
                try {
                    log.info("sending processing: " + dto.getId());
                    ResponseEntity<Boolean> re = evaluationFeignTemplate.calc(c.getURI(), dto);
                    if (Boolean.TRUE.equals(re.getBody())) {
                        tasks.put(t.getId(), t);
                    } else {
                        s.getTaskQueue().add(tt);
                    }
                } catch (Exception ex) {
                    s.getTaskQueue().add(tt);
                    log.info(ex.toString());
                }

            }


            sessionsToComplete.add(s);
        }
    }

    @Scheduled(fixedRate = 1000)
    public void reRunner() {
        Instant before = Instant.now().minusSeconds(TIMEOUT);
        tasks.values().stream()
                .filter(x -> x.getStartInstant().isBefore(before))
                .forEach(x -> {
                    log.warn("returning to queue task: " + x.getId());
                    tasks.remove(x.getId(), x);
                    x.getS().getTaskQueue().add(x.getArgs());
                });

    }
    public Queue<Session> getSessionsToComplete() {
        return sessionsToComplete;
    }


    public Task taskComplete(String id) {

        return tasks.remove(id);
    }
}
