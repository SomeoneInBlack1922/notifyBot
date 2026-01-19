package group.notify.daos;

import org.hibernate.Session;
import org.hibernate.Transaction;

import group.notify.Container;
import group.notify.databaseEntities.Notification;
import group.notify.server.Database;

public class NotificationDao {
    private Container container;
    public NotificationDao(Container container){
        this.container = container;
    }
    public void save(Notification notification){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(notification);
        transaction.commit();
        dbSession.close();
    }
    public Notification get(long notificationId){
        Session dbSession = container.database.getSession();
        Notification notification;
        try{
            notification = dbSession.find(Notification.class, notificationId);
        }
        catch (Exception e){
            dbSession.close();
            return null;
        }
        dbSession.close();
        return notification;
    }
    public void remove(Notification notification){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        try{
            dbSession.remove(notification);
        }
        catch (Exception e){
            System.err.printf("Failed to remove entity in Notification: %s\n", e.getMessage());
        }
        transaction.commit();
        dbSession.close();
    }
}
