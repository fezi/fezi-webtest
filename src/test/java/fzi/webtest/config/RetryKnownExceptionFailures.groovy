package fzi.webtest.config

import org.testng.ISuite
import org.testng.ISuiteListener
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestNGMethod
import org.testng.ITestResult

// per suite
class RetryKnownExceptionFailures implements ISuiteListener, ITestListener {

   private List<ITestResult> retriedTestResults = new ArrayList<>()

   void addRetriedTestResult(ITestResult result) {
      retriedTestResults.add(result)
   }

   @Override
   public void onStart( ISuite suite ) {
      for (ITestNGMethod method : suite.allMethods) {
         method.setRetryAnalyzer(new RetryKnownExceptionAnalyzer(this))
      }
   }

   @Override
   public void onFinish( ISuite suite ) {
   }

   private void cleanUp( ITestContext context ) {
      for (ITestResult result : retriedTestResults) {
         context.getPassedTests().removeResult(result)
      }

      retriedTestResults.clear()
   }

   @Override
   public void onTestStart( ITestResult result ) {}

   @Override
   public void onTestSuccess( ITestResult result ) {}

   @Override
   public void onTestFailure( ITestResult result ) {}

   @Override
   public void onTestSkipped( ITestResult result ) {
      if (retriedTestResults.contains(result)) {
         result.status = ITestResult.SUCCESS
      }
   }

   @Override
   public void onTestFailedButWithinSuccessPercentage( ITestResult result ) {}

   @Override
   public void onStart( ITestContext context ) {
   }

   @Override
   public void onFinish( ITestContext context ) {
      cleanUp(context)
   }
}
