package com.techhub.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LogData {
    private String content;

    public LogData(String message, String level, long timestamp) {
        content = String.format("[**%s**] %s %s", level, new Date(timestamp), message);
    }
}
