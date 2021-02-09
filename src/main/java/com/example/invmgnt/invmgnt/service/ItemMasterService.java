package com.example.invmgnt.invmgnt.service;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import com.example.invmgnt.invmgnt.repository.ItemMasterRepository;
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
public class ItemMasterService {

    private final ItemMasterRepository repository;


    @Autowired
    public ItemMasterService(ItemMasterRepository repository){
        this.repository = repository;
    }


    public List<ItemMaster> getAll() {
        List<ItemMaster> result = repository.findAll();

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
public Page< ItemMaster > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
    return repository.findAll(pageable);

}


    public ItemMaster findById(Long id) throws Exception {
        Optional<ItemMaster> entity = repository.findById(id);

        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public ItemMaster getById(Long id) throws Exception {
        return this.findById(id);
    }


    public void setAttributeForCreateUpdate(){
    }

    public ItemMaster createOrUpdate(ItemMaster entity) {

        this.setAttributeForCreateUpdate();

        if(entity.getId() == null) {
            entity = repository.save(entity);

        } else {
            Optional<ItemMaster> entityOptional = repository.findById(entity.getId());
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
        Optional<ItemMaster> entity = repository.findById(id);

        if(entity.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("No record exist for given id");
        }
    }
}
