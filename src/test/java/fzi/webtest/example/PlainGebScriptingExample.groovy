package fzi.webtest.example

import geb.Browser
import org.openqa.selenium.Keys
import org.testng.annotations.Test

/** example how to drive geb Browser by scripting groovy.*/
class PlainGebScriptingExample {

    @Test
    public void testGoogleSearch() {
        Browser.drive {
            go "http://google.com/ncr"
            assert title == "Google"
            $("input", name: "q").value("wikipedia")
            $("input", name: "q") << Keys.ENTER
            waitFor { title.endsWith("Google Search") }

            def firstLink = $("#rso div.rc a").first()
            assert firstLink.text() == "Wikipedia"

            // makes a png and html snapshot of current tab within the configured report dir.
            report('leavingGoogle')
            firstLink.click()
            waitFor { title.startsWith("Wikipedia") }
        }
    }


}
