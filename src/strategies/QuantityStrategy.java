package strategies;

import data.Data;
import data.Distributor;
import data.Producer;

import java.util.ArrayList;
import java.util.List;

public class QuantityStrategy implements Strategy{

    @Override
    public List<Producer> strategy(Data data, Distributor distributor) {
        List<Producer> producers = new ArrayList<>();
        producers.addAll(data.getProducers());

        producers.sort((A, B) -> {
            if (Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor()) != 0) {
                return (-1) * Integer.compare(A.getEnergyPerDistributor(), B.getEnergyPerDistributor());
            } else {
                return Integer.compare(A.getId(), B.getId());
            }
        });

        List<Producer> result =  new ArrayList<>();
        int energyTotal = 0;

        while (energyTotal < distributor.getEnergyNeeded()) {
            if (producers.get(0).getDistributors().size() != producers.get(0).getMaxDistributors()) {
                result.add(producers.get(0));
                energyTotal += producers.get(0).getEnergyPerDistributor();
            }
            producers.remove(0);
        }
        for (Producer producer : result) {
            producer.getDistributors().add(distributor);
        }

        return result;
    }
}
