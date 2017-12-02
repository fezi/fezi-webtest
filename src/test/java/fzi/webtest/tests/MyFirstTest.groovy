package fzi.webtest.tests

import fzi.webtest.pages.MyFirstPage
import geb.testng.GebReportingTestTrait
import org.testng.annotations.Test

class MyFirstTest implements GebReportingTestTrait {

   @Test
   void firstTest() {
      MyFirstPage page = browser.to MyFirstPage
      assert page.myElement.displayed
   }

}
