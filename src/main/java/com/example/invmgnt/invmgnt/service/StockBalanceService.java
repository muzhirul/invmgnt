package com.example.invmgnt.invmgnt.service;


import com.example.invmgnt.invmgnt.domain.StockBalance;
import com.example.invmgnt.invmgnt.repository.StockBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockBalanceService {

    private final StockBalanceRepository repository;


    @Autowired
    public StockBalanceService(StockBalanceRepository repository){
        this.repository = repository;
    }


    public List<StockBalance> getAll() {
        List<StockBalance> result = repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    //    public List<ItemMaster> getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {
//
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
//
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
//        return (List<ItemMaster>) repository.findAll(pageable);
//
//    }
    public Page< StockBalance > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return repository.findAll(pageable);

    }


    public StockBalance findById(Long id) throws Exception {
        Optional<StockBalance> entity = repository.findById(id);

        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public StockBalance getById(Long id) throws Exception {
        return this.findById(id);
    }


    public void setAttributeForCreateUpdate(){
    }

    public StockBalance createOrUpdate(StockBalance entity) {

        this.setAttributeForCreateUpdate();

        if(entity.getId() == null) {
            entity = repository.save(entity);

        } else {
            Optional<StockBalance> entityOptional = repository.findById(entity.getId());
            if(entityOptional.isPresent()) {
//                SystemMenu editEntity = entityOptional.get();
//                editEntity.setDisplayName(entity.getDisplayName());
//                editEntity.setPhoneNumber(entity.getPhoneNumber());
//                editEntity = repository.save(editEntity);
//                return editEntity;
                entity = repository.save(entity);
            }
        }
        return entity;

    }


    public void deleteById(Long id) throws Exception {
        Optional<StockBalance> entity = repository.findById(id);

        if(entity.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("No record exist for given id");
        }
    }


    // Others helper methods ///////////////////////////////////////////////////////////////////////////////////////////
//    public Map<Long, String> getMapAllParentMenus() {
//
//        List<PurchaseReceived> result = repository.findAll();
//
//        Map<Long, String> map = new HashMap<>();
//        for (PurchaseReceived menu : result) {
//            map.put(menu.getId(), menu.getId().toString() + " - " + menu.getItemName());
//        }
//        return map;
//    }
}
