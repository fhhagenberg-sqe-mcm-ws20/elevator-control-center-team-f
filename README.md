[![Build](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-f/workflows/Build/badge.svg)](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-f/workflows/Build/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-f&metric=alert_status)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-mcm-ws20_elevator-control-center-team-f)

# Elevator Control Center - Team F

## How to run without IDE
1. Download the `jar` file from the release [here](https://github.com/fhhagenberg-sqe-mcm-ws20/elevator-control-center-team-f/releases/tag/1.0.0)
2. Make sure you have your elevator simulator is running 
3. Open or create a scenario and run the simulation
3. Click on the `jar` file to open the `mo-elevator` control system
4. You can see the elevator data on the application
5. You can press on an elevator button and observe the elevator on your simulation


## How to run with IDE and Maven

### Prerequisites

- [x] Java 13 SDK (e.g. Oracle or OpenJDK).
- [x] Maven 3. (If you use an IDE like Eclipse or IntelliJ, Maven is **already included** :sunglasses:.)
	- see http://maven.apache.org/install.html
- [x] An IDE or code editor of your choice.

> Confirm your installation with `mvn -v` in a new shell. The result should be similar to:

```
$ mvn -v
Apache Maven 3.6.2 (40f52333136460af0dc0d7232c0dc0bcf0d9e117; 2019-08-27T17:06:16+02:00)
Maven home: C:\Users\winterer\.tools\apache-maven-3.6.2
Java version: 13, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-13
Default locale: en_GB, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

### Instructions

This maven project is already set up for JavaFx based GUI applications. It also contains a small example application - `App.java`.

1. Import this git repository into your favourite IDE.

1. Make sure, you can run the sample application without errors.
	- Either run it in your IDE
	- Via command line, run it with `mvn clean javafx:run`.

You can build the project with maven using following command

```
mvn clean package
```

The resulting archive (`.jar` file) is in the `target` directory.
