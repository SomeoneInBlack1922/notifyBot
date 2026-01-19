target/notify-0.0.1-SANPSHOT.jar: src/main/java/group/notify/**
	mvn clean package -DskipTests

clean:
	mvn clean

run:
	java -jar ./target/notify-0.0.1-SNAPSHOT.jar