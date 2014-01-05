package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class HelloWorld {
	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.NO_SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);

		ServletHolder jerseyServlet = context.addServlet(
				ServletContainer.class, "/webapi/*");
		jerseyServlet.setInitOrder(1);
		jerseyServlet.setInitParameter(
				"com.sun.jersey.config.property.packages", "tabloide.web");

		ServletHolder staticServlet = context.addServlet(DefaultServlet.class,
				"/*");
		staticServlet.setInitParameter("resourceBase", "src/main/java");
		staticServlet.setInitParameter("pathInfoOnly", "true");

		try {
			server.start();
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
}
