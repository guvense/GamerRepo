package com.ninja.game_crower.Entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.ninja.game_crower.Enum.Category;
import com.ninja.game_crower.Enum.Platform;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
public class Game {

    @Id
    private ObjectId _id;

    private String description;

    private String name;

    @DateTimeFormat(pattern = "YYYY-mm-ddTHH:MM:ssZ")
    private Date releaseDate;

    private Category category;

    private List<ObjectId> linkIds;

    private List<Link> links;



}
