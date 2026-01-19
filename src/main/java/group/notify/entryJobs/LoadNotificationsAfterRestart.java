package group.notify.entryJobs;
import java.lang.Runnable;

import group.notify.server.Database;

public class LoadNotificationsAfterRestart implements Runnable{
    Database db;
    public LoadNotificationsAfterRestart(Database db){
        this.db = db;
    }
    public void run(){

    }
}
