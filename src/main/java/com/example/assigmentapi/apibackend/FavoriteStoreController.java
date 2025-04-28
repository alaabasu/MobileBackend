package com.example.assigmentapi.apibackend;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteStoreController {

    @Autowired
    private FavoriteStoreRepository favRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping("/{userId}/{storeId}")
    public ResponseEntity<?> addToFavorites(@PathVariable Integer userId, @PathVariable Long storeId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Store> storeOptional = storeRepository.findById(storeId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (storeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Store not found");
        }

        if (favRepo.findByUserIdAndStoreId(userId, storeId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Store is already in favorites");
        }

        FavoriteStore favoriteStore = new FavoriteStore();
        favoriteStore.setUser(userOptional.get());
        favoriteStore.setStore(storeOptional.get());

        FavoriteStore savedFavorite = favRepo.save(favoriteStore);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFavorite);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getFavorites(@PathVariable Integer userId) {
        List<FavoriteStore> favoriteStores = favRepo.findByUserId(userId);
        return ResponseEntity.ok(favoriteStores);
    }
}
