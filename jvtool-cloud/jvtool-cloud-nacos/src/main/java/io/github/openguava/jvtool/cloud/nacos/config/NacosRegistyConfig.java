package io.github.openguava.jvtool.cloud.nacos.config;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;

import io.github.openguava.jvtool.lang.util.LogUtils;

@Component
public class NacosRegistyConfig implements ApplicationRunner {

	@Autowired(required = false)
    private NacosAutoServiceRegistration registration;
	
	@Value("${server.port}")
    private Integer port;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (registration != null && port != null) {
            Integer tomcatPort = this.getWebPort();
            if(tomcatPort == null) {
            	tomcatPort = port;
            } else {
            	LogUtils.info(NacosRegistyConfig.class, "Nacos Registy 检测到外部容器端口: {}", tomcatPort);
            }
            registration.setPort(tomcatPort);
            registration.start();
        }
	}

	/**
	 * 获取 web容器端口号
	 * @return
	 */
	private Integer getWebPort() {
		try {
			MBeanServer beanServer;
			if (!MBeanServerFactory.findMBeanServer(null).isEmpty()) {
				beanServer = MBeanServerFactory.findMBeanServer(null).get(0);
            } else {
            	beanServer = ManagementFactory.getPlatformMBeanServer();
            }
			Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			Iterator<ObjectName> iterator = objectNames.iterator();
			if(iterator.hasNext()) {
				ObjectName name = iterator.next();
				String portStr = name.getKeyProperty("port");
				if(portStr == null || portStr.isEmpty()) {
					portStr = beanServer.getAttribute(name, "port").toString();
				}
				return Integer.parseInt(portStr);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}
