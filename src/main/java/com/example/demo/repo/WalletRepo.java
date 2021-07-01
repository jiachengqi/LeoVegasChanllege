package com.example.demo.repo;

import com.example.demo.dataObject.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletRepo extends CrudRepository<Wallet, Long> {

    @Query(nativeQuery = true, value = "select * from wallet where transaction_reference = ?")
    Optional<Wallet> getTransactionByRef(Long txnRef);

    @Query(nativeQuery = true, value = "select ifnull(sum(amount),0.00) from wallet where account_id = ?")
    BigDecimal getBalance(Long accountId);

    @Query(nativeQuery = true, value = "select * from wallet where account_id = ?")
    List<Wallet> getTransactionsForPlayer(Long accountId);
}
