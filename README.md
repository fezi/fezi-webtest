## What is it?
This is a Maven project template for automated browser test using Geb, a groovy and Selenium based framework.

## Prerequisites
Installed Firefox <= version 45. <br>Or use -Dgeb.env to setup different browsers, see file GebConfig.
<p>
Use environment variable FIREFOX_EXECUTABLE if you want to point to a non-default firefox installation,
like a separate long-time-support installation that you can get from
<a href="https://ftp.mozilla.org/pub/firefox/releases/45.9.0esr/win64/en-US/Firefox%20Setup%2045.9.0esr.exe">here</a>.
<br>
It is recommended to disable its updates.
This inconvenience is caused by Firefox new Marionette/Gecko stuff recently introduced by Mozialla and not fully/conveniently supported by Selenium until now.

## Usage
Open MyFirstTest and run (as TestNG test). Or just enter 'mvn test' on command line to execute test-suite testng-default.xml.

You could also use PlainGebScriptingExample to start.
<p>
If you didn't code in Groovy before, you might want to have a look at GroovyLearner

## Start to change code
GebConfig.baseUrl to your actual baseUrl. Geb website is pre-defined as example.

## Features
Following features where added (compared to more naked geb projects):
<li>Mixing Java 8 code with groovy is supported.
<li>Mobile/responsive Website testing support: MyFirstMobileTest uses a UserAgent String and a window size like mobile devices.

## Also see
https://github.com/geb/geb-example-maven (more naked geb example)<br>
http://gebish.org/manual/current/ (Geb doc)