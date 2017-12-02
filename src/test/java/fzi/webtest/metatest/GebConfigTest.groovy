package fzi.webtest.metatest

import fzi.webtest.pages.artificial.AtAlwaysFalsePage
import fzi.webtest.pages.artificial.AtAlwaysFalsePageChild
import fzi.webtest.pages.artificial.AtAlwaysFalsePageChildWorkaround
import fzi.webtest.pages.artificial.BadContentPage
import geb.error.RequiredPageContentNotPresent
import org.testng.Assert
import org.testng.annotations.Test

class GebConfigTest {

    @Test(priority = 10)
    void isImplicitAssertionsGebFeatureTurnedOff() {
        1 == 2 // will throw if implicit assertions are on, according to geb docu ...
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
//
//   @Test(expectedExceptions = Throwable.class) // AssertionError was not reliable
//   void atMethodBehavior() {
//      browser.to MyFirstPage
//      browser.at HomePage // implicit assertions related error
//   }

    @Test
    void toMethodBehaviorChild() {
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
    void toMethodBehaviorChildWorkaround() {
        try {
            browser.to AtAlwaysFalsePageChildWorkaround
        } catch (Throwable e) {
            return
        }
        Assert.fail("'to check' is supposed to throw")
    }

    @Test(priority = 20)
    void alwaysFalsePageNoFalsePositiveIsAt() {
        browser.to InfoLandingPage
        try {
            waitFor QUICK, { browser.at AtAlwaysFalsePage }
        } catch (Throwable e) {
            return
        }
        Assert.fail("'at check' is supposed to throw")
    }
}
