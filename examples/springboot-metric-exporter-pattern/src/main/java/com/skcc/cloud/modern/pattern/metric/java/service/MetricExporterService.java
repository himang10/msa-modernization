package com.skcc.cloud.modern.pattern.metric.java.service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Gauge;
import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.GaugeMetricFamily;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;


@Service
public class MetricExporterService {

	public String sampleCollector() {
		List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();
	    // With no labels.
	    mfs.add(new GaugeMetricFamily("my_gauge", "help", 42));
	    // With labels
		GaugeMetricFamily labeledGauge = new GaugeMetricFamily("my_other_gauge", 
		"help", Arrays.asList("labelname"));
	    labeledGauge.addMetric(Arrays.asList("foo"), 4);
	    labeledGauge.addMetric(Arrays.asList("bar"), 5);
	    mfs.add(labeledGauge);
	    
	    return write(Collections.enumeration(mfs));
	}
	
	protected String write(Enumeration<Collector.MetricFamilySamples> mfs) {
		try {
			Writer writer = new StringWriter();
			TextFormat.write004(writer, mfs);
			return writer.toString();
		} catch(IOException e) {
			throw new RuntimeException("Writing metrics failed", e);
		}
	}
}
