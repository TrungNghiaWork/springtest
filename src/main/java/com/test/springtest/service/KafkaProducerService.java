package com.test.springtest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String defaultTopic;

    public List<String> pushBatch(String topic, String message, int count) {
        String targetTopic = (topic == null || topic.isBlank()) ? defaultTopic : topic;
        List<String> results = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            String payload = message + " - message " + i;
            kafkaTemplate.send(targetTopic, payload)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Push kafka failed: {}", payload, ex);
                        } else {
                            log.info("Pushed to topic {} partition {} offset {}: {}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset(),
                                    payload);
                        }
                    });
            results.add(payload);
        }
        return results;
    }
}
