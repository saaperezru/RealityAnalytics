package tabloide.crawlers;

import tabloide.datamodel.Document;
import java.util.ArrayList;
import java.util.Map;

public interface Extractor {

    public ArrayList<String> getPropertiesNames();

    public Map<String, Object> getAllProperties(Document doc);

    public Object getProperty(String name, Document doc);
}
