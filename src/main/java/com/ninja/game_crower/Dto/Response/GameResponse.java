package com.ninja.game_crower.Dto.Response;

import com.ninja.game_crower.Enum.Category;
import com.ninja.game_crower.Enum.Platform;
import lombok.Data;
import org.bson.types.Decimal128;

import java.util.List;

@Data
public class GameResponse {

    private String description;

    private String name;

    private String _id;

    private Category category;

    private List<LinkResponse> links;
}
