package group.notify;
import group.notify.daos.Daos;
//імпорт власних пакетів
import group.notify.server.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
// import java.io.IOException;
import java.lang.Exception;

import java.util.concurrent.Executors;
// import java.util.concurrent.ExecutorService;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;

import java.util.List;
import java.time.Duration;
import java.lang.Long;
import java.lang.Integer;

import java.math.BigInteger;

import java.lang.Runtime;
import java.lang.Thread;
import java.net.http.*;

// @Component
public class Entry{
    // private final PoolingFetcher poolingFetcher;

    public Entry(){
        // this.poolingFetcher = poolingFetcher;
    }

    // @Override
    public void run(String... args){
        //Налаштувати м'яке завершення роботи
        Runtime.getRuntime().addShutdownHook(new Thread(new GracefullShutdwn()));

        Arguments arguments;
        try{
            arguments = new Arguments(args);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return;
        }
        
        ExecutorCreator executorCreator = new ExecutorCreator(arguments);

        HttpClientCreator httpClientCreator = new HttpClientCreator(arguments, executorCreator);

        Database database;
        try{
            database = new Database(arguments);
        }
        catch (Exception e){
            System.err.printf("Error while connectiong to database\njdbc connection string given:&s\nError message:", arguments.jdbcConnectionString, e.getMessage());
            return;
        }

        //Me showing that you can do the thing spring does with simple struct you pass around manually
        Container container = new Container(arguments, executorCreator, httpClientCreator, database);

        Daos daos = new Daos(container);

        Static.container = container;

        if(arguments.help){
            new HelpFormatter().printHelp("notify", Arguments.options);
        }
        else{
            // System.out.println("Using pooling");
            System.out.printf("Using %s for thread-count\n", arguments.threadCount);
            System.out.printf("Using %s milliseconds for http-timeout\n", arguments.httpTimeout);
            System.out.printf("Using %s milliseconds for pooling-sleep-duration\n", arguments.poolingSleepDuration);
            System.out.printf("Using %s for update-id-offset\n", arguments.updateIdOffset);
            System.out.println("Command line argument parsing is over; starting pooling...");

            //Запуск пулінга
            new PoolingFetcher(container, daos).run();
            // poolingFetcher.run();
        }
        // else{
        //     System.out.println("Webhooks");
        //     new WebhookFetcher(new HttpRequestHandler()).run();
        // }
    }
}