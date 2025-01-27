package data;

import input.Constants;
import strategies.GreenStrategy;
import strategies.PriceStrategy;
import strategies.QuantityStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Data {
    private int numberOfTurns;
    private List<Consumer> consumers = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();
    private List<Producer> producers = new ArrayList<>();
    private List<MonthlyUpdate> monthlyUpdates = new ArrayList<>();
    private int monthNr = 0;
    private GreenStrategy greenStrategy = new GreenStrategy();
    private PriceStrategy priceStrategy = new PriceStrategy();
    private QuantityStrategy quantityStrategy = new QuantityStrategy();
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

    public List<Producer> getProducers() {
        return producers;
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
        for (Integer distributorId : update.getDistributorIds()) {
            for (Distributor databaseDistributor : distributors) {
                if (databaseDistributor.getId() == distributorId) {
                    databaseDistributor.setInfrastructureCost(update
                            .getInfrastructureCosts().get(update
                                    .getDistributorIds().indexOf(distributorId)));
                }
            }
        }
    }

    /**
     *  Executare update pentru producatori
     * @param update
     */
    public void updateProducers(final MonthlyUpdate update) {
        for (Integer producerId : update.getProducersIds()) {
            for (Producer databaseProducer : producers) {
                if (databaseProducer.getId() == producerId) {
                    databaseProducer.setEnergyPerDistributor(update
                            .getEnergyPerDistributor().get(update
                                    .getProducersIds().indexOf(producerId)));
                    for (Distributor distributor : databaseProducer.getDistributors()) {
                        distributor.getProducers().clear();
                        distributor.setNotified(true);
                    }
                }
            }
        }
    }

    /**
     * Rularea unui tur al jocului
     */
    public void simulateTurn() {
        if (monthNr == 0) {
            chooseProducers();
        }
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
        if (monthNr != 0) {
            updateProducers(monthlyUpdates.get(monthNr - 1));
            chooseProducers();
            monthlyReport();
            sort();
        }
        monthNr++;
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
                    for (Producer producer : distributor.getProducers()) {
                        producer.getDistributors().remove(distributor);
                    }
                    distributor.getProducers().clear();
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

    /**
     *  Alegerea producatorilor de catre distribuitori si
     *  calcularea pretului de productie
     */
    public void chooseProducers() {
        for (Distributor distributor : distributors) {
            if (distributor.isNotified() && !distributor.isBankrupt()) {
                for (Producer producer : producers) {
                    if (producer.getDistributors().contains(distributor)) {
                        producer.getDistributors().remove(distributor);
                    }
                }
                switch (distributor.getStrategy()) {
                    case ("GREEN"):
                        distributor.getProducers().addAll(greenStrategy
                                .strategy(this, distributor));
                        break;
                     case ("PRICE"):
                        distributor.getProducers().addAll(priceStrategy
                                .strategy(this, distributor));
                        break;
                     case ("QUANTITY"):
                        distributor.getProducers().addAll(quantityStrategy
                                .strategy(this, distributor));
                        break;
                    default:
                        break;
                }
                double productionCost = 0;
                for (Producer producer : distributor.getProducers()) {
                    productionCost += producer.getEnergyPerDistributor() * producer.getPriceKW();
                }
                int cost = (int) Math.round(Math.floor(productionCost / Constants.TEN));
                distributor.setProductionCost(cost);
                distributor.setNotified(false);
            }
        }
    }

    /**
     *  Adaugarea unui raport lunar pentru producatori
     */
    public void monthlyReport() {
        for (Producer producer : producers) {
            List<Integer> distributorIds = new ArrayList<>();
            for (Distributor distributor : producer.getDistributors()) {
                distributorIds.add(distributor.getId());
            }
            Month month = new Month(monthNr, distributorIds);
            producer.getMonthlyStats().add(month);
        }
    }

    /**
     *  sortarea id-urilor distribuitorilor unui producator
     */
    public void sort() {
        for (Producer producer : producers) {
            for (Month month : producer.getMonthlyStats()) {
                Collections.sort(month.getDistributorIds());
            }
        }
    }
}
