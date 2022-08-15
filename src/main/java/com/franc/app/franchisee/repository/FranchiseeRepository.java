package com.franc.app.franchisee.repository;

import com.franc.app.franchisee.repository.entity.Franchisee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseeRepository extends JpaRepository<Franchisee, Long> {
}
