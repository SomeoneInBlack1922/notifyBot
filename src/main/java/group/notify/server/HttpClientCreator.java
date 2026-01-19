package group.notify.server;
import group.notify.*;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;
// @Component
public class HttpClientCreator{
    public static HttpClient httpClient;
    public static String apiBase = "https://api.telegram.org";
    public static String getUpdatesMethod = "getUpdates";
    public static String sendMessageMethod = "sendMessage";
    private Arguments arguments;
    // private ExecutorCreator executorCreator;
    public HttpClientCreator(Arguments arguments, ExecutorCreator executorCreator){
        this.arguments = arguments;
        // this.executorCreator = executorCreator;
        HttpClientCreator.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(arguments.httpTimeout)
            .executor(executorCreator.httpClientThreadPool)
            .build();
    }
    // public final HttpClient client;
    // public static HttpClient MakeHttpClient(Executor executor, Duration duration){
    //     return HttpClient.newBuilder()
    //         .version(HttpClient.Version.HTTP_2)
    //         .followRedirects(HttpClient.Redirect.NEVER)
    //         .connectTimeout(duration)
    //         .executor(executor)
    //         .build();
    // }
    public HttpResponse<String> sendMessage(BigInteger chat_id, String text){
        HttpResponse<String> response;
            try{
                response = HttpClientCreator.httpClient.send(
                    HttpRequest.newBuilder()
                        .uri(URI.create(
                            apiBase  +
                            "/bot" +
                            arguments.botToken +
                            "/sendMessage?chat_id=" +
                            chat_id.toString() + 
                            "&text=" +
                            URLEncoder.encode(text, "UTF-8")
                            ))
                        .build(),
                    BodyHandlers.ofString()
                );
            }
            catch(Exception e){
                System.out.printf("Error while retreving updates: %s\n", e.getMessage());
                return null;
            }
        return response;
    }
    // public static HttpClient MakeHttpClient(Executor executor){
    //     return MakeHttpClient(executor, Duration.ofSeconds(30));
    // }
    // public HttpClient getClient(){
    //     return this.client;
    // }
}