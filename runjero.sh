java -Xms3G -Xmx3G -Xloggc:'jvm.log' -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -cp target/jeromqeventservice-0.0.1-SNAPSHOT.jar mom.eventservice.EventServiceRunner jero
