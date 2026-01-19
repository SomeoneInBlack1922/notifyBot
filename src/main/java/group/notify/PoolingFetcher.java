package group.notify;
import group.notify.telegram.dataObjects.*;
import group.notify.telegram.*;
import group.notify.daos.Daos;
import group.notify.server.HttpClientCreator;

import java.lang.Runnable;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URI;

import java.lang.Exception;
import java.lang.Thread;
import java.math.BigInteger;

import java.util.Iterator;
// @Component
public class PoolingFetcher implements Runnable{
    private final long poolingSleepDuration;
    // private final String botToken;
    public static BigInteger updateIdOffset;
    private final String getUpdatesMethod = "getUpdates";
    private static String apiBase = "https://api.telegram.org";
    // public final Arguments args;
    // public final Executor executor;

    // private final Arguments arguments;
    // private final ExecutorCreator executorCreator;
    // private final HttpClientCreator httpClientCreator;
    private final Container container;
    private final Daos daos;
    public PoolingFetcher(Container container, Daos daos){
        this.container = container;
        this.daos = daos;
        this.poolingSleepDuration = container.arguments.poolingSleepDuration;
        if(container.arguments.updateIdOffset != null){
            this.updateIdOffset = container.arguments.updateIdOffset;
        }
        else{
            updateIdOffset = new BigInteger("0");
        }
    }
    public void run(){
        while(true){
            //Запит на оновлення
            HttpResponse<String> response;
            try{
                response = HttpClientCreator.httpClient.send(
                    HttpRequest.newBuilder()
                        .uri(URI.create(
                            apiBase  +
                            "/bot" +
                            container.arguments.botToken +
                            "/" +
                            getUpdatesMethod +
                            "?offset=" +
                            updateIdOffset.toString()
                            ))
                        .build(),
                    BodyHandlers.ofString()
                );
            }
            catch(Exception e){
                System.out.printf("Error while retreving updates: %s\n", e.getMessage());
                continue;
            }

            //Отримуємо тіло із отриманої відповіді
            String responseBody = response.body();

            //Парсинг json об'єкта з тіла відповіді
            Response parsedResponse;
            try{
                parsedResponse = ResponseParser.parse(responseBody, Response.typeToken);
                
            }
            catch (Exception e){
                System.out.println("Exception: " + e.getMessage());
                continue;
            }

            if (!parsedResponse.ok){
                    System.err.println("Got not ok-true response");
                    continue;
                }

            //Ітерування через всі отримані update щоб знайти найбільший номер з них
            Iterator<Update> updatesIterator = parsedResponse.result.iterator();
            while(updatesIterator.hasNext()){
                updateIdOffset = updatesIterator.next().update_id.add(BigInteger.ONE);
            }

            // System.out.printf("Got updates!\n%s\n", responseBody);

            //Запуск диспетчера
            container.executorCreator.updateThreadSplitterThreadPool.execute(new UpdateThreadSplitter(parsedResponse.result, container, daos));

            //Спати перед наступним пулом
            try{
                Thread.sleep(poolingSleepDuration);
            }
            catch(Exception e){
                System.out.printf("Pooling thread has been interrupted unexpectedly\n%s\n", e.getMessage());
            }
        }
    }
}