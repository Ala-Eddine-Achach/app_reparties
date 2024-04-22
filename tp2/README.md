Compile the source files:
javac -cp amqp-client-5.16.0.jar *.java
Run the compiled classes (HO in this example) on unix-like systems:
java -cp .:amqp-client-5.16.0.jar:slf4j-api-1.7.36.jar:slf4j-simple-1.7.36.jar:mysql-connector-j-8.3.0.jar HO.java
On windows, replace the colons with semicolons:
java -cp .;amqp-client-5.16.0.jar;slf4j-api-1.7.36.jar;slf4j-simple-1.7.36.jar;mysql-connector-j-8.3.0.jar HO.java

License
-------
This work is released under the terms of GPL-3.0+
See [COPYING](../COPYING) for more details.
