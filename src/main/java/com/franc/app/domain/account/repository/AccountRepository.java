package com.franc.app.domain.account.repository;

import com.franc.app.domain.account.repository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
