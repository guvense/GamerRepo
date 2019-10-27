package com.ninja.game_crower.Entity;

import com.ninja.game_crower.Enum.Platform;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class Link {

    @Id
    private ObjectId _id;

    private String linkAddress;

    private ObjectId storeId;

    private Store store;

    private Platform platform;

    private Double price;

}
