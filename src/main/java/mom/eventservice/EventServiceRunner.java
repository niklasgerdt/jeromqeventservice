package mom.eventservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class EventServiceRunner {
    final static Logger logger = LoggerFactory.getLogger(EventServiceRunner.class);

    public static void main(String[] args) {
        startLog();
        System.setProperty("spring.profiles.active", args[0]);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(EventServiceBaseConfig.class);
        ((AbstractApplicationContext) ctx).registerShutdownHook();
        EventService es = ctx.getBean(EventService.class);
        es.run();
        ((AbstractApplicationContext) ctx).close();
        endLog();
    }

    private static void endLog() {
        logger.info("stopping event service");
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.stop();
    }

    private static void startLog() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
        logger.info("starting event service");
    }
}
