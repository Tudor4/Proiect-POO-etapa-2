package data;

public final class Contract {
    private int id;
    private int price;
    private int remainedContractMonths;

    public Contract(final int id, final int price, final int remainedContractMonths) {
        this.id = id;
        this.price = price;
        this.remainedContractMonths = remainedContractMonths;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getRemainedContractMonths() {
        return remainedContractMonths;
    }

    public void setRemainedContractMonths(final int remainedContractMonths) {
        this.remainedContractMonths = remainedContractMonths;
    }

    @Override
    public String toString() {
        return "Contract{"
                + "id=" + id
                + ", price=" + price
                + ", remainedContractMonths=" + remainedContractMonths
                + '}';
    }
}
