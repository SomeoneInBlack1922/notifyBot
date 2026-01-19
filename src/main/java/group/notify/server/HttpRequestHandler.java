package group.notify.server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.stereotype.Component;
public class HttpRequestHandler implements HttpHandler{
    public HttpRequestHandler(){}
    public void handle(HttpExchange ex) throws IOException{
        String response = "This is the response";
        ex.sendResponseHeaders(200, response.length());
        OutputStream responseBody = ex.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
        System.out.println("Response sent");
    }
}