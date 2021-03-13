# junit_testing
A concrete adaptation of basic data structures of JME CLDC 1.1 to more complex data structure interfaces defined in the Collection Framework of Java J2SE 1.4.2, developed using a test-driven approach with JUnit 4.13 library. T

## Project goals
The goal of this project was to provide a concrete implementation of some data structure interfaces used by a given library developed for J2SE 1.4.2 in a JME CLDC 1.1 environment. 
The project has been developed using a __test-driven__ approach. The tests has been implemented using **JUnit 4.13** test library. Test documentation was generated automatically (using [this](https://github.com/giacomocamposampiero/junit_doc_generator) tool) according to the standards defined by the IBM SAFe template. 
In this project two design patterns were implemented:
-adpater pattern
-iterator pattern
More information about those patterns and the test framework used can be found in the [final report](homework2.pdf) of the assignment.

## Run tests
To run the tests, move to the [src](/src) folder and use the following commands
```
export JUNIT_HOME=/usr/share/java
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit4.jar
javac -cp "./*" adapters/*.java exceptions/*.java interfaces/*.java tester/*.java
java tester.TestRunner
```
