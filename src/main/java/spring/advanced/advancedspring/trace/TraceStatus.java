package spring.advanced.advancedspring.trace;

import lombok.Getter;

@Getter
public class TraceStatus {
    private TraceId traceId; // 로그 아이디(트랜잭션 id, level)
    private Long startTimeMs; // 로그 시작 시간
    private String message; // 메시지

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
}
