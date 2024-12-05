# newkitchen

* [newkitchen](#newkitchen)
  * [Goal](#goal)
  * [How to build and run this application](#how-to-build-and-run-this-application)
  * [Migration process](#migration-process)
    * [Choosing a web framework](#choosing-a-web-framework)
    * [Understanding what the application does](#understanding-what-the-application-does)
    * [Using a PoC to understand the tooling](#using-a-poc-to-understand-the-tooling)
    * [Benchmarking the old application](#benchmarking-the-old-application)
    * [Creating a starting point](#creating-a-starting-point)
    * [Developer Experience](#developer-experience)
      * [Enabling fast iteration](#enabling-fast-iteration)
      * [Fast feedback from CI/CD and application monitoring](#fast-feedback-from-cicd-and-application-monitoring)
      * [Logging](#logging)
      * [Reducing complexity with server-side rendering](#reducing-complexity-with-server-side-rendering)
    * [Rethinking UX](#rethinking-ux)
      * [Modern User Interface](#modern-user-interface)
      * [Internationalization](#internationalization)
      * [Reactive Design](#reactive-design)
      * [Discoverability](#discoverability)
      * [Poka Yoke](#poka-yoke)
      * [Accessibility](#accessibility)
      * [Light and Dark Mode support](#light-and-dark-mode-support)
      * [Technologies used](#technologies-used)
      * [Performance is a feature](#performance-is-a-feature)
    * [Authentication and authorization](#authentication-and-authorization)
    * [Rethinking the data model](#rethinking-the-data-model)
      * [Generated IDs as information leak](#generated-ids-as-information-leak)
      * [Email verification status](#email-verification-status)
      * [Data normalization](#data-normalization)
      * [International name support](#international-name-support)
      * [Email uniqueness](#email-uniqueness)
      * [Phone number formatting](#phone-number-formatting)
      * [Testing the data model](#testing-the-data-model)

## Goal

Porting the [JBoss EAP Quickstarts Kitchensink](https://github.com/jboss-developer/jboss-eap-quickstarts/tree/8.0.x/kitchensink) application to use modern tooling.

## How to build and run this application

1. Install [OpenJDK 21](https://openjdk.org/),
2. Install [Quarkus](https://quarkus.io/get-started/) by following the get started guide,
3. Set your `JAVA_HOME` accordingly and use `mvn -v` (on Windows `.\mvnw.cmd -v`) to verify that your Java version is shown as 21,
4. [Install Docker](https://docs.docker.com/get-started/get-docker/).
5. Run the application with `quarkus dev`,
6. Run the tests by pressing `r`,
7. Open the dev UI by pressing `w`,
8. Show UI by going to `http://localhost:8080/`.

## Migration process

### Choosing a web framework

Looking at Java web frameworks we have to main options. The classical [Spring Boot](https://spring.io/projects/spring-boot) and the newer contender [Quarkus](https://quarkus.io/).

The choice of framework can have a strong impact on other decisions down the line, which is why we shoud make this decision early on. Looking at [Reddit](https://www.reddit.com/r/java/comments/132w9rn/would_you_choose_spring_or_quarkus_for_a_new_set/) most recommendations lean into using Quarkus for greenfield projects, citing among other things the inbuilt features regarding performance profiling, the better performance in general, shallower stack traces for debuggability, strong backing from RedHat and the adherence to modern software engineering principles.

The few voices who claim Spring Boot to be the better choice say there are more examples, an easier guide to get started for beginners, and more detailed documentation for edge cases, as well as fewer quirks.

Asking [perplexityai](https://www.perplexity.ai), we get a similar picture regarding the better performance aspects of Quarkus, better developer experience including hot loading, and the smaller ecosystem.

We choose Quarkus for this modernization since we see the overwhelming majority of points in favor of Quarkus.

### Understanding what the application does

To migrate an application it's first important to know what the application does. We first look at the source of our kitchensink application to gather clues. The name kitchensink doesn't tell us much, except that it probably tests a lot of features of the framework they want to demonstrate, as in the saying "Everything but the kitchen sink", which according to the Cambridge dictionary means [_almost all that you can imagine of something_](https://dictionary.cambridge.org/dictionary/english/everything-but-the-kitchen-sink).

TODO

### Using a PoC to understand the tooling

If we don't know the tools we're going to use it's good to start by building a PoC that uses all the technologies we wish to use on a surface level. This can help detect whether our choice of web framework matches our expectations, whether our design plan can be feasibly done. In case we run into issues here we can pivot to a different technology as necessary early on, without being overinvested in one specific approach.

We should test out all of the features we need to build the app according to our first understanding of what the app does from the previous step.

### Benchmarking the old application

For a successful migration process we would normally establish a baseline for performance (e.g. latency, throughput). Since I don't have a RedHat subscription and no access to JBoss EAP, I will leave this step out.

### Creating a starting point

Following the guide at `https://quarkus.io/guides/mongodb` we can use the Quarkus CLI to generate a basic application with `REST` and `MongoDB` support based on Java 21.

`quarkus create app org.softwareape:newkitchen --java=21 --extension='rest-jackson,mongodb-client'`

### Developer Experience

#### Enabling fast iteration

Quarkus makes developing the application a breeze. The inbuilt templating functionality is very powerful, as well as the automatic reloading of the webpage, which makes it easy to build and maintain the application. For developer experience what matters most is that we make iteration fast and easy.

#### Fast feedback from CI/CD and application monitoring

For a production application we should create a CI/CD pipeline and a PR workflow based on continuous testing to avoid regressions. We should use container technology like Docker to avoid _works on my machine_ issues.

In a production setting we should also continuously monitor application performance.

#### Logging

It's important to add logging to the application that can be active throughout the migration process, this can ease bug discovery.

#### Reducing complexity with server-side rendering

By using server-side template rendering we can avoid using JavaScript, which would create extra complexity for development. For a database-centric application like this it seems like a good choice to have a single source of truth coming from the server.

### Rethinking UX

For acceptance of a modernized application it's important to convince not only the developers, but also the users of the application.

#### Modern User Interface

The original application looks like it was taken straight from the 90s. While this is functionally ok, there is a relationship between the attractiveness of an interface and its perceived usability, originally studied in 1995 by Kurosu, Kashimura[1] and later replicated in a different cultural contextand popularized by Donald Norman[2] famous for the book [The Design of Everyday Things](https://en.wikipedia.org/wiki/The_Design_of_Everyday_Things). There are multiple hypotheses why this could be, e.g. that perceived attractiveness creates a more relaxed environment where the user is more forgiving of issues, feels more at ease to try things to make it work, and may even forget small usability issues they encountered.

For this demonstrator we chose a restyling in material design. For a proper product it would be good to consult a design team.

[1] Kurosu, M. and Kashimura, K. Apparent usability vs. Inherent usability, CHI '95 Conference
Companion, (1995), 292-293.
[2] Norman, Donald. (2002). Emotion & Design: Attractive Things Work Better. Interactions Magazine. 9. 36-42. 10.1145/543434.543435.

#### Internationalization

For a production application we should offer internationalization for all languages that are used by our customers. While English is universally used on the internet, some users may face issues if their language is not available.

#### Reactive Design

We have to think about which environments the application may run in, are all users on laptops? Do some use tablets or smartphones. If so we need to check that the design is usable in all necessary contexts. Not only do we need to keep the important elements in the users field of view for screens of all needed sizes. We also need to ensure that the interface works well with keyboard navigation, mouse navigation, and touch interface.

This also includes testing the user interface on all major browsers being used by our customers. This task can be made simpler by using server side rendering of static HTML.

#### Discoverability

For this small demonstrator there aren't many functions. For a larger application we need to be sure that all functions are discoverable. Ideally this means that a user can enter the application and do what they need to do without consulting a manual. This can be done by only showing the part of the user interface that is necessary at a time. E.g. when picking calendar dates and some dates are not possible, we should grey out the dates that can't be used.

For more complex functions it might be good to add tool-tips that describe the use of items.

#### Poka Yoke

[Poka yoke](https://en.wikipedia.org/wiki/Poka-yoke) is Japanese term for mistake(poka)-avoidance(yokeru). It's a design philosophy that comes from the Toyota Production System, which is very much related to discoverability. In essence it means that if possible the design itself should prevent mistakes. E.g. going back to the calendar example the date picker should not only grey out dates that are not possible, but also make it impossible to enter those dates, either via keyboard, or the datepicker.

#### Accessibility

Accessibility is important, not merely because it's prescribed by law, but also because there are many people with one or more accessibility issues. E.g. [color blindness affects over 4% of the population](https://en.wikipedia.org/wiki/Color_blindness), this means we should make the color design as color-blind friendly as possible and also include signs as a backup channel for conveying important messages. A red warning may not be perceived as a warning by a red-green color-blind person. Making the warning color orange makes it easier to see. Additionally a small warning sign can be added next to the message, that conveys the meaning without needing color discernment.

For blind people screen-reader support is essential, this means all images being used should have a matching and descriptive alt-text, as well as [ensuring that the tab-order follows accessibility guidelines](https://www.csun.edu/universal-design-center/web-accessibility-criteria-tab-order). Tab-order also helps those who have carpal-tunnel syndrome and rely on keyboard navigation.

![Warning symbol next to error message](./images/warning-symbol.png)

#### Light and Dark Mode support

For the demonstrator this is out of scope, however nowadays a lot of users like to use dark mode for their system default. If you open a website that does not support dark mode it creates a jarring experience. For a smooth user experience it's important to enable dark and light mode support, including graphics that match dark and light mode color schemes.

#### Technologies used

Since this is mainly a tech stack demonstrator it's good to add a link to the main technologies being used. In the original application the JBoss EAP Logo was prominently featured. However it could be misconstrued that the logo being displayed is the application itself. Some technology companies are famous for issueing lawsuits over trademark and copyright issues, especially Oracle. Therefore if we want to mention technologies we should analyze whether the company is generally ok with using their logo, and in which fashion it can be used.

TODO: Insert original image here and comparison with new powered by footer.

For our demonstrator we used the MongoDB logo including the MongoDB text. Since it's mentioned on the [MongoDB brand resources page](https://www.mongodb.com/company/newsroom/brand-resources) that the leaf logo without the text shall only be used if the context makes it clear that this is MongoDB. Design wise it might look better if we have smaller logos, or all logos with text on the right, however the Quarkus logo with text on the right looks too heavy due to capitalization.

Compare

<p float="left">
  <img src="./images/quarkus-logo-wide.png" alt="Quarkus Logo Wide" width="30%" />
  <img src="./src/main/resources/META-INF/resources/images/mongodb-logo.svg" alt="MongoDB Logo Wide" width="30%" />
</p>

Before releasing the application it would be necessary to contact each company to make sure that the logo use is appropriate according to their terms. For the purpose of this demonstrator I will assume this has been done.

#### Performance is a feature

Amazon famously found out with A/B testing that [adding 100ms delay to their page loading times would decrease sales by ~1%](https://www.forbes.com/sites/steveolenski/2016/11/10/why-brands-are-fighting-over-milliseconds/). Other research cited in the same article suggests that a quarter of users will abandon a webpage that loads for more than 4 seconds.

If the application is used internally in a company the users may be forced to use it, but the frustration they experience due to forced waiting and slow reaction time may impact their efficiency and mental load at work.

For this reason it's important to test load times, plan for and fix performance regressions, and use performant techonology in the tech stack. Performance needs to be a built-in UX feature from the start.

Say this application hosts thousands of entries. Most users would not need to look at all of them at once. A paging interface might be a good idea for a production application.

### Authentication and authorization

For this demonstrator application this is out of scope. However in a production setting it's probably not legal to display real user information to everybody. Ideally we'd integrate with the company's SSO.

Further we'd probably need to create user groups for registration permissions as well as read-only permissions. The read-only permissions may also be subdivided on a need-to-know basis, if for example we only want to show team information to a group leader, they should not be able to see other users' information.

### Rethinking the data model

For this demonstrator application we have a somewhat simple data model. Each user is assigned a generated ID. And we save the user information in the database.

#### Generated IDs as information leak

The generated IDs are currently shown in the table view. Say a user only has permissions to see some user information they could use the math of the [German tank problem](https://en.wikipedia.org/wiki/German_tank_problem) to find out how many users use this database.

For this reason it might be better to assign UUIDs for generated IDs. That way the generated IDs don't enable an estimate of how many users there are.

#### Email verification status

For a production application it might be good to verify emails and make this part of the data model. This could even be handled on a type-level with sealed interfaces in Java, where we have VerifiedEmail and UnverifiedEmail as subtypes of the sealed interface Email, this could avoid mistaking an unverfied email for a verified one on type system level.

Email verification could also be combined with a timeout for verification to disallow somebody that doesn't control an email address to squat other user's email addresses indefinitely.

#### Data normalization

Users may enter names with unnecessary whitespace before or after the name, this is especially common when entering data via smartphone (See #). Even the same email and phone number can be entered in multiple ways.

#### International name support

It may be that our users include people with names outside the English alphabet. E.g. German names like `Müller`, or Chinese names like `小云`. We may prefer only allowing romanized names, but this should be clarified together with the customer.

#### Email uniqueness

The application uses IDs and emails as unique fields. While it's common practice to use email instead of a username, which also makes it easier for the user not to have an additional username to remember there are some rules for [how email addresses can be written differently which also depends on the mail service provider.](https://en.wikipedia.org/wiki/Email_address). E.g. `myemail@Provider.com` is equivalent to `myemail@provider.com`, and `My.Email@gmail.com` is equivalent to `myemail@gmail.com`. The equivalence in the local part is not part of the standard.

Assuming this was a company internal application we might know more about the mail server rules and could normalize the emails appropriately before entering them into the database.

#### Phone number formatting

If we expect international users it might be good to have a field for entry of country code in front of the phone number. We might also remove formatting like hyphens and parentheses to make the data easier to handle for other use cases. Leading zeros could also be normalized, especially regarding country codes. Since a call to `+49 123456789` might fail if the data is saved as `+49 0123456789`.

#### Testing the data model

The kitchensink application has a very restrictive data validation model. Phone numbers may only be 10-12 digits long. There are a few phone numbers in my address book with fewer digits. There are maybe international phone numbers which need the country code added to the start.

Whichever rules we adopt we should have an extensive test suite for adding these data to the application.

It may be good to check phone numbers, emails, addresses, names and such against the lists of [falsehoods programmers believe about x](https://github.com/kdeldycke/awesome-falsehood).

Testing for weird edge cases can also be aided by employing property-based testing for generating RFC conformant Emails that may trigger weird behavior, e.g. using [jqwik](https://jqwik.net/).