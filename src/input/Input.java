package input;

import java.util.List;

public final class Input {
    private int numberOfTurns;
    private List<InputConsumerData> consumers;
    private List<InputDistributorData> distributors;
    private List<InputProducerData> producers;
    private List<InputMonthlyUpdateData> monthlyUpdates;

    public Input(final int numberOfTurns, final List<InputConsumerData> consumers,
                 final List<InputDistributorData> distributors,
                 final List<InputProducerData> producers,
                 final List<InputMonthlyUpdateData> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
        this.monthlyUpdates = monthlyUpdates;
    }

    public List<InputConsumerData> getConsumers() {
        return consumers;
    }

    public List<InputDistributorData> getDistributors() {
        return distributors;
    }

    public List<InputProducerData> getProducers() {
        return producers;
    }

    public List<InputMonthlyUpdateData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
