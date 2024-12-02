# newkitchen

* [newkitchen](#newkitchen)
  * [Goal](#goal)
  * [How to build and run this application](#how-to-build-and-run-this-application)
  * [Design Decisions](#design-decisions)
    * [Understanding what the kitchensink application does](#understanding-what-the-kitchensink-application-does)
    * [Choosing a web framework](#choosing-a-web-framework)
    * [Migration process](#migration-process)

## Goal

Porting the [JBoss EAP Quickstarts Kitchensink](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/kitchensink) application to use modern tooling.

## How to build and run this application

TODO

## Design Decisions

### Understanding what the kitchensink application does

TODO

### Choosing a web framework

Looking at Java web frameworks we have to main options. The classical [Spring Boot](https://spring.io/projects/spring-boot) and the newer contender [Quarkus](https://quarkus.io/).

The choice of framework can have a strong impact on other decisions down the line, which is why we shoud make this decision early on. Looking at [Reddit](https://www.reddit.com/r/java/comments/132w9rn/would_you_choose_spring_or_quarkus_for_a_new_set/) most recommendations lean into using Quarkus for greenfield projects, citing among other things the inbuilt features regarding performance profiling, the better performance in general, shallower stack traces for debuggability, strong backing from RedHat and the adherence to modern software engineering principles.

The few voices who claim Spring Boot to be the better choice say there are more examples, an easier guide to get started for beginners, and more detailed documentation for edge cases, as well as fewer quirks.

Asking [perplexityai](https://www.perplexity.ai), we get a similar picture regarding the better performance aspects of Quarkus, better developer experience including hot loading, and the smaller ecosystem.

We choose Quarkus for this modernization since we see the overwhelming majority of points in favor of Quarkus.

### Migration process

TODO

