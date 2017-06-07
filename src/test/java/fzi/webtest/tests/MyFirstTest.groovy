package fzi.webtest.tests

import fzi.webtest.pages.MyFirstPage
import geb.testng.GebReportingTest
import org.testng.annotations.Test


class MyFirstTest extends GebReportingTest {

   @Test
   void firstTest() {
      MyFirstPage page = browser.to MyFirstPage
      assert page.myElement.displayed
   }

}
