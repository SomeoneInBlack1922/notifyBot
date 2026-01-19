package group.notify;

import org.springframework.stereotype.Component;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.lang.Exception;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
public class Arguments{
    public static final CommandLineParser commandLineParser = new DefaultParser();
    public static final Options options = buildOprions();

    //Аргументи
    public boolean pooling = false;
    public long poolingSleepDuration;
    public Duration httpTimeout = Duration.ofSeconds(30);
    public int threadCount = 1;
    public BigInteger updateIdOffset;
    public String botToken;
    public boolean help;
    public String jdbcConnectionString;
    public Arguments(String... args) throws Exception{
        //Парсинг агрументів командного рядка
        CommandLine cmd;
        try{
            cmd = commandLineParser.parse(options, args);
        }
        catch (Exception e){
            System.err.printf("Failed to parse arguments: %s\n", e.getMessage());
            return;
        }

        //Завершити роботу якщо є неопізнані аргументи
        List<String> unparsedArguments = cmd.getArgList();
        if (unparsedArguments != null && unparsedArguments.size() != 0){
            System.err.println("The following arguments were not recognized:");
            for(int i=0; i<unparsedArguments.size(); i++){
                System.err.println(unparsedArguments.get(i));
            }
            System.err.println("Shutting down...");
            return;
        }

        //Видобування аргументів
        botToken = cmd.getOptionValue("bot-token").trim();

        help = cmd.hasOption("h");

        pooling = cmd.hasOption("p");

        //Зупинитися якщо не вдалося пропарсити токен
        if (botToken == null){
            throw new Exception("Failed to parse bot-token");
        }

        {
            String readThreadCount = cmd.getOptionValue("thread-count");
            if(readThreadCount != null){
                try{
                    threadCount = Integer.parseInt(readThreadCount);
                }
                catch(Exception e){
                    throw new Exception(String.format("Error while parsing value %s for argument thread-count\n%s\nshutting down...", readThreadCount, e.getMessage()));
                }
            }
        }
        {
            String readJdbcConnectionString = cmd.getOptionValue("jdbc-connection-string");
            if (readJdbcConnectionString == null){
                throw new Exception("Unexpected internal error while parsing jdbc-connection-string\n%s\nshutting down...");
            }
            else{
                jdbcConnectionString = readJdbcConnectionString.trim();
            }
            // System.err.printf("Error while parsing value %s for argument jdbc-connection-string\n%s\nshutting down...", readJdbcConnectionString, e.getMessage());
            // return;
        }
        {
            String readHttpTimeout = cmd.getOptionValue("http-timeout");
            if(readHttpTimeout != null){
                try{
                    httpTimeout = Duration.ofMillis(Long.parseLong(readHttpTimeout));
                }
                catch(Exception e){
                    throw new Exception(String.format("Error while parsing value %s for argument http-timeout\n%s\nshutting down...", readHttpTimeout, e.getMessage()));
                }
            }
        }
        {
            String readPoolingSleepDuration = cmd.getOptionValue("pooling-sleep-duration");
            if (readPoolingSleepDuration != null){
                try{
                    poolingSleepDuration = Long.parseLong(readPoolingSleepDuration);
                }
                catch(Exception e){
                    throw new Exception(String.format("pooling-sleep-duration not provided, using default: %sms\n", poolingSleepDuration));
                }
            }
        }
        {
            String readUpdateIdOffset = cmd.getOptionValue("update-id-offset");
            if (readUpdateIdOffset != null){
                try{
                    updateIdOffset = new BigInteger(readUpdateIdOffset);
                }
                catch(Exception e){
                    throw new Exception(String.format("Error while parsing value %s for argument update-id-offset\n%s\nshutting down...", readUpdateIdOffset, e.getMessage()));
                }
            }
        }

    }
    private static Options buildOprions(){
        Options options = new Options();
        // options.addOption("p", "pooling", false, "use pooling to fetch updates instead of webhook, implies apsence of listening on internet port");
        options.addOption("s", "pooling-sleep-duration", true, "milliseconds in integer from 0 to 9,223,372,036,854,775,807; only for pooling; how long to wait afteer a pool is complete before doing another");
        options.addOption("o", "http-timeout", true, "milliseconds in integer from 0 to 9,223,372,036,854,775,807; default is 30,000; how long to wait for http response when pooling request is sent; setting it to numbers close to zero will likely result in most of requests to fail");
        options.addOption("j", "thread-count", true, "integer from 1 to 2,147,483,647; default is 1; number of threads to use when working on responses");
        options.addOption("f", "update-id-offset", true, "integer; only for pooling; default is 0; id of the first update that should be fetched");
        options.addRequiredOption("t", "bot-token", true, "api bot token");
        options.addOption("h", "help", false, "display this message");
        options.addRequiredOption("d", "jdbc-connection-string", true, "JDBS connction string pointing to database which is used to store messages for scheduled notifycations, user schedule and dialog state");
        return options;
    }
}