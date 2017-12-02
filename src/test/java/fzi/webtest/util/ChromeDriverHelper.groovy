package fzi.webtest.util

import org.apache.commons.exec.OS
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver.Window
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

public class ChromeDriverHelper {
   static final int DESKTOP_HEIGHT = 1100
   static final int MOBILE_HEIGHT = 667 // consider setting to DESKTOP_HEIGHT if scrolling calls get tedious

   static final int DESKTOP_WIDTH = 1420
   static final int MOBILE_WIDTH = 337

   static final ThreadLocal<ChromeDriver> chromeDriver = new ThreadLocal<ChromeDriver>() {
      @Override
      protected ChromeDriver initialValue() {
         return ChromeDriverHelper.newChromeDriver(false)
      }
   }

   static final ThreadLocal<ChromeDriver> chromeMobileDriver = new ThreadLocal<ChromeDriver>() {
      @Override
      protected ChromeDriver initialValue() {
         ChromeDriver driver = ChromeDriverHelper.newChromeDriver(true)

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

   static ChromeDriver newChromeDriver(boolean isMobile) {
      System.setProperty("webdriver.chrome.driver", getChromeBinary())

      DesiredCapabilities capabilities = DesiredCapabilities.chrome()
      capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true)
      capabilities.setCapability(ChromeOptions.CAPABILITY, newChromeOptions(isMobile))

      ChromeDriver chromeDriver = new ChromeDriver(capabilities)

      Window window = chromeDriver.manage().window()
      if (!isMobile) {
         window.setSize(new Dimension(DESKTOP_WIDTH, DESKTOP_HEIGHT))
      } else {
         window.setSize(new Dimension(MOBILE_WIDTH, MOBILE_HEIGHT))
      }

      configureModHeaderPlugin(chromeDriver)

      return chromeDriver
   }

    private static String getChromeBinary() {
        String chromeDriverBinary = "chromedriver" // Linux bin
        if (OS.isFamilyMac()) {
            chromeDriverBinary = "chromedriver.mac"
        } else if (OS.isFamilyWindows()) {
            chromeDriverBinary = "chromedriver.exe"
        }
        chromeDriverBinary
    }


    static ChromeOptions newChromeOptions(boolean isMobile) {
      // A new Chrome profile folder will be created in operatings systems temp folder. e.g. 'C:\Users\myUserName\AppData\Local\Temp\anonymous4018215231328960954webdriver-profile' on driver creation
      ChromeOptions options = new ChromeOptions()

      String executable = readPropertyInOrder("CHROME_EXECUTABLE", null)
      if (executable!=null) {
         options.setBinary(executable)
         println 'using executable: '+executable
      }

      options.addArguments("disable-infobars")
      options.addArguments("ignore-certificate-errors")

      if (isMobile) {
         Map<String, String> deviceMetrics = new HashMap<String, Object>()
         deviceMetrics.put("width", MOBILE_WIDTH)
         deviceMetrics.put("height", MOBILE_HEIGHT)
         deviceMetrics.put("pixelRatio", 1.0)

         Map<String, String> mobileEmulation = new HashMap<String, String>()
         mobileEmulation.put("deviceMetrics", deviceMetrics)
         mobileEmulation.put("userAgent", Constants.USER_AGENT_MOBILE)

         options.setExperimentalOption("mobileEmulation", mobileEmulation)
      }

      installModHeaderPlugin(options)

      return options
   }

   static void installModHeaderPlugin(ChromeOptions chromeOptions) {
      File file = new File("src/test/resources/ModHeader_v2.1.2.crx")
      chromeOptions.addExtensions(file)
   }

   static ChromeDriver configureModHeaderPlugin(ChromeDriver chromeDriver) {
      // Go to a page of the ModHeader extension
      chromeDriver.get("chrome-extension://idgpnmonknjnojddfkpgkljpfnnfcklj/icon.png")
      // Write the config to the local storage
      chromeDriver.executeScript("localStorage.setItem('profiles', '[{\"title\":\"Profile 1\",\"hideComment\":true,\"headers\":[{\"enabled\":true,\"name\":\"X-webtest\",\"value\":\"1\",\"comment\":\"\"}],\"respHeaders\":[],\"filters\":[],\"appendMode\":\"\"}]')")
      return chromeDriver
   }

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
