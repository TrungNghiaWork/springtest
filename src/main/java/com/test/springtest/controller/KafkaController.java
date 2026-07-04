package com.test.springtest.controller;

import com.test.springtest.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/push")
    public Map<String, Object> push(
            @RequestParam(required = false) String topic,
            @RequestParam(defaultValue = "hello kafka") String message) {

        List<String> sent = kafkaProducerService.pushBatch(topic, message, 10);
        return Map.of(
                "topic", (topic == null || topic.isBlank()) ? "default (app.kafka.topic)" : topic,
                "count", sent.size(),
                "messages", sent
        );
    }
}
