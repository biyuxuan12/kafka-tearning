package com.example.demo.websocket;

import com.example.demo.kafka.kafkaProducer;
import com.example.demo.kafka.kafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

@ServerEndpoint("/websocket")
@Service
public class Websocket {
    private static List<Session> sessionList = new ArrayList<>();
    private final kafkaProducer kafkaProducer ;

    public Websocket(kafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @OnOpen
    public void onOpen(Session session) {
        sessionList.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        if (sessionList.contains(session))
            sessionList.remove(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws AWTException, IOException {
        if (message.equals("replay")) {
            for (String press : kafkaConsumer.receiveKafkaMessage()) {
                Timer timer = new Timer();// 实例化Timer类
                timer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            session.getBasicRemote().sendText(press.split(",")[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, Integer.parseInt(press.split(",")[0]));// 这里百毫秒
            }
            return;
        }


        for (Session item : sessionList) {
            if (item != session) {
                try {
                    item.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        kafkaProducer.sendKafkaMessage(message);
    }
}
