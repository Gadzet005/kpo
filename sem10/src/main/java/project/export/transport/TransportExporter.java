package project.export.transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import project.interfaces.Transport;

public interface TransportExporter {
    void export(List<Transport> transports, Writer writer) throws IOException;
}
