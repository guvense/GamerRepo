package com.ninja.game_crower.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import com.ninja.game_crower.Dto.MainResponse;
import com.ninja.game_crower.Dto.Request.GameRequest;
import com.ninja.game_crower.Dto.Response.GameResponse;
import com.ninja.game_crower.Entity.Game;
import com.ninja.game_crower.Entity.Link;
import com.ninja.game_crower.Entity.Store;
import com.ninja.game_crower.Enum.Category;
import com.ninja.game_crower.Repository.GameRepository;
import com.ninja.game_crower.Repository.LinkRepository;
import com.ninja.game_crower.Repository.StoreRepository;
import com.ninja.game_crower.Util;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {

    Logger logger= LoggerFactory.getLogger(getClass());

    private final GameRepository gameRepository;

    private final StoreRepository storeRepository;

    private final ModelMapper modelMapper;

    private final MongoTemplate mongoTemplate;

    private final LinkRepository linkRepository;


    public MainResponse<GameResponse> addGame(GameRequest gameRequest) {


        String storeName = gameRequest.getLinkRequest().getStoreName();

        Optional<Store> byStoreName = storeRepository.findByStoreName(storeName);

        if (!byStoreName.isPresent()) {

            Store s = new Store();
            s.setStoreName(storeName);

            Store storeResult = storeRepository.save(s);

            byStoreName = Optional.of(storeResult);
        }

        Link link = modelMapper.map(gameRequest.getLinkRequest(), Link.class);

        link.setStoreId(byStoreName.get().get_id());

        Link linkResult = linkRepository.save(link);


        Game game = modelMapper.map(gameRequest, Game.class);


        Optional<Game> byName = gameRepository.findByName(game.getName());

        ObjectId gameId;

        if (byName.isPresent()) {

            gameId = byName.get().get_id();
            UpdateResult updateResult = mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(game.getName())), new Update().push("linkIds", linkResult.get_id()), Game.class);
        } else {

            List<ObjectId> linksId = new ArrayList<>();
            linksId.add(linkResult.get_id());
            game.setLinkIds(linksId);
            Game save = gameRepository.save(game);

            gameId = save.get_id();

        }

       return getGameWithStore(gameId);

    }


    public MainResponse<List<GameResponse>> getGameByCategory(Category category) {

        MatchOperation category1 = Aggregation.match(Criteria.where("category").is(category));

        Aggregation aggregation = getGameWithAllComponents(category1);

        List<Game> games = mongoTemplate.aggregate(aggregation, "game", Game.class).getMappedResults();

        // List<GameResponse> gameResponses = modelMapper.map(games, Util.getTypeOfWithList(GameResponse.class));

        List<GameResponse> gameResponses = modelMapper.map(games,  new TypeToken<List<GameResponse>>() {}.getType());



        return new MainResponse<List<GameResponse>>(gameResponses, true);
    }


    public MainResponse<GameResponse> getGameWithStore(ObjectId id) {

        AggregationOperation id1 = Aggregation.match(Criteria.where("_id").is(id));

        Aggregation aggregation = getGameWithAllComponents(id1);

        Game game = mongoTemplate.aggregate(aggregation, "game", Game.class).getUniqueMappedResult();

        GameResponse map = modelMapper.map(game,GameResponse.class);

        return new MainResponse<GameResponse>(map, true);

    }

    private static Aggregation getGameWithAllComponents(AggregationOperation matchOperation){

       return Aggregation.newAggregation(matchOperation,
                Aggregation.unwind("$linkIds"),
                lookUpGameAndLink(),
                Aggregation.unwind("links"),
                lookUpGameLinkAndStore(),
                groupOperationGame());
    }

    private static GroupOperation groupOperationGame(){
        return Aggregation.group("_id","description","name","category").
                push(
                        new BasicDBObject("_id","$links._id")
                                .append("linkAddress","$links.linkAddress")
                                .append("storeId","$links.storeId")
                                .append("store","$links.store")
                                .append("platform","$links.platform")
                                .append("price","$links.price")
                ).as("links");

    }

    private static LookupOperation lookUpGameAndLink() {

        return LookupOperation.newLookup()
                .from("link")
                .localField("linkIds")
                .foreignField("_id")
                .as("links");

    }

    private static LookupOperation lookUpGameLinkAndStore() {

       return LookupOperation.newLookup()
                .from("store")
                .localField("links.storeId")
                .foreignField("_id")
                .as("links.store");

    }



}
