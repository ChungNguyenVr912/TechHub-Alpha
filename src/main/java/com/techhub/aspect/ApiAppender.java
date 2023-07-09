package com.techhub.aspect;

import com.techhub.payload.request.LogData;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiAppender extends AppenderSkeleton {
    private final RestTemplate restTemplate;
    private String logApiUrl = "https://discord.com/api/webhooks/1126762033522679879/luZP4vlPW55-jLhQ4Z10RcNSDsF8R2saA_lhsRoQkoPiMljHWsOmQayWn8h5EG-jzdLv";

    public ApiAppender() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected void append(LoggingEvent event) {
        System.out.println(event);
        String message = event.getMessage().toString();
        String level = event.getLevel().toString();
        long timestamp = event.getTimeStamp();
        try {
//            String jsonPayload = String.format("{\"message\": \"%s\", \"level\": \"%s\", \"timestamp\": %d}", message, level, timestamp);
            LogData logData = new LogData(message, level, timestamp);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<LogData> content = new HttpEntity<>(logData, headers);

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(logApiUrl, content, Object.class);
            // Handle API response
        } catch (Exception e) {
            // Handle if failed to sent log
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
