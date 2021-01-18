package input;

import java.util.List;

public final class InputMonthlyUpdateData {
    private List<InputConsumerData> newConsumers;
    private List<Integer> infrastructureCosts;
    private List<Integer> distributorIds;
    private List<Integer> EnergyPerDistributor;
    private List<Integer> producerIds;

    public InputMonthlyUpdateData(final List<InputConsumerData> newConsumers,
                                  final List<Integer> infrastructureCosts,
                                  final List<Integer> distributorIds,
                                  final List<Integer> EnergyPerDistributor,
                                  final List<Integer> producerIds) {
        this.newConsumers = newConsumers;
        this.infrastructureCosts = infrastructureCosts;
        this.distributorIds = distributorIds;
        this.EnergyPerDistributor = EnergyPerDistributor;
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
        return EnergyPerDistributor;
    }

    public List<Integer> getProducerIds() {
        return producerIds;
    }
}
