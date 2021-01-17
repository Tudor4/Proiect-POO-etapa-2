package input;

public final class InputDistributorData {
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;

    public InputDistributorData(final int id, final int contractLength, final int initialBudget,
                                final int initialInfrastructureCost,
                                final int initialProductionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = initialBudget;
        this.infrastructureCost = initialInfrastructureCost;
        this.productionCost = initialProductionCost;
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

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    @Override
    public String toString() {
        return "Distributor{"
                + "id=" + id
                + ", contractLength=" + contractLength
                + ", budget=" + budget
                + ", infrastructureCost=" + infrastructureCost
                + ", productionCost=" + productionCost
                + '}';
    }
}
