package fezi.webtest.starter

import fezi.webtest.tests.MyFirstTest
import org.testng.TestNG
import org.testng.xml.XmlClass

/** How to run TestNG tests from code. Might be Java code as well. */
class DefaultRunner extends AbstractRunner {

    public static void main(String[] args) {
        List<Class> classes = [new XmlClass(MyFirstTest)]
        TestNG testNG = createTestNG(classes)
        1.times({ testNG.run() })
        println 'exitCode=' + testNG.exitCode.exitCode
    }

}