package fzi.webtest.starter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public abstract class AbstractRunnerJava {

   protected static TestNG createTestNG( List<XmlClass> classes ) {
      XmlSuite xmlSuite = new XmlSuite();
      List<XmlSuite> suites = new ArrayList<>();
      suites.add(xmlSuite);

      XmlTest xmlTest = new XmlTest(xmlSuite);
      xmlTest.setXmlClasses(classes);

      TestNG testNG = new TestNG();
      testNG.setXmlSuites(suites);
      return testNG;
   }

   static void repeat( int count, Runnable action ) {
      IntStream.range(0, count).forEach(i -> action.run());
   }
}