package fzi.webtest.config.mobile

import geb.driver.DriverFactory

import org.openqa.selenium.WebDriver

import fzi.webtest.util.FirefoxDriverHelper

class MobileDriverFactory implements DriverFactory {

   @Override
   public WebDriver getDriver() {
      return FirefoxDriverHelper.firefoxMobileDriver.get()
   }
}
