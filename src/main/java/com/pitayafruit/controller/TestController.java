package com.pitayafruit.controller;

import com.longbridge.dify.entity.DifyResponse;
import com.longbridge.dify.entity.FailedResponse;
import com.pitayafruit.resp.BlockResponse;
import com.pitayafruit.resp.StreamResponse;
import com.pitayafruit.service.DifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @Value("${dify.key.test}")
    private String testKey;

    private final DifyService difyService;

    // http://127.0.0.1:8080/api/test/block
    @GetMapping("/block")
    public String block() {
        String query = "鲁迅和周树人什么关系？";
        Object blockResponse = difyService.blockingMessage(query, 0L, testKey);
        if(blockResponse instanceof DifyResponse){
            return ((DifyResponse)blockResponse).getAnswer();
        }else{
            return ((FailedResponse)blockResponse).getMessage();
        }
    }

    @GetMapping("/blocking")
    public String blocking() {
        String query = "鲁迅和周树人什么关系？";
        BlockResponse blockResponse = difyService.blockingMessageDeprecated(query, 0L, testKey);
        return blockResponse.getAnswer();
    }

    // http://127.0.0.1:8080/api/test/stream
    @GetMapping("/stream")
    public Flux<DifyResponse> stream() {
        String query = "鲁迅和周树人什么关系？";
        return difyService.streamingMessage(query, 0L, testKey);
    }

    @GetMapping("/streaming")
    public Flux<StreamResponse> streaming() {
        String query = "鲁迅和周树人什么关系？";
        return difyService.streamingMessageDeprecated(query, 0L, testKey);
    }
}
