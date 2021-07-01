package com.example.demo.dto;

import com.example.demo.dataObject.Wallet;

import java.util.List;
import java.util.stream.Collectors;

public class WalletMapper {

    public static Wallet DtoToDo(WalletDTO walletDTO) {
        return new Wallet.WalletBuilder().setAccount(walletDTO.getAccountId()).setAmount(walletDTO.getAmount()).setId(walletDTO.getId()).setPurpose(walletDTO.getPurpose()).setTransactionDate(walletDTO.getTransactionDate()).setTransactionReference(walletDTO.getTransactionReference()).build();
    }

    public static WalletDTO dataObjectToDTO(Wallet wallet) {
        return new WalletDTO.WalletDTOBuilder().setAccountId(wallet.getAccount().getId()).setAmount(wallet.getAmount()).setId(wallet.getId()).setPurpose(wallet.getPurpose()).setTransactionDate(wallet.getTransactionDate()).setTransactionReference(wallet.getTransactionReference()).build();
    }

    public static List<WalletDTO> doToDTOList(List<Wallet> wallets) {
        return wallets.stream().map(WalletMapper::dataObjectToDTO).collect(Collectors.toList());
    }
}
