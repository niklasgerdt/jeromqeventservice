package mom.eventservice;

import mom.eventservice.jeromq.NetworkContext;
import mom.eventservice.jeromq.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = { "mom.eventservice" })
public class EventServiceBaseConfig {
    private final static Logger logger = LoggerFactory.getLogger(EventServiceBaseConfig.class);
    @Autowired
    private NetworkContext ctx;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void destroy() throws InterruptedException {
        logger.info("closing application");
        ctx.setClosing(true);
        Thread.sleep(Socket.TIMEOUT);
        ctx.term();
    }
}
