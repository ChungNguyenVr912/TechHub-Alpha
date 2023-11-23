package com.techhub.aspect;

import com.techhub.payload.request.LogData;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiAppender extends AppenderSkeleton {
    private final RestTemplate restTemplate;
    private static final String logApiUrl
            = "https://discord.com/api/webhooks/1126762033522679879/luZP4vlPW55-jLhQ4Z10RcNSDsF8R2saA_lhsRoQkoPiMljHWsOmQayWn8h5EG-jzdLv";

    public ApiAppender() {
        this.restTemplate = new RestTemplate();
        initAppender();
    }

    private void initAppender() {
        PatternLayout layout = new PatternLayout();
        String conversionPattern = "[%-5p] [%d{yyyy-MM-dd HH:mm:ss zZ}] - [%m]%n";
        layout.setConversionPattern(conversionPattern);
        this.setLayout(layout);
        this.activateOptions();
    }

    @Override
    protected void append(LoggingEvent event) {
        String message = layout.format(event);
        String level = event.getLevel().toString();
        long timestamp = event.getTimeStamp();
        try {
//            String jsonPayload = String.format("{\"message\": \"%s\", \"level\": \"%s\", \"timestamp\": %d}", message, level, timestamp);
            LogData logData = new LogData(message, level, timestamp);
            Map<String,String> data = new HashMap<>();
            data.put("content", message);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> content = new HttpEntity<>(data, headers);

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
        return true;
    }
}
