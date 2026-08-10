import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws IOException {
        // Start a web server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Map the root URL path "/" to our web handler
        server.createContext("/", new GreetingHandler());
        
        server.setExecutor(null); // default executor
        server.start();
        System.out.println("--- Server started successfully on port 8080 ---");
        System.out.println("Visit: http://localhost:8080/?name=YourName");
    }

    static class GreetingHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI requestURI = exchange.getRequestURI();
            String query = requestURI.getQuery();
            String name = "Guest";

            // Basic query parsing for ?name=XYZ
            if (query != null && query.startsWith("name=")) {
                name = query.split("=")[1];
            }

            String response = "Hello, " + name + "! Your executable JAR is running on port 8080.";
            
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

