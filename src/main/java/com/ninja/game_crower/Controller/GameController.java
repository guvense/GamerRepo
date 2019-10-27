package com.ninja.game_crower.Controller;

import com.ninja.game_crower.Dto.MainResponse;
import com.ninja.game_crower.Dto.Request.GameRequest;
import com.ninja.game_crower.Dto.Response.GameResponse;
import com.ninja.game_crower.Enum.Category;
import com.ninja.game_crower.Service.GameService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    private final ModelMapper modelMapper;

    private final Deneme deneme;

    @PostMapping
    public MainResponse<GameResponse> addGame(GameRequest game) {

       deneme.getUserName();
        return gameService.addGame(game);
    }

    @GetMapping("/{gameId}")
    public MainResponse<GameResponse> getGame(@PathVariable(value="gameId") ObjectId id){

        return gameService.getGameWithStore(id);
    }

    @GetMapping("/category/{gameCategory}")
    public MainResponse<List<GameResponse>> getGameByCategory(@PathVariable(value = "gameCategory") Category category){
        String s ="";
        MainResponse<List<GameResponse>> gameByCategory = gameService.getGameByCategory(category);
        return gameByCategory;
    }



}
