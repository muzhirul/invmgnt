package com.example.invmgnt.invmgnt.repository;

import com.example.invmgnt.invmgnt.domain.PurchaseReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseReceivedRepository extends JpaRepository<PurchaseReceived, Long> {
}
