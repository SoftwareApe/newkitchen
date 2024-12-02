# newkitchen

* [newkitchen](#newkitchen)
  * [Goal](#goal)
  * [How to build and run this application](#how-to-build-and-run-this-application)
  * [Design Decisions](#design-decisions)
    * [Choosing a web framework](#choosing-a-web-framework)
    * [Migration process](#migration-process)
      * [Understanding what the application does](#understanding-what-the-application-does)
      * [Benchmarking the old application](#benchmarking-the-old-application)
      * [Creating a starting point](#creating-a-starting-point)
      * [Continuous Testing](#continuous-testing)

## Goal

Porting the [JBoss EAP Quickstarts Kitchensink](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/kitchensink) application to use modern tooling.

## How to build and run this application

1. Install OpenJDK 21 and set your `JAVA_HOME` accordingly and use `mvn -v` (on Windows `.\mvnw.cmd -v`) to verify that your Java version is shown as 21,
2. Install [Quarkus](https://quarkus.io/get-started/) by following the get started guide,
3. Run the application with `quarkus dev`,
4. Run the tests by pressing `r`,
5. Open the dev UI by pressing `w`.

## Design Decisions

### Choosing a web framework

Looking at Java web frameworks we have to main options. The classical [Spring Boot](https://spring.io/projects/spring-boot) and the newer contender [Quarkus](https://quarkus.io/).

The choice of framework can have a strong impact on other decisions down the line, which is why we shoud make this decision early on. Looking at [Reddit](https://www.reddit.com/r/java/comments/132w9rn/would_you_choose_spring_or_quarkus_for_a_new_set/) most recommendations lean into using Quarkus for greenfield projects, citing among other things the inbuilt features regarding performance profiling, the better performance in general, shallower stack traces for debuggability, strong backing from RedHat and the adherence to modern software engineering principles.

The few voices who claim Spring Boot to be the better choice say there are more examples, an easier guide to get started for beginners, and more detailed documentation for edge cases, as well as fewer quirks.

Asking [perplexityai](https://www.perplexity.ai), we get a similar picture regarding the better performance aspects of Quarkus, better developer experience including hot loading, and the smaller ecosystem.

We choose Quarkus for this modernization since we see the overwhelming majority of points in favor of Quarkus.

### Migration process

#### Understanding what the application does

To migrate an application it's first important to know what the application does. We first look at the source of our kitchensink application to gather clues. The name kitchensink doesn't tell us much, except that it probably tests a lot of features of the framework they want to demonstrate, as in the saying "Everything but the kitchen sink", which according to the Cambridge dictionary means [_almost all that you can imagine of something_](https://dictionary.cambridge.org/dictionary/english/everything-but-the-kitchen-sink).


TODO

#### Benchmarking the old application

For a successful migration process we would normally establish a baseline for performance (e.g. latency, throughput). Since I don't have a RedHat subscription and no access to JBoss EAP, I will leave this step out.

#### Creating a starting point

Following the guide at `https://quarkus.io/guides/mongodb` we can use the Quarkus CLI to generate a basic application with `REST` and `MongoDB` support based on Java 21.

`quarkus create app org.softwareape:newkitchen --java=21 --extension='rest-jackson,mongodb-client'`

#### Continuous Testing

To avoid problems we should create an extensive test suite that tests all edge cases and run this continuously in CI/CD.

