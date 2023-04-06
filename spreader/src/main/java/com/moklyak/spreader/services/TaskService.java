package com.moklyak.spreader.services;

import com.moklyak.spreader.controller.CallBackController;
import com.moklyak.spreader.dto.AddTaskDto;
import com.moklyak.spreader.dto.ProgressDto;
import com.moklyak.spreader.dto.SetCallbackDto;
import com.moklyak.spreader.dto.SetJarDto;
import com.moklyak.spreader.feign.ServerFeignTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);
    @Value("${path.executable-jar}")
    String pathToJarFileString;
    @Value("${server.port}")
    String port;
    @Autowired
    ServerFeignTemplate serverFeignTemplate;

    String currId;
    @Value("${task.step}")
    private String step;

    private final BigInteger SEVEN = BigInteger.valueOf(7);

    private boolean isCancelled = false;
    private String id;
    private Executor exec = Executors.newSingleThreadExecutor();

    public void start(int[][] circles) {
        isCancelled = false;

        exec.execute(() -> {
            id = serverFeignTemplate.register().getBody();
            currId = id;
            File f = new File(pathToJarFileString);
            id = serverFeignTemplate.setJar(new SetJarDto(id, f)).getBody();
            String ip = "";
            try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            } catch (Exception ex){

            }
            id = serverFeignTemplate.setCallback(new SetCallbackDto(id, ip + ":" + port + "/callback")).getBody();
            int rows, columns, wLen;
            rows = circles.length;
            columns = circles[0].length;
            wLen = rows * columns;
            BigInteger currDown, currUp, max, step;
            max = SEVEN.pow(wLen);

            if (this.step == null || this.step == ""){
                BigInteger n = BigDecimal.valueOf(Double.parseDouble("0.0021"))
                        .multiply(BigDecimal.valueOf(Double.parseDouble("1.3501")).pow(wLen))
                        .add(BigDecimal.ONE)
                        .round(MathContext.DECIMAL32)
                        .toBigInteger();
                step = max.divide(n);

                log.info("subtask size: " + step.toString());
                log.info("subtask count: " + n.toString());
            } else {
                step = BigInteger.TEN.pow(Integer.parseInt(this.step));
            }


            if (isCancelled){
                return;
            }

            StringBuilder task = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    task.append(circles[i][j]);
                }
            }
            String taskString = task.toString();;

            boolean kostyl = true;

            for (currDown = BigInteger.ZERO; currDown.compareTo(max) == -1; currDown = currUp){
                if (isCancelled){
                    return;
                }
                currUp = currDown.add(step).min(max);
                String[] args = new String[4];
                args[0] = String.valueOf(columns);
                args[1] = taskString;
                args[2] = currDown.toString(7);
                args[3] = currUp.toString(7);
                id = serverFeignTemplate.addTask(new AddTaskDto(id, args)).getBody();
                if (kostyl) {
                    kostyl = false;
                    id = serverFeignTemplate.ready(id).getBody();
                }
            }

            currId = id;
        });
    }

    public void cancel(){ // aka complete early
        isCancelled = true;
        try {
            id = serverFeignTemplate.taskComplete(id).getBody();
        } catch (Exception ex){
            log.info(ex.getMessage());
        }
    }

    public int progress() {
        try {
            ProgressDto dto = serverFeignTemplate.progress(id).getBody();
            int res = 0;
            try {
                res = dto.getCurrentCompletedTaskCount()
                        .multiply(BigInteger.valueOf(100))
                        .divide(dto.getTotalReceivedTaskCount())
                        .intValue();
            } catch (ArithmeticException ex){
                log.error(ex.toString());
            }
            return res;
        } catch (IllegalArgumentException ex){
            log.error(ex.toString());
        }
        return 0;
    }

    public String getCurrId() {
        return currId;
    }
}
