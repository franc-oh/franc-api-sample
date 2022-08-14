package com.franc.app.account.repository;

import com.franc.app.account.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
