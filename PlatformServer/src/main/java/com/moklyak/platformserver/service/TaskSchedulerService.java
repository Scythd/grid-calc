package com.moklyak.platformserver.service;

import com.moklyak.platformserver.job.TaskSchedulerJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulerService {

    @Autowired
    private TaskSchedulerJob job;



    public void addSession(Session s) {
        job.getSessionsToComplete().add(s);
    }

    public void removeSession(Session s) {
        job.getSessionsToComplete().remove(s);
    }

    public Task taskComplete(String id) {
        Task t =  job.taskComplete(id);
        if (t != null)
            t.getS().taskComplete(t);
        return t;
    }
}
