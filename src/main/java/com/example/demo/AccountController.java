package com.example.demo;

import com.example.demo.dataObject.Account;
import com.example.demo.dataObject.Wallet;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AccountMapper;
import com.example.demo.dto.WalletDTO;
import com.example.demo.dto.WalletMapper;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.AccountService;
import com.example.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private WalletService walletService;



    @GetMapping("/players")
    public ResponseEntity getPlayers(){
        List<Account> accounts = accountService.getList();
        return new ResponseEntity<List<AccountDTO>>(AccountMapper.DataObjectToDTOList(accounts), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity getPlayer(@PathVariable("id") Long id){
        Account account;
        try{
            account =accountService.accountByPK(id);
        } catch (WalletException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null,e);
            return new ResponseEntity<String>(e.getMsg(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccountDTO>(AccountMapper.DoToDataTransferObject(account),HttpStatus.OK);
    }

    @PostMapping("/players")
    public ResponseEntity createPlayer(@RequestBody AccountDTO accountDTO){
        Account account;
        try{
            account = accountService.save(AccountMapper.DtoToDataObject(accountDTO));
        } catch (WalletException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null,e);
            return new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<AccountDTO>(AccountMapper.DoToDataTransferObject(account), HttpStatus.CREATED);
    }

    @PutMapping("/players/{id}")
    public ResponseEntity updatePlayer(@PathVariable("id") Long accountId, @RequestBody AccountDTO accountDTO){
        Account account;
        try{
            account = accountService.update(AccountMapper.DtoToDataObject(accountDTO), accountId);
        } catch (WalletException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<String>(e.getMsg(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<AccountDTO>(AccountMapper.DoToDataTransferObject(account),HttpStatus.OK);
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity createTransaction(@PathVariable("id")Long accountId, @RequestBody WalletDTO walletDTO){
        Wallet saved;
        try {
            walletDTO.setAccountId(accountId);
            saved = walletService.createTransaction(WalletMapper.DtoToDo(walletDTO));
        } catch (WalletException | InsufficientBalanceException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<WalletDTO> (WalletMapper.dataObjectToDTO(saved),HttpStatus.CREATED);
    }

    @GetMapping(("/transactions"))
    public ResponseEntity getTransactions(){
        List<Wallet> wallets = walletService.getList();
        return new ResponseEntity<List<WalletDTO>>(WalletMapper.doToDTOList(wallets), HttpStatus.OK);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity getTransaction(@PathVariable("id") Long id){
        List<Wallet> wallets  = new ArrayList<Wallet>();
        try{
            wallets =walletService.transactionsByAccountId(id);
        } catch (WalletException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null,e);
            return new ResponseEntity<String>(e.getMsg(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<WalletDTO>>(WalletMapper.doToDTOList(wallets), HttpStatus.OK);
    }



}
