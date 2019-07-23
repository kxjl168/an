package com.kxjl.base.websocket;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//import net.sf.json.JSONObject;

/**
 * Spring Boot 监听器
 */
@Slf4j
@Component
public class StartupRunner implements CommandLineRunner {


    @Autowired
    private ClientConnectSvr clientConnectSvr;
  

    @Override
    public void run(String... args) {
    	clientConnectSvr.startSocketServerAndOther();
    }


}
