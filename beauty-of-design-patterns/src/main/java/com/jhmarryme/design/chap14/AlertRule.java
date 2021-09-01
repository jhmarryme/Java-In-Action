package com.jhmarryme.design.chap14;

import lombok.Builder;
import lombok.Data;

/**
 * AlertRule 存储告警规则，可以自由设置。
 */
public class AlertRule {
    public MatchedRule getMatchedRule(String api) {
        return MatchedRule.builder()
                .maxErrorCount(100)
                .maxTimeoutTps(100)
                .maxTps(100)
                .build();
    }

    @Data
    @Builder
    public static class MatchedRule {
        int maxErrorCount;
        int maxTps;
        int maxTimeoutTps;
    }

}
