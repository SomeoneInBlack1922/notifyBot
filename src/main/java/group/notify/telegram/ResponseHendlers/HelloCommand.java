// package group.notify.telegram.ResponseHendlers;

// import java.lang.Runnable;
// import group.notify.telegram.dataObjects.*;

// import group.notify.server.HttpClientCreator;

// public class HelloCommand implements Runnable{
//     public Update update;
//     public HelloCommand(Update update){
//         this.update = update;
//     }
//     public void run(){
//         HttpClientCreator.sendMessage(update.message.chat.id, "World!");
//     }
// }