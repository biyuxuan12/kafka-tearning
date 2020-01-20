package com.example.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
@Component
public class kafkaConsumer {
    static Properties props = new Properties();
    static KafkaConsumer<String, String> consumer;
    static {
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "group0");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "0");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test1"));
        System.out.println("consummer build done");
    }

    public static ArrayList<String> receiveKafkaMessage() {
        ArrayList<String> replayMap = new ArrayList<String>();
        long temp1 = 0;
        ConsumerRecords<String, String> records = consumer.poll(10);
        for (ConsumerRecord<String, String> record : records) {
            String key = record.key();
            if (temp1 == 0) {
                temp1 = Long.parseLong(key);
            }
            String value = record.value();
            replayMap.add(Long.parseLong(key) - temp1 + "," + value);
        }
        return replayMap;
    }

}
