package com.bluesgao.kafka.monitor;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class JmxConnection {
    private static ConcurrentHashMap<String, MBeanServerConnection> CONNECTOR_HOLDER = new ConcurrentHashMap<>(16);

    public static MBeanServerConnection getConnection(String ip, int port) {
        String ipAndPort = String.format("%s:%s", ip, port);
        String jmxUrl = "service:jmx:rmi:///jndi/rmi://" + ipAndPort + "/jmxrmi";

        if (!CONNECTOR_HOLDER.containsKey(ipAndPort)) {
            try {
                JMXServiceURL serviceURL = new JMXServiceURL(jmxUrl);
                JMXConnector connector = JMXConnectorFactory.connect(serviceURL, null);
                MBeanServerConnection conn = connector.getMBeanServerConnection();
                if (conn == null) {
                    log.error("get connection return null!");
                } else {
                    CONNECTOR_HOLDER.putIfAbsent(ipAndPort, conn);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return CONNECTOR_HOLDER.get(ipAndPort);
    }
}

