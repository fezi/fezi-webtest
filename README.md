## What is it?
This is a project template for automated browser tests using 
- Geb (written in Groovy, uses Selenium)
- Maven
- TestNG (JUnit, Cucumber, Spock would work as well)

Latest versions as of 2017-12-02 are used.

Geb supports DRY principle better than plain Selenium, avoiding maintenance hell.

## Requirements
- **Google Chrome Browser**<br>
Ubuntu did fine with Chromium as well.<br>
You could use -Dgeb.env to setup different browsers (Firefox, headless PhontomJS), see file GebConfig. Additional setup required to do so.<br>
If you want to use a custom Chrome installation, set environment variable CHROME_EXECUTABLE (pointing to the executable)

- **Maven 3 and Java 8** <br>
Versions should be downgrade-able as well by changing pom.xml.
We recommend IntelliJ IDEA (free community edition is sufficient) for its out-of-the-box TestNG and Maven support. Just click the green run button in an opened *Test class. Eclipse requires TestNG plugin to do so.

Nothing else!

If you get "could not connect to renderer" error but Chrome window has opened, you propably need to update the chrome driver (project root folder) with downloads provided at 
https://chromedriver.storage.googleapis.com/index.html 
. See ChromeDriverHelper to learn how OS and filename correlate.

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
<li>Mixing Java 8 code with Groovy code is supported.
<li>Mobile/responsive Website testing support: MyFirstMobileTest uses a UserAgent String and a window size typical smartphones.
<li>geb-reports (html and jpg with current browser state - useful for debugging CI builds) survive 'mvn clean'

## Also see
https://github.com/geb/geb-example-maven more naked geb example<br>
http://gebish.org/manual/2.0/ Geb doc