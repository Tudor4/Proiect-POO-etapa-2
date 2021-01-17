package data;

import input.Constants;

import java.util.ArrayList;
import java.util.List;

public final class Data {
    private int numberOfTurns;
    private List<Consumer> consumers = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
    private boolean gameOver = false;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public List<MonthlyUpdate> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Executare motnhly updates.
     * @param update
     */
    public void update(final MonthlyUpdate update) {
        consumers.addAll(update.getNewConsumers());
        for (Distributor distributor : update.getDistributors()) {
            for (Distributor databaseDistributor : distributors) {
                if (databaseDistributor.getId() == distributor.getId()) {
                    databaseDistributor.setInfrastructureCost(update
                            .getInfrastructureCosts().get(update
                                    .getDistributors().indexOf(distributor)));
                    databaseDistributor.setProductionCost(update
                            .getProductionCosts().get(update
                                    .getDistributors().indexOf(distributor)));
                }
            }
        }
    }

    /**
     * Rularea unui tur al jocului
     */
    public void simulateTurn() {
        checkContracts();
        calculatePrices();
        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                getIncome(consumer);
                if (!consumer.hasContract()) {
                    chooseContract(consumer);
                }
                pay(consumer);
            }
        }
        completedContracts();
        payExpenses();
        removeContracts();
        checkBankrupcy();
    }

    /**
     * Calcularea preturilor fiecarui distribuitor
     */
    public void calculatePrices() {
        for (Distributor distributor : distributors) {
            if (distributor.getNumberOfConsumers() == 0) {
                distributor.setPrice((int) (distributor
                        .getInfrastructureCost() + distributor.getProductionCost()
                                        + Math.round(Math
                        .floor(Constants.PROFIT * distributor.getProductionCost()))));
            } else {
                distributor.setPrice((int) Math.round(Math.floor((float) distributor
                        .getInfrastructureCost()
                        / distributor.getNumberOfConsumers()) + distributor.getProductionCost()
                        + Math.round(Math.floor(Constants.PROFIT * distributor
                        .getProductionCost()))));
            }
        }
    }

    /**
     * Adauagrea venitului unui consumator
     * @param consumer
     */
    public void getIncome(final Consumer consumer) {
            consumer.setBudget(consumer.getBudget() + consumer.getMonthlyIncome());
    }

    /**
     * Gasirea distribuitorului cu cel mai pret si incheierea
     * unui contract cu acel distribuitor.
     * @param consumer
     */
    public void chooseContract(final Consumer consumer) {
        int min = Integer.MAX_VALUE;
        Distributor smallestPriceDistributor = null;
        for (Distributor distributor : distributors) {
            if (distributor.getPrice() < min && !distributor.isBankrupt()) {
                smallestPriceDistributor = distributor;
                min = distributor.getPrice();
            }
        }
        Contract contract = new Contract(consumer.getId(), smallestPriceDistributor.getPrice(),
                smallestPriceDistributor.getContractLength());
        smallestPriceDistributor.getContracts().add(contract);
        smallestPriceDistributor.setNumberOfConsumers(smallestPriceDistributor
                .getNumberOfConsumers() + 1);
        consumer.setContract(contract);
        consumer.setContractor(smallestPriceDistributor);
        consumer.setHasContract(true);
    }

    /**
     * Plata contractelor si penalizarilor(unde este cazul) de catre consumatori
     * @param consumer
     */
    public void pay(final Consumer consumer) {
        if (consumer.getBudget() < consumer.getContract().getPrice() + consumer.getPenalty()) {
            if (consumer.getPenalty() == 0) {
                consumer.setPenalty((int) Math.round(Math.floor(consumer
                        .getContract().getPrice() * Constants.PENALTY)));
                consumer.setPenaltyContractor(consumer.getContractor());
                consumer.getContract().setRemainedContractMonths(consumer
                        .getContract().getRemainedContractMonths() - 1);
            } else {
                consumer.setBankrupt(true);
                consumer.setPenalty(0);
                consumer.setPenaltyContractor(null);
            }
        } else {
            if (consumer.getPenalty() == 0) {
                consumer.setBudget(consumer.getBudget() - consumer.getContract().getPrice());
                consumer.getContractor().setBudget(consumer.getContractor()
                        .getBudget() + consumer.getContract().getPrice());
                consumer.getContract().setRemainedContractMonths(consumer
                        .getContract().getRemainedContractMonths() - 1);
            } else {
                consumer.setBudget(consumer.getBudget() - consumer
                        .getContract().getPrice() - consumer.getPenalty());
                consumer.getContractor().setBudget(consumer.getContractor()
                        .getBudget() + consumer.getContract().getPrice());
                consumer.getPenaltyContractor().setBudget(consumer
                        .getPenaltyContractor().getBudget() + consumer.getPenalty());
                consumer.setPenalty(0);
                consumer.setPenaltyContractor(null);
                consumer.getContract().setRemainedContractMonths(consumer
                        .getContract().getRemainedContractMonths() - 1);
            }
        }
    }

    /**
     * Verificare contractelor
     * Daca un consumator are un contract cu 0 luni ramase acesta este sters.
     * Daca un consumator are un contract incheiat cu un distribuitor care a
     * dat faliment acesta este sters.
     */
    public void checkContracts() {
        for (Consumer consumer : consumers) {
            if (consumer.getContract() != null) {
                if (consumer.getContract().getRemainedContractMonths() == 0) {
                    consumer.getContract().setRemainedContractMonths(consumer
                            .getContract().getRemainedContractMonths() - 1);
                    consumer.setContract(null);
                    consumer.setContractor(null);
                    consumer.setHasContract(false);
                }
                if (consumer.getContract() != null && consumer.getContractor().isBankrupt()) {
                    consumer.setPenalty(0);
                    consumer.setPenaltyContractor(null);
                    consumer.setContract(null);
                    consumer.setContractor(null);
                    consumer.setHasContract(false);
                }
            }
        }
    }

    /**
     * Contractele completate sunt sterse din lista de contracte
     * a distribuitorului.
     */
    public void completedContracts() {
        for (Distributor distributor : distributors) {
            List<Contract> contractsToRemove = new ArrayList<>();
            for (Contract contract : distributor.getContracts()) {
                if (contract.getRemainedContractMonths() == -1) {
                    contractsToRemove.add(contract);
                    distributor.setNumberOfConsumers(distributor.getNumberOfConsumers() - 1);
                }
            }
            distributor.getContracts().removeAll(contractsToRemove);
        }
    }

    /**
     * Plata costurilor de catre distribuitori
     */
    public void payExpenses() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                if (distributor.getBudget() < distributor.getInfrastructureCost()
                        + distributor.getProductionCost()
                        * distributor.getNumberOfConsumers()) {
                    distributor.getContracts().clear();
                    distributor.setBudget(distributor.getBudget() - distributor.getProductionCost()
                            * distributor.getNumberOfConsumers()
                            - distributor.getInfrastructureCost());
                    distributor.setBankrupt(true);
                } else {
                    distributor.setBudget(distributor.getBudget()
                            - distributor.getInfrastructureCost()
                            - distributor.getProductionCost() * distributor.getNumberOfConsumers());
                }
            }
        }
    }

    /**
     * Daca toti distribuitorii sunt in falimment, jocul se va opri.
     */
    public void checkBankrupcy() {
        boolean allDistributorsBankrupt = true;
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                allDistributorsBankrupt = false;
            }
        }
        if (allDistributorsBankrupt) {
            gameOver = true;
        }
    }

    /**
     * Daca un consumator a dat faliment, contractul acestuia este sters.
     */
    public void removeContracts() {
        for (Consumer consumer : consumers) {
            if (consumer.isBankrupt() && consumer.hasContract()) {
                consumer.getContractor().getContracts().remove(consumer.getContract());
                consumer.getContractor().setNumberOfConsumers(consumer
                        .getContractor().getNumberOfConsumers() - 1);
                consumer.setContractor(null);
                consumer.setContract(null);
                consumer.setHasContract(false);
            }
        }
    }
}
