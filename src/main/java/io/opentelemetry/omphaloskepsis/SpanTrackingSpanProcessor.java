package io.opentelemetry.omphaloskepsis;

import io.opentelemetry.context.Context;
import io.opentelemetry.sdk.trace.ReadWriteSpan;
import io.opentelemetry.sdk.trace.ReadableSpan;
import io.opentelemetry.sdk.trace.SpanProcessor;

public class SpanTrackingSpanProcessor implements SpanProcessor {

    private final SpanProcessor delegate;
    private final SpanTracker tracker = new SpanTracker();

    public SpanTrackingSpanProcessor(SpanProcessor delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onStart(Context parentContext, ReadWriteSpan span) {
        delegate.onStart(parentContext, span);
    }

    @Override
    public boolean isStartRequired() {
        return delegate.isStartRequired();
    }

    @Override
    public void onEnd(ReadableSpan span) {
        tracker.track(span);
        delegate.onEnd(span);
    }

    @Override
    public boolean isEndRequired() {
        return true;
    }
}
