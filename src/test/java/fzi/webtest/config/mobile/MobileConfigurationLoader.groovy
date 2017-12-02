package fzi.webtest.config.mobile

import geb.ConfigurationLoader

class MobileConfigurationLoader extends ConfigurationLoader {
    MobileConfigurationLoader(String environment) {
        super(environment, null, null)
    }

    @Override
    protected createConf(ConfigObject rawConfig, GroovyClassLoader classLoader) {
        new MobileConfiguration(rawConfig, properties, createBuildAdapter(classLoader), classLoader)
    }
}
