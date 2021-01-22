启动kafka服务
cd KAFKA_HOME/
bin/kafka-server-start.sh -daemon ../config/server.properties

创建单分区 topic 副本
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

查看topic分区
bin/kafka-topics.sh --list --zookeeper localhost:2181

生产消息命令
kafka-console-producer.sh --broker-list localhost:9092 --topic test

删除主题
sh kafka-topics.sh --delete -zookeeper localhost:2181 --topic db-user

消费者消费消息命令
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning


启动kafka connect
./bin/connect-distributed.sh -daemon config/connect-distributed.properties



查看connector
curl http://localhost:8083/connectors/
新建connector
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{ "name": "mysql_user", "config": { "connector.class": "io.debezium.connector.mysql.MySqlConnector", "tasks.max": "1", "database.hostname": "localhost", "database.port": "3306", "database.user": "debezium", "database.password": "Gx-123456", "database.server.id": "184054", "database.server.name": "db-user", "database.include.list": "user", "database.history.kafka.bootstrap.servers": "localhost:9092", "database.history.kafka.topic": "mysql.user","include.schema.changes": "false" } }'
删除connector
curl -i -X DELETE http://localhost:8083/connectors/mysql_user
重启connector
curl -X POST http://localhost:8083/connectors/mysql_user/restart
查看connector状态
curl http://localhost:8083/connectors/mysql_user/status







