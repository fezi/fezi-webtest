package fzi.webtest.starter

import org.testng.TestNG
import org.testng.xml.XmlClass
import org.testng.xml.XmlSuite
import org.testng.xml.XmlTest

public abstract class AbstractRunner {

    protected static TestNG createTestNG(List<XmlClass> classes) {
        XmlSuite xmlSuite = new XmlSuite()
        List<XmlSuite> suites = new ArrayList<XmlSuite>()
        suites.add(xmlSuite)

        XmlTest xmlTest = new XmlTest(xmlSuite)
        xmlTest.setXmlClasses(classes)

        TestNG testNG = new TestNG()
        testNG.setXmlSuites(suites)
        return testNG
    }
}