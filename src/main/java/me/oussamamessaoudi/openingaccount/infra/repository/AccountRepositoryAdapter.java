package me.oussamamessaoudi.openingaccount.infra.repository;

import me.oussamamessaoudi.openingaccount.application.domain.entity.Account;
import me.oussamamessaoudi.openingaccount.application.domain.entity.Transaction;
import me.oussamamessaoudi.openingaccount.application.domain.repository.AccountRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface AccountRepositoryAdapter extends AccountRepository, CrudRepository<Account, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.balance = a.balance + CAST(:amount AS bigdecimal) WHERE a.id = :accountId")
    int updateBalance(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);

    default Account updateBalance(Transaction transaction) {
        Long accountId = transaction.getAccount().getId();
        BigDecimal amount = transaction.getAmount();
        this.updateBalance(accountId, amount);
        return this.findById(accountId).orElse(null);
    }

}
