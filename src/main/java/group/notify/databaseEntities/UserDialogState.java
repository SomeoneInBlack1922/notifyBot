package group.notify.databaseEntities;
import jakarta.persistence.*;
import java.math.BigInteger;

import jakarta.annotation.Nullable;
@Entity
public class UserDialogState{
    @Id
    public BigInteger userId;
    @Enumerated(EnumType.ORDINAL)
    public DialogState dialogState;
    @Nullable
    public String enteredMessage;
    @Nullable
    public long fireUnixMillisecondsTime;
    public UserDialogState(){}
    public UserDialogState(BigInteger userId, DialogState dialogState){
        this.userId = userId;
        this.dialogState = dialogState;
    }
    public UserDialogState(BigInteger userId, DialogState dialogState, String enteredMessage){
        this.userId = userId;
        this.dialogState = dialogState;
        this.enteredMessage = enteredMessage;
    }
    public enum DialogState{
        SETTING_NOTIFICATION_MESSAGE,
        SETTING_NOTIFICATION_TIME
    }
}