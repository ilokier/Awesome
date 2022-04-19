package Models;

import java.util.ArrayList;
import java.util.List;

public class Environment {
    public EnvironmentModel test;
    public EnvironmentModel test2;

    public EnvironmentModel getTest() {
        return test;
    }

    public EnvironmentModel getTest2() {
        return test2;
    }

    public List<EnvironmentModel> getListOfEnvironments() {
        List<EnvironmentModel> listOfEnvironments = new ArrayList<>();
        listOfEnvironments.add(getTest());
        listOfEnvironments.add(getTest2());
        return listOfEnvironments;
    }
}
