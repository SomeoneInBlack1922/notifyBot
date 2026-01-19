package group.notify.telegram.ResponseHendlers;
import group.notify.Container;
// import group.notify.Static;
import group.notify.server.HttpClientCreator;
import group.notify.telegram.dataObjects.*;
public class HelpMessage {
    Update update;
    Container container;
    public HelpMessage(Update update, Container container){
        this.update = update;
        this.container = container;
    }
    public void run(){
        container.httpClientCreator.sendMessage(update.message.chat.id, "Send me a message containing /set_notification command. It will start a dialog where i will ask you two questions: first will be to send me a message with text you want to see in notification, and second question will de to send me a time when your notification should fire. You should send time in one of two formats: first - \"HH:mm:ss\" like \"20:30:00\" or second - \"dd.MM.yy HH:mm:ss\" like 30.12.22. Second format allows you to specify both day and time. Notifications can only be set to future dates, furthermore - only after 30 seconds since now and before 2 weeks since now. In first format you can only spesify a time, so as date i will use current calendar day");
    }
}
