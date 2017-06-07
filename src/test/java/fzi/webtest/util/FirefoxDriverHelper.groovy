package fzi.webtest.util

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver.Window
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

import java.util.concurrent.TimeUnit

public class FirefoxDriverHelper {

   static final ThreadLocal<FirefoxDriver> firefoxDriver = new ThreadLocal<FirefoxDriver>() {
      @Override
      protected FirefoxDriver initialValue() {
         return FirefoxDriverHelper.newFirefoxDriver(false)
      }
   }

   static final ThreadLocal<FirefoxDriver> firefoxMobileDriver = new ThreadLocal<FirefoxDriver>() {
      @Override
      protected FirefoxDriver initialValue() {
         FirefoxDriver driver = FirefoxDriverHelper.newFirefoxDriver(true)

         // We need to manage the life of the mobile driver on our own
         addShutdownHook {
            try {
               driver.quit()
            } catch (Throwable e) {
            }
         }
         return driver
      }
   }

   static FirefoxDriver newFirefoxDriver(boolean isMobile) {
      DesiredCapabilities capabilities = DesiredCapabilities.firefox()
      capabilities.setCapability("marionette", true) // see http://www.theautomatedtester.co.uk/blog/2016/selenium-webdriver-and-firefox-47.html
      capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true)
      // see https://github.com/SeleniumHQ/selenium/commit/ab994066135f81b43d6ebed93bb398e7c7d064c1

      FirefoxBinary firefoxBinary = null
      String firefoxExe = readPropertyInOrder("FIREFOX_EXECUTABLE", null)
      if (firefoxExe != null && !firefoxExe.isEmpty()) {
         firefoxBinary = new FirefoxBinary(new File(firefoxExe))
      }
      // else: works until Firefox 45 (or 46?) that you need to have installed

      FirefoxDriver firefoxDriver = new FirefoxDriver(firefoxBinary, newFirefoxProfile(isMobile), capabilities)

      Window window = firefoxDriver.manage().window()
      if (isMobile) {
         window.setSize(new Dimension(640, 1200))
      } else {
         window.maximize()
         // optimize screenshots on jenkins
         if (window.getSize().width < 1100) {
            window.setSize(new Dimension(1920, window.getSize().height))
         }
         if (window.getSize().height < 900) {
            window.setSize(new Dimension(window.getSize().width, 1200))
         }
      }
      firefoxDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS)
      firefoxDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS)

      return firefoxDriver
   }


   static FirefoxProfile newFirefoxProfile(boolean isMobile) {
      // a new firefox profile folder will be created in operatings systems temp folder. e.g. 'C:\Users\myUserName\AppData\Local\Temp\anonymous4018215231328960954webdriver-profile' on driver creation
      FirefoxProfile fp = new FirefoxProfile()
      fp.setPreference("browser.download.folderList", 2)
      fp.setPreference("browser.download.manager.showWhenStarting", false)
      fp.setPreference("browser.download.useDownloadDir", true)
      fp.setPreference("browser.helperApps.alwaysAsk.force", false)
      fp.setPreference("xpinstall.signatures.required", false)
      String blank = "about:blank"
      fp.setPreference("browser.startup.homepage", blank)
      fp.setPreference("startup.homepage_welcome_url", blank)
      fp.setPreference("startup.homepage_welcome_url.additional", blank)
      fp.setPreference("startup.homepage_override_url", blank)
      /*fp.setPreference("browser.startup.homepage_override.mstone", "42.0")
       fp.setPreference("browser.startup.homepage_override.buildID", "20151029151421")
       fp.setPreference("browser.startup.page", 0)
       fp.setPreference("services.sync.prefs.sync.browser.startup.homepage", false) */
      String directly = "text/csv,text/x-comma-separated-values,text/comma-separated-values,Application/csv,application/xml,text/xml,application/vnd.ms-excel"
      fp.setPreference("browser.helperApps.neverAsk.saveToDisk", directly)
      if (isMobile) {
         fp.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
      }

      fp.setAcceptUntrustedCertificates(true)
      fp.setAssumeUntrustedCertificateIssuer(false)
      if (!'jenkins'.equals(System.getenv('EXECUTOR'))) {
         installFirebugPlugin(fp)
      }
      installModifyHeadersPlugin(fp)
      return fp
   }

   // so you can set breakpoints in your IDE and then use firebug to inspect the DOM
   static void installFirebugPlugin(FirefoxProfile firefoxProfile) {
      URL fileUrl = ClassLoader.getResource("/xpi/firebug-2.0.13.xpi")
      if (fileUrl) {
         File file = new File(fileUrl.toURI())
         firefoxProfile.addExtension(file)
         // see https://getfirebug.com/wiki/index.php/Firebug_Preferences "panels" menu links left-handside
         // next line will surpress the whats new tab of firebug
         firefoxProfile.setPreference("extensions.firebug.currentVersion", "2.0.13")
         // enable some tabs per default, like net, console or cookie
         firefoxProfile.setPreference("extensions.firebug.net.enableSites", true)
         firefoxProfile.setPreference("extensions.firebug.cookies.enableSites", true)
         firefoxProfile.setPreference("extensions.firebug.console.enableSites", true)
      } else {
         println "ERROR: FIREBUG XPI FILE NOT FOUND"
      }
   }

   static void installModifyHeadersPlugin(FirefoxProfile firefoxProfile) {
      URL fileUrl = ClassLoader.getResource("/xpi/modify_headers-0.7.1.1-fx.xpi")
      if (fileUrl) {
         File file = new File(fileUrl.toURI())
         firefoxProfile.addExtension(file)
         firefoxProfile.setPreference("modifyheaders.headers.count", 1);
         firefoxProfile.setPreference("modifyheaders.headers.action0", "Add");
         firefoxProfile.setPreference("modifyheaders.headers.name0", "noconversion");
         firefoxProfile.setPreference("modifyheaders.headers.value0", "1"); // could be any
         firefoxProfile.setPreference("modifyheaders.headers.enabled0", true);
         firefoxProfile.setPreference("modifyheaders.config.active", true);
         firefoxProfile.setPreference("modifyheaders.config.alwaysOn", true);
      } else {
         println "ERROR: MODIFY-HEADER XPI FILE NOT FOUND"
      }
   }

/**
 * Tries to look up propertyName in JVM properties first, OS env vars second and uses fallback if not found.
 */
   static String readPropertyInOrder(String propertyName, String fallback) {
      String jvmProperty = System.getProperty(propertyName)
      String osProperty = System.getenv(propertyName)
      if (jvmProperty != null) {
         return jvmProperty
      } else if (osProperty != null) {
         return osProperty
      } else {
         return fallback
      }
   }
}
