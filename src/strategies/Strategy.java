package strategies;

import data.Data;
import data.Distributor;
import data.Producer;

import java.util.List;

public interface Strategy {
    public List<Producer> strategy(Data data, Distributor distributor);
}
