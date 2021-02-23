package com.example.invmgnt.invmgnt.service;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import com.example.invmgnt.invmgnt.domain.PurchaseReceived;
import com.example.invmgnt.invmgnt.domain.StockBalance;
import com.example.invmgnt.invmgnt.repository.ItemMasterRepository;
import com.example.invmgnt.invmgnt.repository.PurchaseReceivedRepository;
import com.example.invmgnt.invmgnt.repository.StockBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PurchaseReceivedService {

    private final PurchaseReceivedRepository repository;
    @Autowired
    private StockBalanceRepository stockRepository;


    @Autowired
    public PurchaseReceivedService(PurchaseReceivedRepository repository){
        this.repository = repository;
    }


    public List<PurchaseReceived> getAll() {
        List<PurchaseReceived> result = repository.findAll();

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
    public Page< PurchaseReceived > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return repository.findAll(pageable);

    }


    public PurchaseReceived findById(Long id) throws Exception {
        Optional<PurchaseReceived> entity = repository.findById(id);

        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public PurchaseReceived getById(Long id) throws Exception {
        return this.findById(id);
    }



    public void createOrUpdateStockBalance(PurchaseReceived purchaseReceived){

        ItemMaster item = purchaseReceived.getItem();
        StockBalance sb = stockRepository.findByItem(item);
        if(sb==null){
            sb = new StockBalance();
            sb.setItem(item);
            sb.setQty(purchaseReceived.getQty());
            sb.setAvgWeightPrice(purchaseReceived.getUnitPrice());
            stockRepository.save(sb);
        }else {
            Double existQty = sb.getQty();
            Double existAmt = sb.getAvgWeightPrice();

            Double tnxQty = purchaseReceived.getQty();
            Double tnxAmt = purchaseReceived.getUnitPrice();

            Double existAvgAmt = existQty * existAmt;
            Double tnxAvgAmt = tnxQty * tnxAmt;

            Double toAmt = existAvgAmt + tnxAvgAmt;

            Double tobQty = existQty + tnxQty;

            Double avgAmt = toAmt / tobQty;

            sb.setQty(tobQty);
            sb.setAvgWeightPrice(avgAmt);
            stockRepository.save(sb);
        }
    }


    public PurchaseReceived setAttributeForCreateUpdate(PurchaseReceived purchaseReceived){
        ItemMaster item = purchaseReceived.getItem();

        Double qty = purchaseReceived.getQty();
        Double unitPrice = (item == null) ? 0.00 : item.getPurchasePrice();
        Double amount = qty * unitPrice;
        purchaseReceived.setUnitPrice(unitPrice);
        purchaseReceived.setAmount(amount);
        return purchaseReceived;
    }

    public PurchaseReceived createOrUpdate(PurchaseReceived entity) {

        entity = this.setAttributeForCreateUpdate(entity);
        this.createOrUpdateStockBalance(entity);

        if(entity.getId() == null) {
            entity = repository.save(entity);

        } else {
            Optional<PurchaseReceived> entityOptional = repository.findById(entity.getId());
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
        Optional<PurchaseReceived> entity = repository.findById(id);

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
