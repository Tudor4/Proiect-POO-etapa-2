package input;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {
    private String path;

    public InputLoader(final String path) {
        this.path = path;
    }

    /**
     * Metoda parseaza datele din fisierul json de intrare
     * si introduce datele in obiecte de tip InputConsumerData,
     * InputDistributorData si InputMonthlyUpdateData.
     * @return
     */
    public Input getData() {
        JSONParser jsonParser = new JSONParser();
        List<InputConsumerData> consumers = new ArrayList<>();
        List<InputDistributorData> distributors = new ArrayList<>();
        List<InputProducerData> producers = new ArrayList<>();
        List<InputMonthlyUpdateData> monthlyUpdates = new ArrayList<>();
        int numberOfTurns = 0;

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
            numberOfTurns = Integer.parseInt(jsonObject.get(Constants.NUMBER_OF_TURNS).toString());
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray) initialData.get(Constants.PRODUCERS);
            JSONArray jsonMonthlyUpdates = (JSONArray) jsonObject.get(Constants.MONTHLY_UPDATES);

            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add(new InputConsumerData(Integer.parseInt(((JSONObject) jsonConsumer)
                            .get(Constants.ID).toString()),
                            Integer.parseInt(((JSONObject) jsonConsumer)
                                    .get(Constants.INITIAL_BUDGET).toString()),
                            Integer.parseInt(((JSONObject) jsonConsumer)
                                    .get(Constants.MONTHLY_INCOME).toString())));
                }
            }

            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {
                    distributors.add(new InputDistributorData(Integer.parseInt(
                            ((JSONObject) jsonDistributor).get(Constants.ID).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.CONTRACT_LENGTH).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIAL_BUDGET).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIAL_INFRASTRUCTURE_COST).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.ENERGY_NEEDED).toString()),
                            ((JSONObject) jsonDistributor).get(Constants.STRATEGY).toString()));
                }
            }

            if (jsonProducers != null) {
                for (Object jsonProducer : jsonProducers) {
                    producers.add(new InputProducerData(Integer.parseInt(
                            ((JSONObject) jsonProducer).get(Constants.ID).toString()),
                            ((JSONObject) jsonProducer).get(Constants.ENERGY_TYPE).toString(),
                            Integer.parseInt(((JSONObject) jsonProducer)
                                    .get(Constants.MAX_DISTRIBUTORS).toString()),
                            Double.parseDouble(((JSONObject) jsonProducer)
                                    .get(Constants.PRICE_KW).toString()),
                            Integer.parseInt(((JSONObject) jsonProducer)
                                    .get(Constants.ENERGY_PER_DISTRIBUTOR).toString())));
                }
            }

            if (jsonMonthlyUpdates != null) {
                for (Object jsonMonthlyUpdate : jsonMonthlyUpdates) {
                    JSONArray jsonNewConsumers = (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.NEW_CONSUMERS);
                    JSONArray jsonDistributorChanges = (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.DISTRIBUTOR_CHANGES);
                    JSONArray jsonProducerChanges = (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.PRODUCER_CHANGES);
                    List<InputConsumerData> newConsumers = new ArrayList<>();
                    List<Integer> distributorsChanged = new ArrayList<>();
                    List<Integer> infrastructureCostChanges = new ArrayList<>();
                    List<Integer> producersChanged = new ArrayList<>();
                    List<Integer> energyPerDistributor = new ArrayList<>();

                    if (jsonNewConsumers != null) {
                        for (Object jsonNewConsumer : jsonNewConsumers) {
                            newConsumers.add(new InputConsumerData(Integer
                                    .parseInt(((JSONObject) jsonNewConsumer)
                                            .get(Constants.ID).toString()),
                                    Integer.parseInt(((JSONObject) jsonNewConsumer)
                                            .get(Constants.INITIAL_BUDGET).toString()),
                                    Integer.parseInt(((JSONObject) jsonNewConsumer)
                                            .get(Constants.MONTHLY_INCOME).toString())));
                        }
                    }
                    if (jsonDistributorChanges != null) {
                        for (Object jsonDistributorChange : jsonDistributorChanges) {
                            infrastructureCostChanges.add(Integer
                                    .parseInt(((JSONObject) jsonDistributorChange)
                                            .get(Constants.INFRASTRUCTURE_COST).toString()));
                            distributorsChanged.add(Integer
                                    .parseInt(((JSONObject) jsonDistributorChange)
                                    .get(Constants.ID).toString()));
                        }
                    }
                    if (jsonProducerChanges != null) {
                        for (Object jsonProducerChange : jsonProducerChanges) {
                            energyPerDistributor.add(Integer
                                    .parseInt(((JSONObject) jsonProducerChange)
                                    .get(Constants.ENERGY_PER_DISTRIBUTOR).toString()));
                            producersChanged.add(Integer.parseInt(((JSONObject) jsonProducerChange)
                                    .get(Constants.ID).toString()));
                        }
                    }
                    monthlyUpdates.add(new InputMonthlyUpdateData(
                            newConsumers, infrastructureCostChanges,
                            distributorsChanged, energyPerDistributor,
                            producersChanged));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Input(numberOfTurns, consumers, distributors, producers, monthlyUpdates);
    }
}
