package input;

import java.util.List;

public final class InputMonthlyUpdateData {
    private List<InputConsumerData> newConsumers;
    private List<Integer> infrastructureCosts;
    private List<Integer> distributorIds;
    private List<Integer> energyPerDistributor;
    private List<Integer> producerIds;

    public InputMonthlyUpdateData(final List<InputConsumerData> newConsumers,
                                  final List<Integer> infrastructureCosts,
                                  final List<Integer> distributorIds,
                                  final List<Integer> energyPerDistributor,
                                  final List<Integer> producerIds) {
        this.newConsumers = newConsumers;
        this.infrastructureCosts = infrastructureCosts;
        this.distributorIds = distributorIds;
        this.energyPerDistributor = energyPerDistributor;
        this.producerIds = producerIds;
    }

    public List<InputConsumerData> getNewConsumers() {
        return newConsumers;
    }

    public List<Integer> getInfrastructureCosts() {
        return infrastructureCosts;
    }

    public List<Integer> getDistributorIds() {
        return distributorIds;
    }

    public List<Integer> getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public List<Integer> getProducerIds() {
        return producerIds;
    }
}
