package data;

import java.util.ArrayList;
import java.util.List;

public final class MonthlyUpdate {
    private List<Consumer> newConsumers = new ArrayList<>();
    private List<Integer> infrastructureCosts = new ArrayList<>();
    private List<Integer> distributorIds = new ArrayList<>();
    private List<Integer> energyPerDistributor = new ArrayList<>();
    private List<Integer> producersIds = new ArrayList<>();

    public List<Consumer> getNewConsumers() {
        return newConsumers;
    }

    public List<Integer> getInfrastructureCosts() {
        return infrastructureCosts;
    }

    public List<Integer> getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<Integer> getDistributorIds() {
        return distributorIds;
    }

    public List<Integer> getProducersIds() {
        return producersIds;
    }


}
