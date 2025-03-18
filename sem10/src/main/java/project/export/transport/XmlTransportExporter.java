package project.export.transport;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import project.interfaces.Transport;

public class XmlTransportExporter implements TransportExporter {
    @Override
    public void export(List<Transport> transports, Writer writer)
            throws IOException {
        transports.forEach(transport -> {
            var content = String.format("""
                    <Vehicle>
                        <VIN>%d</VIN>
                        <Type>%s</Type>
                        <Engine>
                            <Type>%s</Type>
                        </Engine>
                    </Vehicle>
                    """, transport.getVin(), transport.getTransportType(),
                    transport.getEngineType());
            try {
                writer.write(content);
            } catch (IOException e) {
                throw new RuntimeException("Error writing to CSV file", e);
            }
        });
    }
}
