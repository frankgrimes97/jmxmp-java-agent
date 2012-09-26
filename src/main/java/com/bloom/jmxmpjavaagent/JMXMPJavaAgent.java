package com.bloom.jmxmpjavaagent;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class JMXMPJavaAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        try {
            Map<String, String> jmxEnvironment = new HashMap<String, String>();
            jmxEnvironment.put("jmx.remote.server.address.wildcard", "false");
            JMXServiceURL jmxUrl = new JMXServiceURL(System.getProperty("javax.management.remote.JMXServiceURL", "service:jmx:jmxmp://127.0.0.1:5555"));
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            JMXConnectorServer jmxRemoteServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxUrl, jmxEnvironment, mbs);

            System.out.println("Starting JMXMPJavaAgent on '" + jmxUrl + "'");

            jmxRemoteServer.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}