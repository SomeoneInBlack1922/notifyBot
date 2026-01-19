package group.notify.server;
import group.notify.Arguments;
import group.notify.databaseEntities.Notification;
import group.notify.databaseEntities.TestEntity;
import group.notify.databaseEntities.UserDialogState;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import java.lang.Exception;
import java.sql.DriverManager;
public class Database{
    private SessionFactory dbSessionFactory;

    public Database(Arguments arguments) throws Exception{
        // System.out.println("Bootstrap started");
        // String jdbcDriverName = DriverManager.getDriver(jdbcConnectionString).getClass().getName();
        // System.out.printf("Driver name:%s\n", jdbcDriverName);
        String jdbcDriverName = DriverManager.getDriver(arguments.jdbcConnectionString).getClass().getName();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySetting("jakarta.persistence.jdbc.url", arguments.jdbcConnectionString)
            .applySetting("jakarta.persistence.jdbc.driver", jdbcDriverName)
            // .applySetting("hibernate.dialect", "SQLServerDialect")
            .applySetting("hibernate.hbm2ddl.auto", "create")
            .applySetting("hibernate.connection.pool_size", arguments.threadCount)
            .build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        // metadataSources.addPackage("group.notify.databaseEntities");
        metadataSources = metadataSources
            .addAnnotatedClass(UserDialogState.class)
            .addAnnotatedClass(Notification.class)
            .addAnnotatedClass(TestEntity.class);
        System.out.printf("Classes, added to hibernate as entities: %s\n", metadataSources.getAnnotatedClassNames());
        Metadata metadata = metadataSources.buildMetadata();
        this.dbSessionFactory = metadata.buildSessionFactory();
    }
    public Session getSession(){
        return dbSessionFactory.openSession();
    }
    public void close(){
        dbSessionFactory.close();
    }
}