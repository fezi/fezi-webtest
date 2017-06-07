package fzi.webtest.tests

import fzi.webtest.config.mobile.MobileConfigurationLoader
import fzi.webtest.pages.MyFirstPage
import geb.Configuration
import geb.testng.GebReportingTest
import org.testng.annotations.Test


class MyFirstMobileTest extends GebReportingTest {

   @Override
   public Configuration createConf() {
      new MobileConfigurationLoader(gebConfEnv).getConf(gebConfScript)
   }

   @Test
   void firstTest() {
      MyFirstPage page = browser.to MyFirstPage
      assert page.myElement.displayed
   }

}
