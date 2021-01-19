package strategies;

import data.Data;
import data.Distributor;
import data.Producer;

import java.util.List;

public interface Strategy {
    /**
     *
     * @param data
     * @param distributor
     * @return
     */
    List<Producer> strategy(Data data, Distributor distributor);
}
