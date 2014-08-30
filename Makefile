all: jar

jar: java
	jar cvfe assignment1.jar net.microtriangle.soedar.Main -C bin .  

java:
	mkdir -p bin
	javac -d bin src/net/microtriangle/soedar/eventmanager/*.java
	javac -d bin -cp ./bin src/net/microtriangle/soedar/filters/*.java
	javac -d bin -cp ./bin src/net/microtriangle/soedar/*.java

clean:
	rm -rf bin
	rm assignment1.jar
