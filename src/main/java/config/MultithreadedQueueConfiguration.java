package config;

import model.MultithreadedQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultithreadedQueueConfiguration {
    @Bean
    public MultithreadedQueue multithreadedQueue() {
        //fake logic here
        return new MultithreadedQueue();
    }
}
