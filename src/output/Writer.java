package output;

import data.Consumer;
import data.Contract;
import data.Data;
import data.Distributor;
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
            jsonDistributor.put("budget", distributor.getBudget());
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
        result.put("consumers", consumers);
        result.put("distributors", distributors);
        file.write(result.toJSONString());
        file.flush();
        file.close();
    }
}
