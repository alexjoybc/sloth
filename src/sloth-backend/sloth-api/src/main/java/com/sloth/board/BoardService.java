package com.sloth.board;

import com.sloth.board.models.Board;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    List<Board> getAllBoards();

    Board findById(UUID id);
}
