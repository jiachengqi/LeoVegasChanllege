package com.example.demo.dataObject;


import com.sun.istack.NotNull;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    private Account account;

    @NonNull
    private BigDecimal amount;

    private String purpose;

    @Temporal(TemporalType.DATE)
    private Date transactionDate;

    @NotNull
    @Column(unique = true)
    private Long transactionReference;

    @Version
    private int version;
//    to make the account updated all the time, for concurrency


    public Wallet() {
    }

    public Wallet(Account account, @NonNull BigDecimal amount, String purpose, Date transactionDate,
                  Long transactionReference) {
        this.account = account;
        this.amount = amount;
        this.purpose = purpose;
        this.transactionDate = transactionDate;
        this.transactionReference = transactionReference;
    }

    public Wallet(WalletBuilder builder) {
        id = builder.id;
        account = new Account(builder.accountId);
        amount = builder.amount;
        purpose = builder.purpose;
        transactionDate = builder.transactionDate;
        transactionReference = builder.transactionReference;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(Long transactionReference) {
        this.transactionReference = transactionReference;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Wallet other = (Wallet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

//    override hashcode and equals makes the transaction safe
//    for example different currency

    public static class WalletBuilder {

        private Long id;
        private Long accountId;
        private BigDecimal amount;
        private String purpose;
        private Date transactionDate;
        private Long transactionReference;

        public WalletBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public WalletBuilder setAccount(Long accountId) {
            this.accountId = accountId;
            return this;
        }

        public WalletBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public WalletBuilder setPurpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public WalletBuilder setTransactionDate(Date transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public WalletBuilder setTransactionReference(Long transactionReference) {
            this.transactionReference = transactionReference;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }

    }
}
