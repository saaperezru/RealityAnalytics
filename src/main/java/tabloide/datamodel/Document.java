package tabloide.datamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import tabloide.crawlers.Extractor;

public class Document {

    String content;
    Map<String, Extractor> propertiesExtractorsMap = new HashMap<String, Extractor>();

    public void addExtractor(Extractor newExtractor) {
        for (String name : newExtractor.getPropertiesNames()) {
            this.propertiesExtractorsMap.put(name, newExtractor);
        }
    }

    public Collection<String> getPropertiesNames() {
        return this.propertiesExtractorsMap.keySet();
    }

    public Map<String, Object> getProperties() {
        HashSet<Extractor> visitedExtractors = new HashSet<Extractor>();
        HashMap<String, Object> ret = new HashMap<String, Object>();
        for (Extractor e : this.propertiesExtractorsMap.values()) {
            if (visitedExtractors.contains(e)) {
                continue;
            } else {
                visitedExtractors.add(e);
                ret.putAll(e.getAllProperties(this));
            }
        }
        return ret;

    }

    public Object getProperty(String name) throws Exception {
        if (!propertiesExtractorsMap.containsKey(name)) {
            throw new Exception("The property " + name + " does not exist for this document");
        }
        return this.propertiesExtractorsMap.get(name).getProperty(name, this);
    }
}
