package group.notify.telegram.ResponseHendlers;

import group.notify.Container;
import group.notify.telegram.dataObjects.Update;

public class Start {
    Update update;
    Container container;
    public Start(Update update, Container container){
        this.update = update;
        this.container = container;
    }
    public void run(){
        container.httpClientCreator.sendMessage(update.message.chat.id, "Greetings to the notifybot!\nYou can ask me to send you a message at some time in future\nPress \"Menu\" button to list avaliable commands\nSend /help to get information on bot's usage");
    }
}
