package extensions;

import java.util.stream.Stream;

public interface Orderer {
    <ObjectEntity, OrderOptions> Stream<ObjectEntity> order(Stream<ObjectEntity> stream, OrderOptions options);
}
