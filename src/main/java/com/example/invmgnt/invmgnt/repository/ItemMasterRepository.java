package com.example.invmgnt.invmgnt.repository;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMasterRepository extends JpaRepository<ItemMaster, Long> {

}
