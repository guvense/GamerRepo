package com.ninja.game_crower.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class Store {

    @Id
    private ObjectId _id;

    private String storeName;
}
