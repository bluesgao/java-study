package com.bluesgao.kafka.monitor;

import lombok.extern.slf4j.Slf4j;

import javax.management.*;
import java.io.IOException;

import static com.bluesgao.kafka.monitor.JmxConstants.BrokerServer.MESSAGES_IN_PER_SEC;

@Slf4j
public class JmxManager {
    public static void main(String[] args) throws Exception {
        MBeanServerConnection connection = JmxConnection.getConnection("47.97.205.190", 10001);
        int mcount = connection.getMBeanCount();
        log.info("mcount:{}", mcount);

        double ret = getMessagesInPerSec(connection);
        log.info("ret:{}", ret);


    }

    public static double getMessagesInPerSec(MBeanServerConnection conn) {
        AttributeList attributes = getAttributes(conn, MESSAGES_IN_PER_SEC.getValue(), new String[]{JmxConstants.MessagesInPerSec.COUNT,
                JmxConstants.MessagesInPerSec.ONE_MINUTE_RATE, JmxConstants.MessagesInPerSec.FIVE_MINUTE_RATE,
                JmxConstants.MessagesInPerSec.FIFTEEN_MINUTE_RATE, JmxConstants.MessagesInPerSec.RATE_UNIT});

        log.info("AttributeList:{}", attributes);
        for (Object attr : attributes) {
            log.info("attr:{}", attr);
        }

        return 0.0;
    }

    private static Object getAttribute(MBeanServerConnection conn, String objName, String objAttr) {
        if (conn == null) {
            log.error("jmx connection is null");
            return null;
        }

        ObjectName objectName = null;
        try {
            objectName = new ObjectName(objName);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return conn.getAttribute(objectName, objAttr);
        } catch (MBeanException e) {
            e.printStackTrace();
            return null;
        } catch (AttributeNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ReflectionException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static AttributeList getAttributes(MBeanServerConnection conn, String objName, String[] objAttr) {
        if (conn == null) {
            log.error("jmx connection is null");
            return null;
        }

        ObjectName objectName = null;
        try {
            objectName = new ObjectName(objName);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
            return null;
        }

        try {
            return conn.getAttributes(objectName, objAttr);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
        } catch (ReflectionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
