package group.notify.daos;
import org.hibernate.Transaction;

import group.notify.Container;
import group.notify.databaseEntities.*;
import group.notify.databaseEntities.UserDialogState.DialogState;
import group.notify.server.Database;
import group.notify.telegram.dataObjects.Update;

import org.hibernate.Session;
import java.math.BigInteger;
import java.lang.Exception;

public class UserDialogStateDao {
    private Container container;
    public UserDialogStateDao(Container container){
        this.container = container;
    }
    public void save(UserDialogState userDialogState){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        dbSession.persist(userDialogState);
        transaction.commit();
        dbSession.close();
    }
    public UserDialogState get(BigInteger userId){
        Session dbSession = container.database.getSession();
        UserDialogState userDialogState;
        try{
            userDialogState = dbSession.find(UserDialogState.class, userId);
        }
        catch (Exception e){
            dbSession.close();
            return null;
        }
        dbSession.close();
        return userDialogState;
    }
    public void remove(UserDialogState userDialogState){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        try{
            dbSession.remove(userDialogState);
            transaction.commit();
        }
        catch (Exception e){
            System.err.printf("Failed to remove entity in UserDialogStateDao: %s\n", e.getMessage());
        }
        dbSession.close();
    }
    public void updateState(UserDialogState userDialogState, DialogState dialogState){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        userDialogState = dbSession.merge(userDialogState);
        userDialogState.dialogState = dialogState;
        transaction.commit();
        dbSession.close();
    }
    public void updateStateAndMessage(UserDialogState userDialogState, DialogState dialogState, String message){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        userDialogState = dbSession.merge(userDialogState);
        userDialogState.dialogState = dialogState;
        userDialogState.enteredMessage = message;
        transaction.commit();
        dbSession.close();
    }
    public void nullifyState(Update update){
        Session dbSession = container.database.getSession();
        Transaction transaction = dbSession.getTransaction();
        transaction.begin();
        UserDialogState userDialogState;
        try{
            userDialogState = dbSession.find(UserDialogState.class, update.message.from.id);
        }
        catch (Exception e){
            dbSession.close();
            return;
        }
        try{
            // dbSession.merge(userDialogState);
            dbSession.remove(userDialogState);
            transaction.commit();
        }
        catch (Exception e){
            // System.err.printf("Failed to remove entity in UserDialogStateDao: %s\n", e.getMessage());
        }
        dbSession.close();
    }
}
