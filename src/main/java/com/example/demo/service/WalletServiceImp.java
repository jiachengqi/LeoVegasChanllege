package com.example.demo.service;

import com.example.demo.dataObject.Wallet;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.repo.WalletRepo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class WalletServiceImp implements WalletService {

    @Autowired
    private WalletRepo walletRepo;
    @Autowired
    private AccountService accountService;

    @Override
    public Wallet transactionByRef(Long txnRef) throws WalletException {
        return walletRepo.getTransactionByRef(txnRef).orElseThrow(() -> new WalletException(String.format(
                "transaction with reference number '%d' doesn't exist", txnRef)));
    }

    @Override
    @Transactional
    public Wallet createTransaction(Wallet wallet) throws WalletException, InsufficientBalanceException {
        if (walletRepo.getTransactionByRef(wallet.getTransactionReference()).isPresent()) {
            throw new WalletException("transaction reference number has been used");
        }

        BigDecimal balance = walletRepo.getBalance(wallet.getAccount().getId());
        if (wallet.getPurpose().equals("withdraw")){
            if(wallet.getAmount().compareTo(balance) <= 0){
                wallet.setAmount(wallet.getAmount().negate());
            } else {
                throw new InsufficientBalanceException(String.format("player's balance is %.2f and cannot perform a " +
                        "transaction of %.2f", balance.doubleValue(), wallet.getAmount().doubleValue()));
            }

        }

        if (balance.add(wallet.getAmount()).compareTo(BigDecimal.ZERO) == 1 | balance.add(wallet.getAmount()).compareTo(BigDecimal.ZERO) == 0) {
//            result greater or equal than zero

            return walletRepo.save(wallet);
        }
        throw new InsufficientBalanceException(String.format("player's balance is %.2f and cannot perform a " +
                "transaction of %.2f", balance.doubleValue(), wallet.getAmount().doubleValue()));


    }


    @Override
    public Wallet update(Wallet wallet, Long id) throws WalletException {
        throw new UnsupportedOperationException("not support");
    }

    @Override
    public List<Wallet> getList() {
        return Lists.newArrayList(walletRepo.findAll());
    }


    @Override
    public List<Wallet> transactionsByAccountId(Long accountId) {
        return walletRepo.getTransactionsForPlayer(accountId);
    }

    @Override
    public BigDecimal balanceByAccountID(Long accountId) {
        return walletRepo.getBalance(accountId);
    }

    @Override
    public List<Wallet> transactions() {
        return Lists.newArrayList(walletRepo.findAll());
    }

    @Override
    public Wallet save(Wallet wallet) throws WalletException {
        throw new UnsupportedOperationException("not support");
    }


}
