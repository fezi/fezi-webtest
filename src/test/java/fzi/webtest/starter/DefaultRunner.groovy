package fzi.webtest.starter

import fzi.webtest.tests.MyFirstTest
import org.testng.TestNG
import org.testng.xml.XmlClass

/** How to run tests without TestNG xml files */
class DefaultRunner extends AbstractRunner {

   public static void main(String[] args) {
      //System.setProperty("against", "stage")

      List<Class> classes = [
            new XmlClass(MyFirstTest),
      ]
      classes.addAll([
            new XmlClass(MyFirstTest),
      ])
      TestNG testNG = createTestNG(classes)
      1.times({ testNG.run() })
   }

}

