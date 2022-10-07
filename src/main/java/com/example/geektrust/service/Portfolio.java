package com.example.geektrust.service;

import com.example.geektrust.common.Utils;
import com.example.geektrust.exception.InvalidCommand;
import com.example.geektrust.helper.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.geektrust.common.Constants.COMMAND_NOT_FOUND;
import static com.example.geektrust.common.Constants.SPACE_REGEX;

public class Portfolio extends BasePortfolio {
    private static final Logger logger = Logger.getLogger(Portfolio.class.getName());

    public Portfolio() {
        super();
    }

    public void executeTransaction(String input) {
        logger.info("[Portfolio] [executeTransaction] [input] = " + input);
        LinkedList<String> inputs = new LinkedList<>(Arrays.asList(input.split(SPACE_REGEX)));
        String inputType = inputs.get(0);
        List<Integer> fundAllocation;

        try {
            switch (inputType) {
                case ALLOCATE:
                    fundAllocation = InitialAllocateOperation.getInstance().processRequest(transactions, inputs);
                    initialInvestment = new InitialInvestment(fundAllocation);
                    break;

                case SIP:
                    fundAllocation = SystematicInvestmentOperation.getInstance().processRequest(transactions, inputs);
                    systematicInvestment = new SystematicInvestment(fundAllocation);
                    break;

                case CHANGE:
                    if (Utils.getMonth(inputs.getLast()) > 0) {
                        SystematicInvestmentOperation.getInstance().processAllocation(transactions, inputs.getLast(), systematicInvestment.getFundsValue());
                    }
                    fundAllocation = ChangeOperation.getInstance().processRequest(transactions, inputs);
                    ChangeOperation.getInstance().processAllocation(transactions, inputs.getLast(), fundAllocation);
                    break;

                case REBALANCE:
                    fundAllocation = RebalanceOperation.getInstance(initialInvestment).processRequest(transactions, inputs);
                    RebalanceOperation.getInstance(initialInvestment).processAllocation(transactions, null, fundAllocation);
                    break;

                case BALANCE:
                    BalanceOperation.getInstance().processRequest(transactions, inputs);
                    break;

                default:
                    throw new InvalidCommand(COMMAND_NOT_FOUND, Portfolio.class.getSimpleName(), "executeTransaction");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
