package com.ninja.game_crower.Repository;

import com.ninja.game_crower.Entity.Link;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LinkRepository extends MongoRepository<Link, ObjectId> {
}
