package data;

import input.InputConsumerData;
import input.InputDistributorData;
import input.InputMonthlyUpdateData;
import input.InputProducerData;

public final class DataFactory {
    private static DataFactory instance = null;

    private DataFactory() {

    }

    /**
     *
     * @return
     */
    public static DataFactory getInstance() {
        if (instance == null) {
            instance = new DataFactory();
        }
        return instance;
    }

    /**
     * factory pentru Consumer
     * @param inputConsumer
     * @return
     */
    public Consumer createConsumer(final InputConsumerData inputConsumer) {
        return new Consumer(inputConsumer.getId(), inputConsumer.getBudget(),
                inputConsumer.getMonthlyIncome());
    }

    /**
     * factory pentru Distributor
     * @param inputDistributor
     * @return
     */
    public Distributor createDistributor(final InputDistributorData inputDistributor) {
        return new Distributor(inputDistributor.getId(), inputDistributor.getContractLength(),
                inputDistributor.getBudget(), inputDistributor.getInfrastructureCost(),
                inputDistributor.getEnergyNeeded(), inputDistributor.getStrategy());
    }

    /**
     * factory pentru Producer
     * @param inputProducer
     * @return
     */
    public Producer createProducer(final InputProducerData inputProducer) {
        return new Producer(inputProducer.getId(), inputProducer.getEnergyType(),
                inputProducer.getMaxDistributors(), inputProducer.getPriceKW(),
                inputProducer.getEnergyPerDistributor());
    }

    /**
     * factory pentru MontlyUpdate
     * @param inputMonthlyUpdate
     * @return
     */
    public MonthlyUpdate createMonthlyUpdate(final InputMonthlyUpdateData inputMonthlyUpdate) {
        MonthlyUpdate monthlyUpdate = new MonthlyUpdate();
        for (InputConsumerData inputConsumer : inputMonthlyUpdate.getNewConsumers()) {
            monthlyUpdate.getNewConsumers().add(createConsumer(inputConsumer));
        }
        monthlyUpdate.getDistributorIds().addAll(inputMonthlyUpdate.getDistributorIds());
        monthlyUpdate.getInfrastructureCosts().addAll(inputMonthlyUpdate.getInfrastructureCosts());
        monthlyUpdate.getEnergyPerDistributor()
                .addAll(inputMonthlyUpdate.getEnergyPerDistributor());
        monthlyUpdate.getProducersIds().addAll(inputMonthlyUpdate.getProducerIds());
        return monthlyUpdate;
    }

    /**
     * Implementarea factory pattern.
     * Metoda primeste obiecte de tip InputConsumerData, InputDistributorData
     * si InputMonthlyUpdateData, creeaza obiecte de tip Consumer, Distributor
     * si MonthlyUpdate si le returneaza in main.
     * @param object
     * @return
     */
    public Object factory(final Object object) {
        if (object instanceof InputConsumerData) {
            return createConsumer((InputConsumerData) object);
        } else if (object instanceof InputDistributorData) {
            return createDistributor((InputDistributorData) object);
        } else if (object instanceof  InputProducerData) {
            return createProducer((InputProducerData) object);
        } else {
            return createMonthlyUpdate((InputMonthlyUpdateData) object);
        }
    }
}
