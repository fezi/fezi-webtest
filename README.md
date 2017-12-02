## What is it?
This is a project template for automated browser test using 
- Geb (written in Groovy, uses Selenium)
- Maven
- TestNG (JUnit also possible)

## Prerequisites
- Google Chrome Browser<br>
You can use -Dgeb.env to setup different browsers, see file GebConfig. Not recommended for beginners.<br>
If you want to use a custom Chrome installation, set environment variable CHROME_EXECUTABLE (pointing to the executable)
- Maven 3 and Java 8 (should be downgrade-able as well in pom.xml) 
We recommend IntelliJ IDEA (free community edition is sufficient) for its out-of-the-box TestNG and Maven support. Just click the green run button in an opened *Test class. Eclipse requires TestNG plugin to do so.

Nothing else!

If you get "could not connect to renderer" error but see an openend chrome window, you propably need to update the chrome driver (project root folder) with downloads provided at https://chromedriver.storage.googleapis.com/index.html

## Execute
Either of those:
- Open e.g. MyFirstTest in your IDE and execute (as TestNG test)
- Run 'mvn test' on command line. This will use TestNG suite defintion testng-default.xml

## Where to start to learn
- To learn Geb, you could look at PlainGebScriptingExample
- To learn Groovy, you could look at GroovyLearner

## Where to start to adapt (to your website)
1. Set GebConfig.baseUrl to your actual baseUrl. Geb's homepage is pre-defined.
1. Adapt MyFirstTest and MyFirstPage to your needs.

## Features
Following features where added (compared to more naked Geb projects):
<li>Mixing Java 8 code with groovy is supported.
<li>Mobile/responsive Website testing support: MyFirstMobileTest uses a UserAgent String and a window size typical smartphones.

## Also see
https://github.com/geb/geb-example-maven more naked geb example<br>
http://gebish.org/manual/current/ Geb doc