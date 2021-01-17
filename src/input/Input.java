package input;

import java.util.List;

public final class Input {
    private int numberOfTurns;
    private List<InputConsumerData> consumers;
    private List<InputDistributorData> distributors;
    private List<InputMonthlyUpdateData> monthlyUpdates;

    public Input(final int numberOfTurns, final List<InputConsumerData> consumers,
                 final List<InputDistributorData> distributors,
                 final List<InputMonthlyUpdateData> monthlyUpdates) {
        this.numberOfTurns = numberOfTurns;
        this.consumers = consumers;
        this.distributors = distributors;
        this.monthlyUpdates = monthlyUpdates;
    }

    public List<InputConsumerData> getConsumers() {
        return consumers;
    }

    public List<InputDistributorData> getDistributors() {
        return distributors;
    }

    public List<InputMonthlyUpdateData> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }
}
