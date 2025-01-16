package io.opentelemetry.omphaloskepsis;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.sdk.common.InstrumentationScopeInfo;
import io.opentelemetry.sdk.trace.ReadableSpan;

import static io.opentelemetry.api.common.AttributeKey.stringKey;
import static io.opentelemetry.omphaloskepsis.IntrospectionCustomizer.INSTRUMENTATION_SCOPE;

public class SpanTracker {

    private static final String SPAN_COUNTS = "otel.span.count";
    private final Object lock = new Object();
    private LongCounter counter = null;

    public void track(ReadableSpan span) {
        LongCounter ctr = getCounter();
        InstrumentationScopeInfo spanScope = span.getInstrumentationScopeInfo();
        Attributes attributes = Attributes.of(
                stringKey("instrumentation.name"), spanScope.getName(),
                stringKey("instrumentation.version"), spanScope.getVersion()
        );
        ctr.add(1, attributes);
    }

    private LongCounter getCounter() {
        synchronized (lock) {
            if (counter == null) {
                OpenTelemetry otel = GlobalOpenTelemetry.get();
                counter = otel.getMeter(INSTRUMENTATION_SCOPE)
                        .counterBuilder(SPAN_COUNTS)
                        .setUnit("span")
                        .setDescription("A count of the number of spans created for an instrumentation")
                        .build();
            }
            return counter;
        }
    }
}
