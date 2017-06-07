import java.text.SimpleDateFormat

import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities

import fzi.webtest.util.FirefoxDriverHelper


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
   // -- BROWSERS: Only firefox was tested

   // needs a system property 'webdriver.chrome.driver' pointing to chrome executable
   chrome {
      driver = {
         final chromeDriver = new ChromeDriver()
         chromeDriver.location
         chromeDriver.manage().window().maximize()
         return chromeDriver
      }
   }

   // a Safari / WebKit based, headless browser. much better than htmlunit, but needs a system prop 'phantomjs.binary.path' pointing to a phantomjs executable (as chrome and most other browsers)
   phantomjs {
      driver = {
         final capabilities = new DesiredCapabilities()
         capabilities.setCapability('phantomjs.page.settings.userAgent', 'Mozilla/5.0; rv:17.0) Gecko/20100101 Firefox/17.0 Webdriver')
         final phantomJSDriver = new PhantomJSDriver(capabilities)
         phantomJSDriver.manage().window().setSize(new Dimension(1800, 1100))
         return phantomJSDriver
      }
   }
   ie { driver = { new InternetExplorerDriver() } }
   edge { driver = { new EdgeDriver() } }
}

// firefox works well and is the default driver no environment is given (by e.g -Dgeb.env=chrome)
// use FIREFOX_EXECUTABLE to point to a particular firefox driver compatible executable

driver = { FirefoxDriverHelper.firefoxDriver.get() }


