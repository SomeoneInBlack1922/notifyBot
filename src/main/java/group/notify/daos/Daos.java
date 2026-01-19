package group.notify.daos;

import group.notify.Container;

public class Daos {
    public final UserDialogStateDao userDialogStateDao;
    public final NotificationDao notificationDao;
    public Daos(Container container){
        this.userDialogStateDao = new UserDialogStateDao(container);
        this.notificationDao = new NotificationDao(container);
    }
}
