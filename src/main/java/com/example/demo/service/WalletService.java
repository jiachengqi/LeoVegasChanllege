package com.example.demo.service;

import com.example.demo.dataObject.Wallet;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.exceptions.WalletException;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService extends PlayerService<Wallet> {
    List<Wallet> transactionsByAccountId(Long accountId) throws WalletException;

    Wallet transactionByRef(Long txnRef) throws WalletException;

    BigDecimal balanceByAccountID(Long accountId) throws WalletException;

    List<Wallet> transactions();

    Wallet createTransaction(Wallet wallet) throws WalletException, InsufficientBalanceException;
}
