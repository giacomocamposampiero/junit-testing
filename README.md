# junit_testing
A concrete adaptation of basic data structures of JME CLDC 1.1 to more complex data structure interfaces defined in the Collection Framework of Java J2SE 1.4.2, developed using a test-driven approach with JUnit 4.13 library. This project was a homework for a Software Engineering course that I attended during my BSc second year. The project documents, as well as the software documentation, are in Italian.

## Project goals
The goal of this project was to provide a concrete implementation of some data structure interfaces used by a given library developed for J2SE 1.4.2 in a JME CLDC 1.1 environment. 
The project has been developed using a __test-driven__ approach. The tests has been implemented using **JUnit 4.13** test library. Test documentation was generated automatically (using [this](https://github.com/giacomocamposampiero/junit_doc_generator) tool) according to the standards defined by the IBM SAFe template. 
In this project two design patterns were implemented:
-adpater pattern
-iterator pattern
More information about those patterns and the test framework used can be found in the [final report](homework2.pdf) of the assignment.

## Code documentation
The generated JavaDoc of the adapters can be found [here](/javaDoc/), whereas the SAFe doc of each test can be found [here](/testDoc/). 

## Run tests
To run the tests, move to the [src](/src) folder and use the following commands
```
export JUNIT_HOME=/usr/share/java
export CLASSPATH=$CLASSPATH:$JUNIT_HOME/junit4.jar
javac -cp "./*" adapters/*.java exceptions/*.java interfaces/*.java tester/*.java
java tester.TestRunner
```

## Course link
Link to the software engineering course [here](https://en.didattica.unipd.it/off/2018/LT/IN/IN0508/000ZZ/INP8084339/N0).
