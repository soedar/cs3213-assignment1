all: jar

jar: java
	jar cvfe assignment1.jar net.microtriangle.soedar.Main -C class .  

java:
	mkdir -p class
	javac -d class src/net/microtriangle/soedar/eventmanager/*.java
	javac -d class -cp ./class src/net/microtriangle/soedar/filters/*.java
	javac -d class -cp ./class src/net/microtriangle/soedar/*.java

clean:
	rm -rf class
	rm assignment1.jar
