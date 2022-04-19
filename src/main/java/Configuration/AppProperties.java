package Configuration;

import Models.Browser;
import Models.EnvironmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AppProperties {
    static Logger log = LoggerFactory.getLogger(AppProperties.class);
    YamlReader yamlReader = new YamlReader();
    private Browser browser;
    private List<EnvironmentModel> listOfEnvironments;

    public AppProperties() {
        setBrowserProperties();
        setEnvironmentProperties();
    }

    public static AppProperties getInstance() {
        return AppPropertiesSingleton.INSTANCE;
    }

    private void setEnvironmentProperties() {
        listOfEnvironments = yamlReader.getConfig().getEnvironment().getListOfEnvironments();
        boolean foundActiveEnvironment = false;
        for (EnvironmentModel environmentModel : listOfEnvironments) {
            if (environmentModel.isActive()) {
                foundActiveEnvironment = true;
                Map<String, Object> environmentProperties = environmentModel.getProperties();
                for (Map.Entry entry : environmentProperties.entrySet()) {
                    System.setProperty(entry.getKey().toString(), entry.getValue().toString());
                    log.info("Loaded env properties: {} = {}", entry.getKey().toString(), entry.getValue().toString());
                }
                break;
            }
        }

    }

    private void setBrowserProperties() {
        browser = yamlReader.getConfig().getBrowser();
        Map<String, Object> browserProperties = browser.getBrowserProperties();
        for (Map.Entry entry : browserProperties.entrySet()) {
            System.setProperty(entry.getKey().toString(), entry.getValue().toString());
            log.info("Loaded browser properties: {} = {}", entry.getKey().toString(), entry.getValue().toString());
        }
    }

    private static class AppPropertiesSingleton {
        private static final AppProperties INSTANCE = new AppProperties();
    }
}
