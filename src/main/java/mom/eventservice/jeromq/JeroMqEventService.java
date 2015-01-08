package mom.eventservice.jeromq;

import java.util.List;
import javax.annotation.PostConstruct;
import mom.eventservice.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMQ;

@Component
@Profile({ "jero", "jerotest" })
public class JeroMqEventService implements EventService {
    private final static Logger logger = LoggerFactory.getLogger(JeroMqEventService.class);
    private NetworkContext context;
    private final List<String> inAddresses;
    private String outAddress;
    private Socket sub;
    private Socket pub;

    @Autowired
    public JeroMqEventService(final NetworkContext context, final JeroMqAddressContainer outAddress,
            final JeroMqAddressContainer inAddresses) {
        this.context = context;
        this.outAddress = outAddress.getAddresses().get(0);
        this.inAddresses = inAddresses.getAddresses();
    }

    @Override
    public void run() {
        logger.info("running event service");
        while (!context.isClosing()) {
            String msg = sub.recvStr();
            if (msg != null) {
                logger.debug("routing event {}", msg);
                pub.send(msg);
            }
        }
    }

    @PostConstruct
    public void up() {
        logger.info("setting up event service (in({}), out({}))", inAddresses, outAddress);
        sub = context.socket(ZMQ.SUB);
        inAddresses.forEach(s -> sub.connect(s));
        sub.subscribe("".getBytes());
        logger.info("subscribers up");
        pub = context.socket(ZMQ.PUB);
        pub.bind(outAddress);
        logger.info("binded to {}", outAddress);
    }
}
