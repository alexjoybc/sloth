package com.sloth.board;

import com.sloth.board.models.Board;
import com.sloth.board.models.Column;
import com.sloth.board.models.Issue;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BoardServiceImpl implements BoardService {

    private Board starwarsboard;
    private List<Board> allBoards = new ArrayList<>();

    public BoardServiceImpl() {

        starwarsboard = new Board(UUID.randomUUID());
        starwarsboard.setTitle("Star Wars");

        Column rebels = new Column();
        rebels.setTitle("Rebels");
        Issue luke = new Issue();
        luke.setTitle("Luke Skywalker");
        luke.setDescription("Leia's brohter");
        rebels.addIssue(luke);
        Issue leia = new Issue();
        leia.setTitle("Leia Organa");
        leia.setDescription("Lukes' sister");
        rebels.addIssue(leia);
        starwarsboard.addColumn(rebels);
        Column darkside = new Column();
        darkside.setTitle("Dark Side");
        Issue darthVader = new Issue();
        darthVader.setTitle("Darth Vader");
        darthVader.setDescription("Luke and Leia father");
        darkside.addIssue(darthVader);
        starwarsboard.addColumn(darkside);

        allBoards.add(starwarsboard);

    }

    @Override
    public List<Board> getAllBoards() {
        return allBoards;
    }

    @Override
    public Board findById(UUID id) {
        return starwarsboard;
    }

}
