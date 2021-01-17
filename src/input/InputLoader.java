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
        List<InputMonthlyUpdateData> monthlyUpdates = new ArrayList<>();
        int numberOfTurns = 0;

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(path));
            numberOfTurns = Integer.parseInt(jsonObject.get(Constants.NUMBER_OF_TURNS).toString());
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonConsumers = (JSONArray) initialData.get(Constants.CONSUMERS);
            JSONArray jsonDistributors = (JSONArray) initialData.get(Constants.DISTRIBUTORS);
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
                                    .get(Constants.INITIAL_PRODUCTION_COST).toString())));
                }
            }

            if (jsonMonthlyUpdates != null) {
                for (Object jsonMonthlyUpdate : jsonMonthlyUpdates) {
                    JSONArray jsonNewConsumers = (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.NEW_CONSUMERS);
                    JSONArray jsonCostChanges = (JSONArray) ((JSONObject) jsonMonthlyUpdate)
                            .get(Constants.COST_CHANGES);
                    List<InputConsumerData> newConsumers = new ArrayList<>();
                    List<InputDistributorData> distributorsChanged = new ArrayList<>();
                    List<Integer> infrastructureCostChanges = new ArrayList<>();
                    List<Integer> productionCostChanges = new ArrayList<>();

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
                    if (jsonCostChanges != null) {
                        for (Object jsonCostChange : jsonCostChanges) {
                            infrastructureCostChanges.add(Integer
                                    .parseInt(((JSONObject) jsonCostChange)
                                            .get(Constants.INFRASTRUCTURE_COST).toString()));
                            productionCostChanges.add(Integer.parseInt(((JSONObject) jsonCostChange)
                                    .get(Constants.PRODUCTION_COST).toString()));
                            int id = Integer.parseInt(((JSONObject) jsonCostChange)
                                    .get(Constants.ID).toString());
                            for (InputDistributorData distributor : distributors) {
                                if (id == distributor.getId()) {
                                    distributorsChanged.add(distributor);
                                }
                            }
                        }
                    }
                    monthlyUpdates.add(new InputMonthlyUpdateData(
                            newConsumers, infrastructureCostChanges,
                            productionCostChanges, distributorsChanged));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Input(numberOfTurns, consumers, distributors, monthlyUpdates);
    }
}
