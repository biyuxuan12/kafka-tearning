import java.util.Properties
import org.apache.kafka.clients.producer.KafkaProducer

class ProducerForTest(){
    fun configProducer():Properties{
        props = Properties()
        props.put("bootstrap.servers", "127.0.0.1:9092")
        props.put("acks", "all")  // 仅当所有Replica确认后，才认为记录提交成功
        props.put("retries", 0)   // 不重试，注意重试可能引起重复消息
        props.put("linger.ms", 0) // 即使缓冲区有空间，批次也可能立即被发送，此配置引入延迟
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
       return props
    }

    fun CreateProducer(props:Properties):KafkaProducer{



    }


}
