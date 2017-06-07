package fzi.webtest.metatest

import org.openqa.selenium.StaleElementReferenceException
import org.testng.annotations.Listeners
import org.testng.annotations.Test

import fzi.webtest.config.RetryKnownExceptionFailures

@Listeners(RetryKnownExceptionFailures.class)
class RetryKnownExceptionsTest {

   private static boolean dependingMethodWasExecuted

   private boolean exceptionThrown

   @Test
   void testKnownFuzzyException_stale() {
      if (!exceptionThrown) {
         exceptionThrown = true
         throw new StaleElementReferenceException("message")
      }
   }

   @Test(dependsOnMethods='testKnownFuzzyException_stale')
   void testKnownFuzzyException_stale2() {
      dependingMethodWasExecuted = true
   }

   @Test(dependsOnMethods='testKnownFuzzyException_stale2')
   void testKnownFuzzyException_stale3() {
      assert dependingMethodWasExecuted
   }
}
