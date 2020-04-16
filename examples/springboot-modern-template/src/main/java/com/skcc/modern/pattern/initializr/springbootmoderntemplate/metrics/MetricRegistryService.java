package com.skcc.modern.pattern.initializr.springbootmoderntemplate.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MetricRegistryService {
    @Autowired
    private MeterRegistry meterRegistry;
    private Counter callMethod;

    private static final String CALL_METHOD_COUNT = "call_method_total";

    /*
    * TODO : Annotation으로 변경 제공
    */
    public void count(String className, String methodName) {
        this.callMethod = Counter.builder(CALL_METHOD_COUNT)
        .tags("class",className, "method",methodName)
        .description("Total method call count")
        .register(meterRegistry);

        this.callMethod.increment();
    }
}