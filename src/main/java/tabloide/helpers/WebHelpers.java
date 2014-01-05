package tabloide.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.mozilla.universalchardet.UniversalDetector;

public class WebHelpers {
	private static UniversalDetector detector = new UniversalDetector(null);

	public static String getUrl(String finalUrl) throws Exception {
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";

		try {

			url = new URL(finalUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			byte[] bytes = IOUtils.toByteArray(conn.getInputStream());
			detector.handleData(bytes, 0, bytes.length);
			detector.dataEnd();
			String encoding = detector.getDetectedCharset();
			if (encoding != null) {
				result = new String(bytes, encoding);
			} else {
				throw new Exception("No encoding detected.");
			}
		} catch (MalformedURLException ex) {
			throw new Exception("Couldn't open the URL" + finalUrl);
		} catch (ProtocolException ex) {
			throw new Exception("Protocol exception" + finalUrl);
		} catch (IOException ex) {
			throw new Exception("IOException" + finalUrl);
		}
		return result;
	}
}
