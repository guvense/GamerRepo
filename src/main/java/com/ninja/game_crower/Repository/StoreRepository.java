package com.ninja.game_crower.Repository;

import com.ninja.game_crower.Entity.Store;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoreRepository extends MongoRepository<Store, ObjectId> {

    Optional<Store> findByStoreName(String s);

}
