package data;

import java.util.List;

public class Month {
    private int monthNr;
    private List<Integer> distributorIds;

    public Month(int monthNr, List<Integer> distributorIds) {
        this.monthNr = monthNr;
        this.distributorIds = distributorIds;
    }

    public int getMonthNr() {
        return monthNr;
    }

    public List<Integer> getDistributorIds() {
        return distributorIds;
    }
}
