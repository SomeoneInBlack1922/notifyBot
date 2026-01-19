package group.notify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import static java.util.concurrent.ScheduledExecutorService;
// import static java.util.concurrent.Executors;
// @SpringBootApplication
public class NotifyApplication {
	public static void main(String[] args) throws java.lang.InterruptedException{
		// SpringApplication.run(NotifyApplication.class, args);
		// java.util.concurrent.ScheduledExecutorService scheduler = java.util.concurrent.Executors.newScheduledThreadPool(4);
		// // scheduler.scheduleWithFixedDelay(new ScheduledTask(), 1, 1, java.util.concurrent.TimeUnit.SECONDS);
		// // System.out.println(new java.util.Date(1743014100).getTime() - System.currentTimeMillis());
		// // System.out.println(new java.util.Date(1743014100).getTime());
		// System.out.println(System.currentTimeMillis());
		// scheduler.schedule(new ScheduledTask(), new java.util.Date(1743015060000L).getTime() - System.currentTimeMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
		// java.lang.Thread.currentThread().sleep(5000);
		// scheduler.shutdown();
		new Entry().run(args);
	}
}
