//UNUSED

package group.notify;

import org.springframework.stereotype.Component;

import com.sun.net.httpserver.*;

import group.notify.server.HttpRequestHandler;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
public class WebhookFetcher{
    private final int portAdress;
    private final int threadPoolSize;
    private final HttpRequestHandler reqHendler;

    public WebhookFetcher(HttpRequestHandler reqHendler){
        this.portAdress = 12345;
        this.threadPoolSize = 4;
        this.reqHendler = reqHendler;
    }

    public void run(){
        // if (!setWebHook()){
        //     System.out.println("Failed to set up web hook, shutting down...");
        //     return;
        // }
        // HttpServer server;
        // try{
        //     server = HttpServer.create(new InetSocketAddress(portAdress), 1000);
        // }
        // catch (IOException ex){
        //     System.out.printf("IO error when binding to port %d\n", portAdress);
        //     System.out.printf("The exception message: %s\n", ex.getMessage());
        //     return;
        // }
        // // //Set up threadPool
        // ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        // server.createContext("/", this.reqHendler);
        // server.setExecutor(executor);
        // server.start();
        System.err.println("Webhooks are not yet imlemented, shutting down...");
    }
    boolean setWebHook(){
        return true;
    }
}