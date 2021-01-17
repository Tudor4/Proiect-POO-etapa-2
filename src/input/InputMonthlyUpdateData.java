package input;

import java.util.List;

public final class InputMonthlyUpdateData {
    private List<InputConsumerData> newConsumers;
    private List<Integer> infrastructureCosts;
    private List<Integer> productionCosts;
    private List<InputDistributorData> distributors;

    public InputMonthlyUpdateData(final List<InputConsumerData> newConsumers,
                                  final List<Integer> infrastructureCosts,
                                  final List<Integer> productionCosts,
                                  final List<InputDistributorData> distributors) {
        this.newConsumers = newConsumers;
        this.infrastructureCosts = infrastructureCosts;
        this.productionCosts = productionCosts;
        this.distributors = distributors;
    }

    public List<InputConsumerData> getNewConsumers() {
        return newConsumers;
    }

    public List<Integer> getInfrastructureCosts() {
        return infrastructureCosts;
    }

    public List<Integer> getProductionCosts() {
        return productionCosts;
    }

    public List<InputDistributorData> getDistributors() {
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
