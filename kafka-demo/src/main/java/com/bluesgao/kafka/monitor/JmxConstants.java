package com.bluesgao.kafka.monitor;

public class JmxConstants {

	private static final String KAFKA_COMMON_VALUE = "Value";

	public enum KafkaLog {
		SIZE("kafka.log:type=Log,name=Size,topic=%s,partition=%s"),
		VALUE(KAFKA_COMMON_VALUE);
		private String value;
		private KafkaLog(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}

	public enum KafkaServer8 {
		VERSION("kafka.common:type=AppInfo,name=Version"),
		VALUE(KAFKA_COMMON_VALUE),
		END_LOG_SIZE("kafka.log:type=Log,name=LogEndOffset,topic=%s,partition=%s"),
		START_LOG_SIZE("kafka.log:type=Log,name=LogStartOffset,topic=%s,partition=%s");
		private String value;
		public String getValue() {
			return value;
		}
		private KafkaServer8(String value) {
			this.value = value;
		}
	}

	public final class MessagesInPerSec {
        public static final String COUNT = "Count";
        public static final String EVENT_TYPE = "EventType";
        public static final String FIFTEEN_MINUTE_RATE = "FifteenMinuteRate";
        public static final String FIVE_MINUTE_RATE = "FiveMinuteRate";
        public static final String MEAN_RATE = "MeanRate";
        public static final String ONE_MINUTE_RATE = "OneMinuteRate";
        public static final String RATE_UNIT = "RateUnit";
        public static final String VALUE = "Value";
    }

	public enum BrokerServer {
		BYTES_IN_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec"),
		BYTES_OUT_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=BytesOutPerSec"),
		BYTES_REJECTED_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=BytesRejectedPerSec"),
		FAILED_FETCH_REQUESTS_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=FailedFetchRequestsPerSec"),
		FAILED_PRODUCE_REQUESTS_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=FailedProduceRequestsPerSec"),
		MESSAGES_IN_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec"),
		PRODUCE_MESSAGE_CONVERSIONS_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=ProduceMessageConversionsPerSec"),
		REPLICATION_BYTES_IN_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=ReplicationBytesInPerSec"),
		REPLICATION_BYTES_OUT_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=ReplicationBytesOutPerSec"),
		TOTAL_FETCH_REQUESTS_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=TotalFetchRequestsPerSec"),
		TOTAL_PRODUCE_REQUESTS_PER_SEC("kafka.server:type=BrokerTopicMetrics,name=TotalProduceRequestsPerSec"),
		BYTES_IN_PER_SEC_TOPIC("kafka.server:type=BrokerTopicMetrics,name=BytesInPerSec,topic=%s"),
		BYTES_OUT_PER_SEC_TOPIC("kafka.server:type=BrokerTopicMetrics,name=BytesOutPerSec,topic=%s"),
		JMX_PERFORMANCE_TYPE("java.lang:type=OperatingSystem"),
		TOTAL_PHYSICAL_MEMORY_SIZE("TotalPhysicalMemorySize"),
		FREE_PHYSICAL_MEMORY_SIZE("FreePhysicalMemorySize"),
		PROCESS_CPU_LOAD("ProcessCpuLoad"),
		BROKER_VERSION("kafka.server:type=app-info,id=%s"),
		BROKER_VERSION_VALUE("Version");

		private String value;
		public String getValue() {
			return value;
		}
		private BrokerServer(String value) {
			this.value = value;
		}
	}

}
