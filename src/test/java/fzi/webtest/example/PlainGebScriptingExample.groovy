package fzi.webtest.example

import geb.Browser
import org.testng.annotations.Test

/** example how to drive geb Browser by scripting groovy.*/
class PlainGebScriptingExample {

    @Test
    public void testGoogleSearch() {
        Browser.drive {
            go "http://google.com/ncr"
            assert title == "Google"
            $("input", name: "q").value("wikipedia")
            waitFor { title.endsWith("Google Search") }
            def firstLink = $("li.g", 0).find("a.l")
            assert firstLink.text() == "Wikipedia"
            // seems to work even without 'extends GebReportingTest'. makes a png and html snapshot of current tab within the configured report dir.
            report('leavingGoogle')
            firstLink.click()
            waitFor { title.startsWith("Wikipedia") }
        }
    }


}
