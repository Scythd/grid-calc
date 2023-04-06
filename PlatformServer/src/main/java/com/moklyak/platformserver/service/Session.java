package com.moklyak.platformserver.service;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
public class Session {

    String id;
    String callbackURL;
    Path tempStoredFilePath;
    Queue<String[]> taskQueue = new ConcurrentLinkedQueue<>();
    BigInteger totalReceivedTaskCount = BigInteger.ZERO;
    BigInteger currentCompletedTaskCount = BigInteger.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setJar(MultipartFile jarFile) {
        String filename = jarFile.getOriginalFilename();
        tempStoredFilePath = Path.of("./temp/" + filename);
        try {
            jarFile.transferTo(tempStoredFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCallbackURL(String url){
        callbackURL = url;
    }
    public void addTask(String[] task) {
        taskQueue.add(task);
        synchronized(this) {
            totalReceivedTaskCount = totalReceivedTaskCount.add(BigInteger.ONE);
        }
    }

    public void addTasks(List<String[]> tasks) {
        taskQueue.addAll(tasks);
        synchronized(this) {
            totalReceivedTaskCount = totalReceivedTaskCount.add(BigInteger.valueOf(tasks.size()));
        }
    }

    public void taskComplete(Task t) {
        currentCompletedTaskCount = currentCompletedTaskCount.add(BigInteger.ONE);
    }
}
