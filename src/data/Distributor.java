package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class Distributor{
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private int energyNeeded;
    private String strategy;
    private int numberOfConsumers = 0;
    private int price = 0;
    private List<Contract> contracts = new ArrayList<>();
    private boolean isBankrupt = false;
    private List<Consumer> consumers = new ArrayList<>();
    private List<Producer> producers = new ArrayList<>();

    public Distributor(final int id, final int contractLength, final int initialBudget,
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

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public int getNumberOfConsumers() {
        return numberOfConsumers;
    }

    public void setNumberOfConsumers(final int numberOfConsumers) {
        this.numberOfConsumers = numberOfConsumers;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public int getEnergyNeeded() {
        return energyNeeded;
    }

    public void setEnergyNeeded(int energyNeeded) {
        this.energyNeeded = energyNeeded;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public void setContracts(final List<Contract> contracts) {
        this.contracts = contracts;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Producer> getProducers() {
        return producers;
    }


    @Override
    public String toString() {
        return "Distributor{" +
                "id=" + id +
                ", energyNeeded=" + energyNeeded +
                ", price=" + price +
                ", budget=" + budget +
                ", strategy='" + strategy + '\'' +
                ", isBankrupt=" + isBankrupt +
                ", contracts=" + contracts +
                '}';
    }
}
