package com.example.assigmentapi.apibackend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired private StoreRepository storeRepo;

    @GetMapping
    public List<Store> getAllStores() {
        return storeRepo.findAll();
    }

    @PostMapping
    public Store addStore(@RequestBody Store store) {
        return storeRepo.save(store);
    }
}
