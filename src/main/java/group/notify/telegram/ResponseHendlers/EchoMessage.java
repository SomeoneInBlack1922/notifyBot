// package group.notify.telegram.ResponseHendlers;
// import java.lang.Runnable;

// import group.notify.server.HttpClientCreator;
// import group.notify.telegram.dataObjects.Update;
// public class EchoMessage implements Runnable{
//     public Update update;
//     public EchoMessage(Update update){
//         this.update = update;
//     }
//     public void run(){
//         HttpClientCreator.sendMessage(update.message.chat.id, update.message.text);
//     }
// }
