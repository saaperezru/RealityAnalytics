package tabloide.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("book")
public class Test {

	@GET
	@Path("all")
	public static String getAll() {
		return "all";
	}

	@GET
	@Path("search/{type}/{query}")
	public static String getAll(@PathParam("type") String type,
			@PathParam("query") String query) {
		return type + " " + query;
	}

}