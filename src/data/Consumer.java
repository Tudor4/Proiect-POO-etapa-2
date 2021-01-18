package data;

public final class Consumer {
    private int id;
    private int budget;
    private int monthlyIncome;
    private Contract contract = null;
    private int penalty = 0;
    private Distributor contractor = null;
    private Distributor penaltyContractor = null;
    private boolean hasContract = false;
    private boolean isBankrupt = false;

    public Consumer(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.budget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(final Contract contract) {
        this.contract = contract;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(final int penalty) {
        this.penalty = penalty;
    }

    public Distributor getContractor() {
        return contractor;
    }

    public void setContractor(final Distributor contractor) {
        this.contractor = contractor;
    }

    public Distributor getPenaltyContractor() {
        return penaltyContractor;
    }

    public void setPenaltyContractor(final Distributor penaltyContractor) {
        this.penaltyContractor = penaltyContractor;
    }

    /**
     *
     * @return
     */
    public boolean hasContract() {
        return hasContract;
    }

    public void setHasContract(final boolean hasContract) {
        this.hasContract = hasContract;
    }


    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", budget=" + budget +
                ", monthlyIncome=" + monthlyIncome +
                '}';
    }
}
