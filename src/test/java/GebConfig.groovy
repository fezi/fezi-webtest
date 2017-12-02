import fzi.webtest.util.ChromeDriverHelper
import fzi.webtest.util.Constants
import fzi.webtest.util.FirefoxDriverHelper
import org.openqa.selenium.Dimension
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

import java.text.SimpleDateFormat


// @see http://www.gebish.org/manual/0.9.2/configuration.html
// @see geb.Configuration for runtime overrides via browser.config.bla

baseUrl = 'http://'+InetAddress.getLocalHost().getHostAddress()
baseUrl = 'http://www.gebish.org'

autoClearCookies = false

reportsDir = 'geb-reports/' + new SimpleDateFormat('yyyy-MM-dd_HH.mm.ss').format(DateHolder.date)
reportOnTestFailureOnly = true

//cacheDriverPerThread = false // true is pre-requisite for parallel execution


waiting {
   timeout = 10 // seconds for waitFor
   retryInterval = 0.1 // seconds after which presence is re-checked
   presets {
      // can be referenced from content template options map
      quick {
         timeout = 3
         retryInterval = 0.1
      }
      slow {
         timeout = 30
         retryInterval = 0.3
      }
   }
}
atCheckWaiting = false

// choose by -Dgeb.env=... You can enumerate several separated by space
environments {
   // a Safari / WebKit based, headless browser. much better than htmlunit, but needs a system property 'phantomjs.binary.path' pointing to a phantomjs executable, or have it on PATH
   phantomjs {
      driver = {
         final capabilities = new DesiredCapabilities()
         capabilities.setCapability('phantomjs.page.settings.userAgent', Constants.USER_AGENT_DESKTOP)
         // work in progress. click event not available yet
         capabilities.setCapability('webSecurityEnabled', false)
         capabilities.setCapability('acceptSslCerts', true)
         capabilities.setCapability('localToRemoteUrlAccessEnabled', true)
         final phantomJSDriver = new PhantomJSDriver(capabilities)
         phantomJSDriver.manage().window().setSize(new Dimension(1800, 1100))
         return phantomJSDriver
      }
   }
   ie { driver = { new InternetExplorerDriver() } }
   edge { driver = { new EdgeDriver() } }
   chrome { driver = { ChromeDriverHelper.chromeDriver.get() } }
   firefox { driver = { FirefoxDriverHelper.firefoxDriver.get() } }
}
// default driver if no environment is given (by e.g -Dgeb.env=firefox)
driver = { ChromeDriverHelper.chromeDriver.get() }


