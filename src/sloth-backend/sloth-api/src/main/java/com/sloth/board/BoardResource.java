package com.sloth.board;

import com.sloth.board.models.Board;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import java.util.List;
import java.util.UUID;

@GraphQLApi
public class BoardResource {

    private final BoardService boardService;

    public BoardResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @Query("allBoards")
    @Description("Get all boards")
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @Query("findBoardById")
    @Description("Get board by id")
    public Board findBoardById(@Name("boardId") UUID id) {
        return boardService.findById(id);
    }

}
