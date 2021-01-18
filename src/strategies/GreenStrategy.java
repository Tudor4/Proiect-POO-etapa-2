package strategies;

import data.Data;
import data.Distributor;
import data.Producer;

import java.util.ArrayList;
import java.util.List;

public class GreenStrategy implements Strategy{

    @Override
    public List<Producer> strategy(Data data, Distributor distributor) {
        List<Producer> greenProducers = new ArrayList<>();
        List<Producer> dirtyProducers = new ArrayList<>();

        for (Producer producer : data.getProducers()) {
            if (producer.getEnergyType().equals("WIND")
                    || producer.getEnergyType().equals("SOLAR")
                    || producer.getEnergyType().equals("HYDRO")) {
                greenProducers.add(producer);
            } else {
                dirtyProducers.add(producer);
            }
        }

        greenProducers.sort((A, B) -> {
            if (Double.compare(A.getPriceKW(), B.getPriceKW()) != 0) {
                return Double.compare(A.getPriceKW(), B.getPriceKW());
            } else {
                if (Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor()) != 0) {
                    return (-1) * Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor());
                } else {
                    return Integer.compare(A.getId(), B.getId());
                }
            }
        });

        dirtyProducers.sort((A, B) -> {
            if (Double.compare(A.getPriceKW(), B.getPriceKW()) != 0) {
                return Double.compare(A.getPriceKW(), B.getPriceKW());
            } else {
                if (Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor()) != 0) {
                    return (-1) * Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor());
                } else {
                    return Integer.compare(A.getId(), B.getId());
                }
            }
        });

        List<Producer> sortedProducers = new ArrayList<>();
        sortedProducers.addAll(greenProducers);
        sortedProducers.addAll(dirtyProducers);

        List<Producer> result =  new ArrayList<>();
        int energyTotal = 0;

        while (energyTotal < distributor.getEnergyNeeded()) {
            if (sortedProducers.get(0).getDistributors().size() != sortedProducers.get(0).getMaxDistributors()) {
                result.add(sortedProducers.get(0));
                energyTotal += sortedProducers.get(0).getEnergyPerDistributor();
            }
            sortedProducers.remove(0);
        }
        for (Producer producer : result) {
            producer.getDistributors().add(distributor);
        }

        return result;
    }
}
