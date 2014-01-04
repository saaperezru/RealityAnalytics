package tabloide.crawlers;

import tabloide.datamodel.Document;
import java.util.List;
import java.util.Map;

public interface Extractor {

    public List<String> getPropertiesNames();

    public Map<String, Object> getAllProperties(Document doc);

    public Object getProperty(String name, Document doc);
}
