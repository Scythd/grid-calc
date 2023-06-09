package com.moklyak.client.configuration;

import com.moklyak.client.dto.ClientDTO;
import com.moklyak.client.feign.PlatformClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.time.Instant;


@Component
public class MyInitializingBean implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(MyInitializingBean.class);
    @Value("${server.port}")
    private int port;
    @Autowired
    private PlatformClient platformClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setThreadCount(Runtime.getRuntime().availableProcessors());
        clientDTO.setBandwidth(getBandwidth());
        clientDTO.setMaxClockSpeed(getMaxClockSpeed());
        clientDTO.setPort(port);
        platformClient.addClient(clientDTO);

        Instant a, b;
        a = Instant.now();
        platformClient.test().getBody();
        b = Instant.now();
        log.info(b.toEpochMilli() - a.toEpochMilli() + "");

    }

    private Double getMaxClockSpeed() throws Exception{
        if (System.getProperty("os.name").startsWith("Windows")){
            String c = "wmic cpu get maxclockspeed";
            Process p = Runtime.getRuntime().exec(c);
            BufferedReader r = p.inputReader();
            r.readLine();
            r.readLine();
            return Double.parseDouble(r.readLine());

        }
        return null;
    }

    private Double getBandwidth() throws Exception {
        if (System.getProperty("os.name").startsWith("Windows")){
            String c = "typeperf -sc 1 \"\\Network Interface(*)\\Current Bandwidth\"";
            Process p = Runtime.getRuntime().exec(c);
            BufferedReader r = p.inputReader();
            r.readLine();
            r.readLine();
            String line = r.readLine();
            int i = line.indexOf(",");
            line = line.substring(i+2);
            i = line.indexOf("\"");
            return Double.parseDouble(line.substring(0, i));
        }
        return null;
    }
}