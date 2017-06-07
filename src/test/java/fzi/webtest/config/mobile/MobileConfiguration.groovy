package fzi.webtest.config.mobile

import geb.BuildAdapter
import geb.Configuration
import geb.driver.DriverFactory

class MobileConfiguration extends Configuration {

   private static MobileDriverFactory mobileDriverFactory = new MobileDriverFactory()

   MobileConfiguration(ConfigObject rawConfig = null, Properties properties = null, BuildAdapter buildAdapter = null, ClassLoader classLoader = null) {
      super(rawConfig, properties, buildAdapter, classLoader)
   }

   @Override
   protected DriverFactory wrapDriverFactoryInCachingIfNeeded( DriverFactory factory ) {
      return mobileDriverFactory
   }
}
