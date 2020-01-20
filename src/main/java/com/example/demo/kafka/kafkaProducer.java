package com.example.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Component
public class kafkaProducer {
    static Properties props;
    public static  Producer<String, String> producer;

    static {
        props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");  // 仅当所有Replica确认后，才认为记录提交成功
        props.put("retries", 0);   // 不重试，注意重试可能引起重复消息
        props.put("linger.ms", 0); // 即使缓冲区有空间，批次也可能立即被发送，此配置引入延迟
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public  void sendKafkaMessage(String message)
    {
        String topic="test1";
        String key=Long.toString( System.currentTimeMillis());
        ProducerRecord<String, String> record = new ProducerRecord<>( topic, key, message);
        producer.send( record );
    }
}
