package mom.eventservice.jeromq;

import java.util.List;
import lombok.Getter;

public class JeroMqAddressContainer {
    @Getter
    private final List<String> addresses;

    public JeroMqAddressContainer(final List<String> addresses) {
        this.addresses = addresses;
    }
}
