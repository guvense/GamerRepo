package com.ninja.game_crower.Dto.Request;

import com.ninja.game_crower.Enum.Category;
import com.ninja.game_crower.Enum.Platform;
import io.swagger.annotations.SwaggerDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class GameRequest {

    private String description;

    private String name;

    @DateTimeFormat(pattern = "YYYY-mm-ddTHH:MM:ssZ")
    private Date releaseDate;

    private Category category;

    private LinkRequest linkRequest;


}
