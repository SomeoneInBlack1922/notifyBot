package group.notify.telegram;
import group.notify.telegram.dataObjects.*;
import group.notify.server.*;
import group.notify.telegram.ResponseHendlers.*;
import group.notify.Container;
import group.notify.daos.*;

import java.lang.Runnable;
public class UpdateDispetcher implements Runnable{
    public Update update;
    private Container container;
    private Daos daos;
    public UpdateDispetcher(Update update, Container container, Daos daos){
        this.container = container;
        this.daos = daos;
        this.update = update;
    }
    public void run(){
        // if(update.message.text != null){
        //     switch (update.message.text){
        //         case "/hello":
        //             new HelloCommand(update).run();
        //             break;
        //         case "/start":
        //             HttpClientCreator.sendMessage(update.message.chat.id, "Greetings to the notifybot!\nYou can ask me to send you a message at some time in future\nPress \"Menu\" button to list avaliable commands\nSend /help to get information on bot's usage");
        //             break;
        //         default:
        //             System.err.println("Got invalid command:\n" + update.message.text);
        //     }
        // }
        if (update.message != null){
            if (update.message.text != null && update.message.text != ""){
                switch (update.message.text){
                    case "/start":
                        daos.userDialogStateDao.nullifyState(update);
                        new Start(update, container).run();
                        break;
                    case "/help":
                        daos.userDialogStateDao.nullifyState(update);
                        new HelpMessage(update, container).run();
                        break;
                    // case "/testEcho":
                    //     new EchoMessage(update).run();
                    //     break;
                    default:
                        new SetNotification(update, container, daos).run();
                    }
            }
            // if (update.message.text == "/start"){
            //     HttpClientCreator.sendMessage(update.message.chat.id, "Greetings to the notifybot!\nYou can ask me to send you a message at some time in future\nPress \"Menu\" button to list avaliable commands\nSend /help to get information on bot's usage");
            // }
            // else if (update.message.text == "/hello"){
            //     new HelloCommand(update).run();
            // }
        }
        

    }
}