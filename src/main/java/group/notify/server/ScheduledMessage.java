package group.notify.server;
import group.notify.Container;
import group.notify.daos.Daos;
import group.notify.daos.NotificationDao;
import group.notify.databaseEntities.Notification;

import java.lang.Runnable;
import java.math.BigInteger;
public class ScheduledMessage implements Runnable{
    public long dbNotificationId;

    private Container container;
    private final Daos daos;
    public ScheduledMessage(BigInteger chatId, long fireTime, String message, Container container, Daos daos){
        this.daos = daos;
        this.container = container;
        Notification notification = new Notification(chatId, fireTime, message);
        daos.notificationDao.save(notification);
        dbNotificationId = notification.id;
    }
    public void run(){
        Notification notification = daos.notificationDao.get(dbNotificationId);
        if (notification == null){
            System.err.printf("SheduledMessage's run() failed to retrive Notification with id %s from database\n", dbNotificationId);
            return;
        }
        container.httpClientCreator.sendMessage(notification.chatId, notification.message);
        daos.notificationDao.remove(notification);
    }
}
