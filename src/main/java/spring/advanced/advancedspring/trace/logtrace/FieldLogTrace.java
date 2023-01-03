package spring.advanced.advancedspring.trace.logtrace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.advanced.advancedspring.trace.TraceId;
import spring.advanced.advancedspring.trace.TraceStatus;

@Slf4j
public class FieldLogTrace implements LogTrace {

    private static final String START_PREFIX = "--->";
    private static final String COMPLEX_PREFIX = "<---";
    private static final String EX_PREFIX = "<-X-";

    private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTime, message);
    }

    private void syncTraceId() {
        if(traceIdHolder == null) {
            traceIdHolder = new TraceId(); // 신규 생성
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTime = System.currentTimeMillis();
        long resultTimeMs = stopTime - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if(e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLEX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }

        releaseTraceId();
    }

    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel()) {
            traceIdHolder = null; // destroy
        } else {
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    // level = 0
    // level = 1 |--->
    // level = 2 |    |--->
    // level = 1 ex |<-X-
    // level = 2 ex |    |<-X-
    private static String addSpace(String prefix, int level) {

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" +prefix : "|    ");
        }

        return sb.toString();
    }
}
