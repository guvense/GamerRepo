package com.ninja.game_crower.Dto.Response;

import com.ninja.game_crower.Enum.Platform;
import lombok.Data;

@Data
public class LinkResponse {

    private StoreResponse store;

    private String  _id;

    private String linkAddress;

    private Platform platform;

    private Double price;
}
