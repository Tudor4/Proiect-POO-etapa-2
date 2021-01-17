import input.InputLoader;
import input.InputConsumerData;
import input.InputDistributorData;
import input.InputMonthlyUpdateData;
import input.Input;
import data.Consumer;
import data.Distributor;
import data.Data;
import data.DataFactory;
import data.MonthlyUpdate;
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

        Writer writer = new Writer(args[1]);
        writer.jsonWrite(data);
    }
}