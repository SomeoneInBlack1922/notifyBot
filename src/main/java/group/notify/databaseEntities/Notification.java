package group.notify.databaseEntities;
import jakarta.persistence.*;
import java.math.BigInteger;
import jakarta.persistence.GenerationType;
@Entity
public class Notification{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public BigInteger chatId;
    public long fireTime;
    public String message;
    public Notification(){}
    public Notification(BigInteger chatId, long fireTime, String message){
        this.chatId = chatId;
        this.fireTime = fireTime;
        this.message = message;
    }
    // public NotificationCompositeKey compositeKeyField;
    // public class NotificationCompositeKey{
    //     public NotificationCompositeKey(BigInteger userId, Date fireTime, String message){
    //         this.userId = userId;
    //         this.fireTime = fireTime;
    //         this.message = message;
    //     }
    //     public BigInteger userId;
    //     public Date fireTime;
    //     public String message;
    // }
}