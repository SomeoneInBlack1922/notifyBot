package group.notify.telegram.ResponseHendlers;
import group.notify.server.Database;
import group.notify.server.ExecutorCreator;
import group.notify.server.HttpClientCreator;
import group.notify.server.ScheduledMessage;
import group.notify.server.TimeParser;
import group.notify.telegram.dataObjects.*;
import group.notify.Container;
import group.notify.daos.Daos;
import group.notify.daos.UserDialogStateDao;
import group.notify.databaseEntities.*;
import group.notify.databaseEntities.UserDialogState.DialogState;

import java.lang.Runnable;
import java.util.concurrent.TimeUnit;
import java.lang.Exception;

import org.hibernate.Session;

public class SetNotification implements Runnable{
    public Update update;

    private final Container container;
    private final Daos daos;
    public SetNotification(Update update, Container container, Daos daos){
        this.container = container;
        this.daos = daos;
        this.update = update;
    }
    public void run(){
                        // Session dbSession = container.database.getSession();
                        UserDialogState userDialogState = daos.userDialogStateDao.get(update.message.from.id);
                        if (userDialogState == null && update.message.text.equals("/set_notification")){
                            UserDialogState newDialogState = new UserDialogState(update.message.from.id, DialogState.SETTING_NOTIFICATION_MESSAGE);
                            daos.userDialogStateDao.save(newDialogState);
                            // dbSession.save(newDialogState);
                            container.httpClientCreator.sendMessage(update.message.chat.id, "We will set up a notification in two steps: first send me the text of desired notification.");
                        }
                        else if(userDialogState == null){
                            container.httpClientCreator.sendMessage(update.message.chat.id, "?");
                        }
                        else if(update.message.text.equals("/set_notification")){
                            daos.userDialogStateDao.nullifyState(update);
                            container.executorCreator.updateDispetcherThreadPool.execute(new SetNotification(update, container, daos));
                            return;
                        }
                        else if(userDialogState.dialogState == DialogState.SETTING_NOTIFICATION_MESSAGE){
                            String message = update.message.text.trim();
                            if (message == "" && message == null){
                                container.httpClientCreator.sendMessage(update.message.chat.id, "Sorry, can't make an empty notification");
                                return;
                            }
                            daos.userDialogStateDao.updateStateAndMessage(userDialogState, DialogState.SETTING_NOTIFICATION_TIME, message);
                            container.httpClientCreator.sendMessage(update.message.chat.id, "Good, this message will be used in nitification. Now send the time when notification should fire");
                        }
                        else{ // стан == SETTING_NOTIFICATION_TIME
                            String time = update.message.text.trim();
                            
                            // Спроба пропарсити час
                            long parsedTime;
                            try{
                                parsedTime = TimeParser.parseTime(time);
                            }
                            catch (Exception e){
                                container.httpClientCreator.sendMessage(update.message.chat.id, "Could not parse time. Make shure you enter time in correct format. To get fromats send /help");
                                return;
                            }
                            // Валідація пропарщеного часу
                            byte timeValidation = TimeParser.validateTime(parsedTime);
                            if (timeValidation == 1){
                                container.httpClientCreator.sendMessage(update.message.chat.id, "Can't set timer for more than 2 weeks, enter another value");
                                return;
                            }
                            if (timeValidation == -1){
                                container.httpClientCreator.sendMessage(update.message.chat.id, "Can't set timer for less than 30 seconds, enter another value");
                                return;
                            }
                            if (userDialogState.enteredMessage == null){
                                container.httpClientCreator.sendMessage(update.message.chat.id, "Unexpected error, try from start...");
                                daos.userDialogStateDao.remove(userDialogState);
                                new HelpMessage(update, container).run();
                                return;
                            }
                            container.executorCreator.scheduleExecutor.schedule(new ScheduledMessage(update.message.chat.id, parsedTime, userDialogState.enteredMessage, container, daos), parsedTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                            daos.userDialogStateDao.remove(userDialogState);
                            container.httpClientCreator.sendMessage(update.message.chat.id, "Notification has been sucessfully set! Now WAIT.....");
                        }
                        return;
    }
}