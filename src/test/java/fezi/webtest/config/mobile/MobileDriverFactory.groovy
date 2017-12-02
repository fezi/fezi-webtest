package fezi.webtest.config.mobile

import fezi.webtest.util.ChromeDriverHelper
import geb.driver.DriverFactory
import org.openqa.selenium.WebDriver

class MobileDriverFactory implements DriverFactory {

    @Override
    public WebDriver getDriver() {
        return ChromeDriverHelper.chromeMobileDriver.get()
    }
}
