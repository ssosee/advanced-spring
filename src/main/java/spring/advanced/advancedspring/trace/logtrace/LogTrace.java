package spring.advanced.advancedspring.trace.logtrace;

import spring.advanced.advancedspring.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
