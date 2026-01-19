package group.notify;
import group.notify.server.*;
import java.lang.Runnable;
import java.io.FileWriter;
import java.io.IOException;
// @Component
public class GracefullShutdwn implements Runnable{
    public void run(){
        System.out.printf("\nStarting graceful shutdown...\n");
        if (PoolingFetcher.updateIdOffset != null){
            System.out.printf("Saving number of last expected update to file last_expected_update.txt...");
            try{
                FileWriter fileWriter = new FileWriter("last_expected_update.txt");
                fileWriter.write(PoolingFetcher.updateIdOffset.toString());
                fileWriter.close();
            }
            catch (IOException e){
                System.out.println("IO Exceptin occured\n" + e.getMessage() + "\nnumber of last expected update was NOT writen to a file");
            }
            System.out.println("Number of last expected update: " + PoolingFetcher.updateIdOffset.toString());
        }

        //Закриття з'єднання з базою данних
        System.out.println("Closing database connection...");
        try{
            Static.container.database.close();
            System.out.println("Database connection is closed");
        }
        catch (Exception e){
            System.err.printf("Could not close database connection grasefully becouse connection to database is not alaviable\n%s\n", e.getMessage());
        }
        
        System.out.println("Graceful shutdown if ower");
    }
}