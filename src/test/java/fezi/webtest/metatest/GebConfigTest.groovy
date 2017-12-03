package fezi.webtest.metatest

import fezi.webtest.pages.MyFirstPage
import fezi.webtest.pages.artificial.AtAlwaysFalsePage
import fezi.webtest.pages.artificial.AtAlwaysFalsePageChild
import fezi.webtest.pages.artificial.AtAlwaysFalsePageChildWorkaround
import fezi.webtest.pages.artificial.BadContentPage
import geb.error.RequiredPageContentNotPresent
import geb.testng.GebTestTrait
import org.testng.Assert
import org.testng.annotations.Test

class GebConfigTest implements GebTestTrait {

    @Test(priority = 10)
    void implicitAssertionsIsTurnedOff() {
        1 == 2 // would throw if implicit assertions are on, according to geb documentation
    }

    @Test
    void toMethodBehavior() {
        try {
            browser.to AtAlwaysFalsePage
        } catch (Throwable e) {
            // org.codehaus.groovy.runtime.powerassert.PowerAssertionError or geb.waiting.WaitTimeoutException depending on GebConfig's atCheckWaiting
            return
        }
        Assert.fail("'to check' is supposed to throw")
    }

    @Test
    void toMethodBehavior_child() {
        browser.to AtAlwaysFalsePageChild
    }

    @Test
    void contentAccess_unrequiredWorks() {
        browser.to BadContentPage
        neverexistsUnrequired
        // access on unrequired content does not throw
    }

    @Test(expectedExceptions = RequiredPageContentNotPresent.class)
    void contentAccess_requiredIsRequired() {
        browser.to BadContentPage
        neverexistsRequired
    }

    @Test(expectedExceptions = RequiredPageContentNotPresent.class)
    void contentAccess_defaultIsRequired() {
        browser.to BadContentPage
        // does not throw yet. but access will throw:
        neverexistsUnspecified
    }

    @Test(expectedExceptions = Throwable.class)
    void contentAccess_unknownName() {
        browser.to BadContentPage // or any
        completelyUndefinedThing
    }

    @Test
    void groovyAssertStatementBehavior() {
        mustThrow(0)
        mustThrow(null)
        mustThrow('')
        mustThrow(false)
        mustThrow(new ArrayList())
        assert new Object()
        assert 'false'
        assert 'true'
        assert 2
    }

    private void mustThrow(Object o) {
        try {
            assert o
        } catch (AssertionError e) {
            return
        }
        Assert.fail('expected to fail for ' + o + ' of class ' + o.getClass())
    }

    @Test
    void toMethodBehavior_childWorkaround() {
        try {
            browser.to AtAlwaysFalsePageChildWorkaround
        } catch (Throwable e) {
            return
        }
        Assert.fail("'to check' is supposed to throw")
    }

    @Test(priority = 20)
    void alwaysFalsePageDeliversNoFalsePositiveAt() {
        browser.to MyFirstPage
        try {
            browser.waitFor 3.0, { browser.at AtAlwaysFalsePage }
        } catch (Throwable e) {
            return
        }
        Assert.fail("'at check' is supposed to throw")
    }
}
