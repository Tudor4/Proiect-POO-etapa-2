package data;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyUpdate {
    private List<Consumer> newConsumers = new ArrayList<>();
    private List<Integer> infrastructureCosts = new ArrayList<>();
    private List<Integer> productionCosts = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<Integer> getInfrastructureCosts() {
        return infrastructureCosts;
    }

    public List<Integer> getProductionCosts() {
        return productionCosts;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    @Override
    public String toString() {
        return "MonthlyUpdate{"
                + "newConsumers=" + newConsumers
                + ", infrastructureCosts=" + infrastructureCosts
                + ", productionCosts=" + productionCosts
                + ", distributors=" + distributors
                + '}';
    }
}
