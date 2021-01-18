package output;

import data.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public final class Writer {
    private final FileWriter file;

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }

    /**
     * Metoda ce face afisarea in format json.
     * @param data
     * @throws IOException
     */
    public void jsonWrite(final Data data) throws IOException {
        JSONObject result = new JSONObject();
        JSONArray consumers = new JSONArray();
        JSONArray distributors = new JSONArray();
        JSONArray producers = new JSONArray();

        for (Consumer consumer : data.getConsumers()) {
            JSONObject jsonConsumer = new JSONObject();
            jsonConsumer.put("id", consumer.getId());
            jsonConsumer.put("isBankrupt", consumer.isBankrupt());
            jsonConsumer.put("budget", consumer.getBudget());
            consumers.add(jsonConsumer);
        }

        for (Distributor distributor : data.getDistributors()) {
            JSONObject jsonDistributor = new JSONObject();
            jsonDistributor.put("id", distributor.getId());
            jsonDistributor.put("energyNeededKW", distributor.getEnergyNeeded());
            jsonDistributor.put("contractCost", distributor.getPrice());
            jsonDistributor.put("budget", distributor.getBudget());
            jsonDistributor.put("producerStrategy", distributor.getStrategy());
            jsonDistributor.put("isBankrupt", distributor.isBankrupt());

            JSONArray jsonContracts = new JSONArray();
            for (Contract contract : distributor.getContracts()) {
                JSONObject jsonContract = new JSONObject();
                jsonContract.put("consumerId", contract.getId());
                jsonContract.put("price", contract.getPrice());
                jsonContract.put("remainedContractMonths", contract.getRemainedContractMonths());

                jsonContracts.add(jsonContract);
            }
            jsonDistributor.put("contracts", jsonContracts);
            distributors.add(jsonDistributor);
        }

        for (Producer producer : data.getProducers()) {
            JSONObject jsonProducer = new JSONObject();
            jsonProducer.put("id", producer.getId());
            jsonProducer.put("maxDistributors", producer.getMaxDistributors());
            jsonProducer.put("priceKW", producer.getPriceKW());
            jsonProducer.put("energyType", producer.getEnergyType());
            jsonProducer.put("energyPerDistributor", producer.getEnergyPerDistributor());

            JSONArray jsonMonthlyStats = new JSONArray();
            for (Month month : producer.getMonthlyStats()) {
                JSONObject jsonStat = new JSONObject();
                jsonStat.put("month", month.getMonthNr());
                JSONArray jsonIds = new JSONArray();
                for (Integer id : month.getDistributorIds()) {
                    jsonIds.add(id);
                }
                jsonStat.put("distributorsIds", jsonIds);

                jsonMonthlyStats.add(jsonStat);
            }
            jsonProducer.put("monthlyStats", jsonMonthlyStats);
            producers.add(jsonProducer);
        }
        result.put("consumers", consumers);
        result.put("distributors", distributors);
        result.put("energyProducers", producers);
        file.write(result.toJSONString());
        file.flush();
        file.close();
    }
}
