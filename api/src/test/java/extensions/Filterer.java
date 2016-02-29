package extensions;

import java.util.stream.Stream;

public interface Filterer {
    <ObjectEntity, FilterOptions> Stream<ObjectEntity> filter(Stream<ObjectEntity> stream, FilterOptions options);
}
