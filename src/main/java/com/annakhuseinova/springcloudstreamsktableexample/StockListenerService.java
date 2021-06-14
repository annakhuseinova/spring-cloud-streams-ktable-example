package com.annakhuseinova.springcloudstreamsktableexample;

import com.annakhuseinova.springcloudstreamsktableexample.bindings.StockListenerBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableBinding(StockListenerBinding.class)
public class StockListenerService {

    @StreamListener("stock-input-channel")
    public void process(KTable<String, String> input){
        input.filter((key, value)-> key.contains("HDFCBANK"))
                // You cannot send a KTable to a Kafka topic, you should convert it to a KStream
                // Also foreach() and to() methods are not available in the KTable
                .toStream()
                .foreach((key, value)-> System.out.println("Key = " + key + "Value = " + value));
    }
}
