package fzi.webtest.tests

import fzi.webtest.config.mobile.MobileConfigurationLoader
import fzi.webtest.pages.MyFirstPage
import geb.Configuration
import geb.testng.GebReportingTestTrait
import org.testng.annotations.Test


class MyFirstMobileTest implements GebReportingTestTrait {

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
