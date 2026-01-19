//Бере колекцію update і створює потік на кожну у якому запускає диспетчер
package group.notify.telegram;
import group.notify.telegram.dataObjects.*;
import group.notify.Container;
import group.notify.server.*;
import group.notify.daos.Daos;

import java.lang.Runnable;

import java.util.Collection;
import java.util.Iterator;
public class UpdateThreadSplitter implements Runnable{
    public final Iterator<Update> updateIterator;

    public final Container container;
    public final Daos daos;
    public UpdateThreadSplitter(Collection<Update> update, Container container, Daos daos){
        this.container = container;
        this.daos = daos;
        this.updateIterator = update.iterator();
    }
    public void run(){
        while(updateIterator.hasNext()){
            Update update = updateIterator.next();
            if (update == null){
                continue;
            }
            container.executorCreator.updateDispetcherThreadPool.execute(new UpdateDispetcher(update, container, daos));
        }
    }
}