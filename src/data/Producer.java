package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Producer extends Observable {
    private int id;
    private String energyType;
    private int maxDistributors;
    private double priceKW;
    private int energyPerDistributor;
    private List<Distributor> distributors = new ArrayList<>();
    private List<Month> monthlyStats = new ArrayList<>();

    public Producer(int id, String energyType, int maxDistributors,
                             double priceKW, int energyPerDistributor) {
        this.id = id;
        this.energyType = energyType;
        this.maxDistributors = maxDistributors;
        this.priceKW = priceKW;
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public double getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(double priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    public List<Month> getMonthlyStats() {
        return monthlyStats;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", energyType='" + energyType + '\'' +
                ", maxDistributors=" + maxDistributors +
                ", priceKW=" + priceKW +
                ", energyPerDistributor=" + energyPerDistributor +
                '}';
    }
}
