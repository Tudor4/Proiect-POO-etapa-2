import data.*;
import input.*;
import output.Writer;

public final class Main {
    private Main() {
    }
    /**
     * Scheletul programlui.
     * Parseaza datele, apeleaza
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        String inputPath = args[0];
        InputLoader inputLoader = new InputLoader(inputPath);
        Input input = inputLoader.getData();
        Data data = new Data();
        data.setNumberOfTurns(input.getNumberOfTurns());
        DataFactory factory = DataFactory.getInstance();

        for (InputConsumerData inputConsumerData : input.getConsumers()) {
            data.getConsumers().add((Consumer) factory.factory(inputConsumerData));
        }
        for (InputDistributorData inputDistributorData : input.getDistributors()) {
            data.getDistributors().add((Distributor) factory.factory(inputDistributorData));
        }
        for (InputProducerData inputProducerData : input.getProducers()) {
            data.getProducers().add((Producer) factory.factory(inputProducerData));
        }
        for (InputMonthlyUpdateData inputMonthlyUpdateData : input.getMonthlyUpdates()) {
            data.getMonthlyUpdates().add((MonthlyUpdate) factory.factory(inputMonthlyUpdateData));
        }

        data.simulateTurn();
        for (MonthlyUpdate update : data.getMonthlyUpdates()) {
            data.update(update);
            data.simulateTurn();
            if (data.isGameOver()) {
                break;
            }
        }

        /*for (Consumer consumer : data.getConsumers()) {
            System.out.println(consumer);
        }
        for (Distributor distributor : data.getDistributors()) {
            System.out.println(distributor);
        }
        for (Producer producer : data.getProducers()) {
            System.out.println(producer);
        }*/
        Writer writer = new Writer(args[1]);
        writer.jsonWrite(data);
    }
}
