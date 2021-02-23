package com.example.invmgnt.invmgnt.repository;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import com.example.invmgnt.invmgnt.domain.StockBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalance, Long> {

    StockBalance findByItem(ItemMaster item);

}
