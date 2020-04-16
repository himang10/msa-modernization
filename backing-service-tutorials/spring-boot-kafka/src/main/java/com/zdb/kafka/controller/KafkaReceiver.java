package com.zdb.kafka.controller;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@RestController
@Configuration
public class KafkaReceiver {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value(value = "${spring.kafka.topic-name}")
	private String topic;

	@Bean
	public Map<String, Object> consumerConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, topic + "-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100000000);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 100000000);
		return props;
	}
	
	@Bean
	public Map<String, Object> consumerConfigWithoutCommit() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, topic + "manualcommit-group");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100000000);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 100000000);
		return props;
	}
	
	@Bean
	public Map<String, Object> consumerPatitionConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "partition-topic");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100000000);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 100000000);
		return props;
	}
	
	@Bean
	public Map<String, Object> consumerPatitionAllMessageConfig() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "partition-topic-all");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100000000);
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 100000000);
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfig());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@PostMapping("/consumerAutoCommit")
	@ApiOperation(value="consumerAutoCommit", notes="자동적으로 offset 이 commit 되는 consumer API.")
	public void consumerAutoCommit() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfig());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		//Topic 다수로 subscribe
		
		//강제로 중지하는 방법 - 안됨
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				log.info("Starting exit...");
				kafkaConsumer.wakeup();
			}
		});
		
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
			}
		}
	}
	
	@PostMapping("/consumerWithoutCommit")
	@ApiOperation(value="consumerWithoutCommit", notes="offset 이 commit 되지 않는 consumer API.")
	public void consumerWithoutCommit() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfigWithoutCommit());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
			}
		}
	}
	
	@PostMapping("/consumerManualCommitSync")
	@ApiOperation(value="consumerManualCommitSync", notes="offset 을 동기 방식으로 직접 Commit 하는 API.")
	public void consumerManualCommitSync() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfigWithoutCommit());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
				kafkaConsumer.commitSync();
			}
		}
	}
	
	@PostMapping("/consumerManualCommitAsync")
	@ApiOperation(value="consumerManualCommitAsync", notes="offset 을 비동기 방식으로 직접 Commit 하는 API.")
	public void consumerManualCommitAsync() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfigWithoutCommit());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
				kafkaConsumer.commitAsync();
			}
		}
	}
	
	@PostMapping("/consumerManualCommitAsyncCallback")
	@ApiOperation(value="consumerManualCommitAsyncCallback", notes="offset 을 비동기 방식으로 직접 Commit 하고, 결과를 Callback 으로 받는 API.")
	public void consumerManualCommitAsyncCallback() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfigWithoutCommit());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
				
				kafkaConsumer.commitAsync(new OffsetCommitCallback() {
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
						 if( e != null) {
							 log.error("Commit Failed for offset{}", offsets, e);
						 }
                    }
				});
			}
		}
	}
	
	@PostMapping("/consumerManualCommitSyncAndAsyncCallback")
	@ApiOperation(value="consumerManualCommitAsyncCallback", notes="offset 을 비동기 방식으로 직접 Commit 하고 실패할 경우에 동기식 방식으로 commit 하는 API.")
	public void consumerManualCommitSyncAndAsyncCallback() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfigWithoutCommit());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		try {
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
				for (ConsumerRecord<String, String> record : records) {
					if (record.value() != null) {
						log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
								record.partition(), record.offset(), record.key(), record.value());
					}
					kafkaConsumer.commitAsync();
				}
			}
		} catch (Exception e) {
			log.error("Unexpected error", e);
		} finally {
			try {
				kafkaConsumer.commitSync();
			} finally {
				kafkaConsumer.close();
			}
		}
	}
	
	@PostMapping("/consumerPartitionAutoCommitAll")
	@ApiOperation(value="consumerPartitionAutoCommitAll", notes="파티션 방식으로 offset 을 자동 커밋하는 API.")
	public void consumerPartitionAutoCommitAll() {
		String topic = "partition-topic";
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerPatitionAllMessageConfig());
		kafkaConsumer.subscribe(Arrays.asList(topic));
		
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
			}
		}
	}
	
	@PostMapping("/consumerPartitionAutoCommit")
	@ApiOperation(value="consumerPartitionAutoCommit", notes="자동적으로 특정 partiion 의 offset 이 commit 되는 cosumer API.")
	public void consumerPartitionAutoCommit(
			@RequestParam(value = "topicName")String topicName,
	        @RequestParam(value = "partition")int partition) {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerPatitionConfig());
		TopicPartition partition0 = new TopicPartition(topicName, partition);
		kafkaConsumer.assign(Arrays.asList(partition0));
		
		while (true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(10));
			for (ConsumerRecord<String, String> record : records) {
				if (record.value() != null) {
					log.info("Topic : {}, Partition : {}, Offset : {}, Key : {}, Value : {}", record.topic(),
							record.partition(), record.offset(), record.key(), record.value());
				}
			}
		}
	}
	
	@PostMapping("/getTopicList")
	public void getTopicList() {
		KafkaConsumer kafkaConsumer = new KafkaConsumer(consumerConfig());
		Map<String, List<PartitionInfo>> topics = kafkaConsumer.listTopics();
		Set key = topics.keySet();
		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
			String keyName = (String) iterator.next();
			log.info("keyName : " + keyName);
			PartitionInfo pi = topics.get(keyName).get(0);
			log.info("topic name : " + pi.topic());
		}
	}
	
	/*
	 * 특정 오프셋부터 읽기
	 * 
	 * public void seekToBeginning(Collection<TopicPartition> partitions)

	public void seekToEnd(Collection<TopicPartition> partitions)

	public void seek(TopicPartition partition, long offset)
	 */

}
