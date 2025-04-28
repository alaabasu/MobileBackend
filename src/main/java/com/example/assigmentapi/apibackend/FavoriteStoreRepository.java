package com.example.assigmentapi.apibackend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteStoreRepository extends JpaRepository<FavoriteStore, Long> {
    Optional<FavoriteStore> findByUserIdAndStoreId(Integer userId, Long storeId);
    List<FavoriteStore> findByUserId(Integer userId);
    
}