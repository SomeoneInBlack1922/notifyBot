package group.notify.telegram.dataObjects;

import java.math.BigInteger;
public class Message{
    public BigInteger message_id;
    // public BigInteger message_thread_id;
    public User from;
    public Chat chat;
    public String text;
    public Message reply_to_message;
}