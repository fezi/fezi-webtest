package fzi.webtest.config

import org.openqa.selenium.StaleElementReferenceException
import org.testng.IRetryAnalyzer
import org.testng.ITestResult

// per test method
class RetryKnownExceptionAnalyzer implements IRetryAnalyzer {

   private RetryKnownExceptionFailures retryKnownExceptionFailures
   private int retries = 0
   private int maxRetries = 3

   public RetryKnownExceptionAnalyzer(RetryKnownExceptionFailures resultCleaner) {
      this.retryKnownExceptionFailures = resultCleaner
   }

   @Override
   public boolean retry( ITestResult result ) {
      if (isRetryRequired(result)) {
         ++retries
         retryKnownExceptionFailures.addRetriedTestResult(result)
         println "Test method " + result.getMethod() + " failed because of well-known exception " + result.throwable.class + ". Retrying test method (attempt " + retries + "/" + maxRetries + ")."

         return true
      }

      return false
   }

   private boolean isRetryRequired( ITestResult result ) {
      return isFuzzyException(result.throwable) && retries + 1 < maxRetries
   }

   private boolean isFuzzyException( Throwable throwable ) {
      return (throwable instanceof StaleElementReferenceException
       ||  throwable instanceof org.openqa.selenium.remote.UnreachableBrowserException)
   }
}
