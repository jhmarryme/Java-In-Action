package com.jhmarryme.design.chap14;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiStatInfo {
    private String api;
    private long requestCount;
    private long errorCount;
    private long durationOfSeconds;
    private long timeoutCount;
}
