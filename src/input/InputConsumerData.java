package input;

public final class InputConsumerData {
    private int id;
    private int budget;
    private int monthlyIncome;

    public InputConsumerData(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.budget = initialBudget;
        this.monthlyIncome = monthlyIncome;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public String toString() {
        return "Consumer{"
                + "id=" + id
                + ", budget=" + budget
                + ", monthlyIncome=" + monthlyIncome
                + '}';
    }
}
