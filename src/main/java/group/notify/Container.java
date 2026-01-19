package group.notify;

import java.net.http.HttpClient;

import javax.xml.crypto.Data;

import group.notify.daos.NotificationDao;
import group.notify.daos.UserDialogStateDao;
import group.notify.server.Database;
import group.notify.server.ExecutorCreator;
import group.notify.server.HttpClientCreator;

public class Container {
    public final Arguments arguments;
    public final ExecutorCreator executorCreator;
    public final HttpClientCreator httpClientCreator;
    public final Database database;
    public Container(
        Arguments arguments,
        ExecutorCreator executorCreator,
        HttpClientCreator httpClientCreator,
        Database database
    ){
        this.arguments = arguments;
        this.executorCreator = executorCreator;
        this.httpClientCreator = httpClientCreator;
        this.database = database;
    }
}
