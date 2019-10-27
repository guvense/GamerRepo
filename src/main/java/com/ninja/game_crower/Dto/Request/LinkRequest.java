package com.ninja.game_crower.Dto.Request;

import com.ninja.game_crower.Enum.Platform;
import lombok.Data;

@Data
public class LinkRequest {

    private String linkAddress;

    private String storeName;

    private Platform platform;

    private Double price;
}
