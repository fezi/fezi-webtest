package fzi.webtest.util

import org.apache.commons.exec.OS
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver.Window
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities

public class ChromeDriverHelper {
   public static final boolean IS_WINDOWS = OS.isFamilyWindows()
   public static final boolean IS_MAC = OS.isFamilyMac()
   public static final boolean IS_LINUX = !IS_MAC && OS.isFamilyUnix()

   static final int DESKTOP_HEIGHT = 1100
   static final int MOBILE_HEIGHT = 667 // consider setting to DESKTOP_HEIGHT if scrolling calls get tedious

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
      String chromeDriverBinary = "chromedriver"
      if (IS_MAC) {
         chromeDriverBinary = "chromedriver.mac"
      } else if (IS_WINDOWS) {
         chromeDriverBinary = "chromedriver.exe"
      }
      System.setProperty("webdriver.chrome.driver", chromeDriverBinary)

      DesiredCapabilities capabilities = DesiredCapabilities.chrome()
      capabilities.setCapability(CapabilityType.OVERLAPPING_CHECK_DISABLED, true)
      capabilities.setCapability(ChromeOptions.CAPABILITY, newChromeOptions(isMobile))

      ChromeDriver chromeDriver = new ChromeDriver(capabilities)

      Window window = chromeDriver.manage().window()
      if (!isMobile) {
         window.setSize(new Dimension(1420, DESKTOP_HEIGHT))
      } else {
         window.setSize(new Dimension(337, MOBILE_HEIGHT))
      }

      // Very long timeout to prevent false positives, because of external JS libraries
      //chromeDriver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS)

      configureModHeaderPlugin(chromeDriver)

      return chromeDriver
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
         deviceMetrics.put("width", 337)
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

      // Write the config to the local storage. For changes, simply adjust the JSON string or export the profile of your local installation and paste it here (attention: Add the [] around the export!)
      chromeDriver.executeScript("localStorage.setItem('profiles', '[{\"title\":\"Profile 1\",\"hideComment\":true,\"headers\":[{\"enabled\":true,\"name\":\"noconversion\",\"value\":\"1\",\"comment\":\"\"},{\"enabled\":true,\"name\":\"webtest\",\"value\":\"1\",\"comment\":\"\"}],\"respHeaders\":[],\"filters\":[],\"appendMode\":\"\"}]')")

      return chromeDriver
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
