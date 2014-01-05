package tabloide.datamodel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import tabloide.crawlers.Extractor;

public class Document {

	private Map<String, String> contents;
	private Source source;
	private Map<String, Extractor> propertiesExtractorsMap = new HashMap<String, Extractor>();

	public Document(Map<String, String> content, Source source) {
		this.contents = content;
		this.source = source;
	}

	public Document(String content, Source source) {
		this.contents = new HashMap();
		this.contents.put("raw", content);
		this.source = source;
	}

	public String getContent() {
		StringBuilder sb = new StringBuilder();
		for (String s : this.contents.values())
			sb.append(s);
		return sb.toString();
	}

	public Map<String, String> getContents() {
		return contents;
	}

	public void setContent(String content) {
		this.contents.put("raw", content);
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

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
			throw new Exception("The property " + name
					+ " does not exist for this document");
		}
		return this.propertiesExtractorsMap.get(name).getProperty(name, this);
	}

	public void addExtractors(List<Extractor> extractors) {
		for (Extractor ex : extractors) {
			addExtractor(ex);
		}
	}

}
