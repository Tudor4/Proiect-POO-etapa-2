package input;

public final class InputDistributorData {
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private int energyNeeded;
    private String strategy;

    public InputDistributorData(final int id, final int contractLength, final int initialBudget,
                                final int initialInfrastructureCost, final int energyNeeded,
                                final String strategy) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.energyNeeded = energyNeeded;
        this.strategy = strategy;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getEnergyNeeded() {
        return energyNeeded;
    }

    public String getStrategy() {
        return strategy;
    }


}
