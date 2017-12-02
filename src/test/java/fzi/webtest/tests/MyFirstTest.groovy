package fzi.webtest.tests

import fzi.webtest.pages.MyFirstPage
import fzi.webtest.util.Behavior
import geb.testng.GebReportingTestTrait
import org.testng.annotations.Test

class MyFirstTest implements GebReportingTestTrait {

    @Test
    void firstTest() {
        MyFirstPage page = browser.to MyFirstPage
        println page.headline.text()
        assert page.headline.text().contains('Geb')

        page.sidebar.sidemenuAnchors.getAt(2).click()
        Behavior.sleepSoundly(1000)
    }

}
