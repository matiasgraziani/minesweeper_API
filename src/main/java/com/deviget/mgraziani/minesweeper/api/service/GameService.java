package com.deviget.mgraziani.minesweeper.api.service;

import com.deviget.mgraziani.minesweeper.api.domain.Cell;
import com.deviget.mgraziani.minesweeper.api.domain.Game;
import com.deviget.mgraziani.minesweeper.api.domain.MineStatus;
import com.deviget.mgraziani.minesweeper.api.domain.Player;
import com.deviget.mgraziani.minesweeper.api.repository.GameRepository;
import com.deviget.mgraziani.minesweeper.api.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    public static final Long DEFAULT_PLAYER = 1L;
    public static final Integer DEFAULT_MINES_NUM = 4;
    public static final Integer DEFAULT_HORIZONTAL_SIZE = 4;
    public static final Integer DEFAULT_VERTICAL_SIZE = 4;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public Game create() {
        Player player = playerRepository.findById(DEFAULT_PLAYER).get();
        Game game = new Game();
        game.setPlayer(player);
        game.setMines(DEFAULT_MINES_NUM);
        game.setHorizontalSize(DEFAULT_HORIZONTAL_SIZE);
        game.setVerticalSize(DEFAULT_VERTICAL_SIZE);

        this.createCells(game);

        //TODO set mines
        gameRepository.save(game);
        return game;
    }

    private void createCells(Game game){
        for (int h = 1; h <= game.getHorizontalSize(); h++) {
            for (int v = 1; v <= game.getVerticalSize(); v++) {
                Cell cell = new Cell();
                cell.setHorizontal(h);
                cell.setVertical(v);
                cell.setMine(Boolean.FALSE);
                cell.setStatus(MineStatus.None);
                game.getCells().add(cell);
            }
        }
    }

    public Optional<Game> get() {
        return gameRepository.findById(DEFAULT_PLAYER);
    }
}