package group.notify.server;

import org.springframework.stereotype.Component;

import group.notify.Arguments;

import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
public class ExecutorCreator{
    public Executor httpClientThreadPool;
    public Executor updateThreadSplitterThreadPool;
    public Executor updateDispetcherThreadPool;
    public ScheduledExecutorService scheduleExecutor;
    public ExecutorCreator(Arguments arguments){
        this.httpClientThreadPool = Executors.newFixedThreadPool(1);
        this.updateDispetcherThreadPool = Executors.newFixedThreadPool(arguments.threadCount);
        this.updateThreadSplitterThreadPool = Executors.newFixedThreadPool(1);
        this.scheduleExecutor = Executors.newScheduledThreadPool(arguments.threadCount);
    }
    // public ExecutorCreator(){
    //     this.threadPoolSize = 4;
    //     this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    // }
    // public Executor getExecutor(){
    //     return threadPool;
    // }
    public static Executor createThreadPool(int threadCount){
        return Executors.newFixedThreadPool(threadCount);
    }
}