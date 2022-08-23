package com.franc.app.domain.franchisee.repository;

import com.franc.app.domain.franchisee.repository.entity.Franchisee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseeRepository extends JpaRepository<Franchisee, Long> {
}
