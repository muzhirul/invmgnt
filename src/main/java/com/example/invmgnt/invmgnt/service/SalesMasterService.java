package com.example.invmgnt.invmgnt.service;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import com.example.invmgnt.invmgnt.domain.PurchaseReceived;
import com.example.invmgnt.invmgnt.domain.SalesMaster;
import com.example.invmgnt.invmgnt.domain.StockBalance;
import com.example.invmgnt.invmgnt.repository.PurchaseReceivedRepository;
import com.example.invmgnt.invmgnt.repository.SalesMasterRepository;
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
public class SalesMasterService {

    private final SalesMasterRepository repository;
    @Autowired
    public SalesMasterService(SalesMasterRepository repository){
        this.repository = repository;
    }



    @Autowired
    private StockBalanceRepository stockRepository;


    public List<SalesMaster> getAll() {
        List<SalesMaster> result = repository.findAll();

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
    public Page< SalesMaster > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return repository.findAll(pageable);

    }


    public SalesMaster findById(Long id) throws Exception {
        Optional<SalesMaster> entity = repository.findById(id);

        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public SalesMaster getById(Long id) throws Exception {
        return this.findById(id);
    }



    public void createOrUpdateStockBalance(SalesMaster salesMaster){

        ItemMaster item = salesMaster.getItem();
        StockBalance sb = stockRepository.findByItem(item);

            Double existQty = sb.getQty();
            Double tnxQty = salesMaster.getQty();
            Double tobQty = existQty - tnxQty;
            sb.setQty(tobQty);
            stockRepository.save(sb);

    }


    public SalesMaster setAttributeForCreateUpdate(SalesMaster salesMaster){
        ItemMaster item = salesMaster.getItem();

        Double qty = salesMaster.getQty();
        Double unitPrice = (item == null) ? 0.00 : item.getSellingPrice();
        Double amount = qty * unitPrice;
        salesMaster.setUnitPrice(unitPrice);
        salesMaster.setAmount(amount);
        return salesMaster;
    }

    public SalesMaster createOrUpdate(SalesMaster entity) {

        entity = this.setAttributeForCreateUpdate(entity);
        this.createOrUpdateStockBalance(entity);

        if(entity.getId() == null) {
            entity = repository.save(entity);

        } else {
            Optional<SalesMaster> entityOptional = repository.findById(entity.getId());
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
        Optional<SalesMaster> entity = repository.findById(id);

        if(entity.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("No record exist for given id");
        }
    }
}
