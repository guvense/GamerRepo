package com.ninja.game_crower.Repository;

import com.ninja.game_crower.Entity.Game;
import com.ninja.game_crower.Enum.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, ObjectId> {
    Optional<Game> findById(ObjectId id);
    List<Game> findAllByCategory(Category category);
    Optional<Game> findByName(String name);

}
