package com.example.geektrust.service;

import com.example.geektrust.Main;
import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidCommand;
import com.example.geektrust.helper.*;
import com.example.geektrust.model.Portfolio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.geektrust.common.Constants.*;

public class PortfolioService {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static PortfolioService portfolioService;

    private PortfolioService() {
    }

    public static PortfolioService getInstance() {
        if (portfolioService == null) {
            synchronized (PortfolioService.class) {
                if (portfolioService == null) {
                    portfolioService = new PortfolioService();
                }
            }
        }
        return portfolioService;
    }

    public void submitTask(Portfolio portfolio, String filePath) throws IOException {
        Files.readAllLines(Paths.get(filePath)).forEach(input -> executeTransactions(input, portfolio));
    }

    private void executeTransactions(String input, Portfolio portfolio) {
        LinkedList<String> inputs = new LinkedList<>(Arrays.asList(input.split(SPACE_REGEX)));
        String inputType = inputs.get(0);
        List<Integer> fundAllocation;

        try {
            switch (inputType) {
                case ALLOCATE:
                    fundAllocation = InitialAllocateOperation.getInstance().processRequest(portfolio.getTransactions(), inputs);
                    portfolio.setInitialInvestment(fundAllocation);
                    break;

                case SIP:
                    fundAllocation = SystematicInvestmentOperation.getInstance().processRequest(portfolio.getTransactions(), inputs);
                    portfolio.setSystematicInvestment(fundAllocation);
                    break;

                case CHANGE:
                    if (Utils.getMonth(inputs.getLast()) > 0) {
                        SystematicInvestmentOperation.getInstance().processAllocation(portfolio.getTransactions(),
                                inputs.getLast(), portfolio.getSystematicInvestment().getFundsValue());
                    }
                    fundAllocation = ChangeOperation.getInstance().processRequest(portfolio.getTransactions(), inputs);
                    ChangeOperation.getInstance().processAllocation(portfolio.getTransactions(), inputs.getLast(), fundAllocation);
                    break;

                case REBALANCE:
                    fundAllocation = RebalanceOperation.getInstance(portfolio.getInitialInvestment()).processRequest(portfolio.getTransactions(), inputs);
                    RebalanceOperation.getInstance(portfolio.getInitialInvestment()).processAllocation(portfolio.getTransactions(), null, fundAllocation);
                    break;

                case BALANCE:
                    BalanceOperation.getInstance().processRequest(portfolio.getTransactions(), inputs);
                    break;

                default:
                    throw new InvalidCommand(COMMAND_NOT_FOUND, Portfolio.class.getSimpleName(), "executeTransaction");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
