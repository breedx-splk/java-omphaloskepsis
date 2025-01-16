package io.opentelemetry.omphaloskepsis;

import com.google.auto.service.AutoService;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizer;
import io.opentelemetry.sdk.autoconfigure.spi.AutoConfigurationCustomizerProvider;

@AutoService(AutoConfigurationCustomizerProvider.class)
public class IntrospectionCustomizer implements AutoConfigurationCustomizerProvider {

    public final static String INSTRUMENTATION_SCOPE = "io.opentelemetry.omphaloskepsis";

    @Override
    public void customize(AutoConfigurationCustomizer autoConfiguration) {
        autoConfiguration.addSpanProcessorCustomizer(
                (spanProcessor, configProperties) -> new SpanTrackingSpanProcessor(spanProcessor)
        );
    }
}
