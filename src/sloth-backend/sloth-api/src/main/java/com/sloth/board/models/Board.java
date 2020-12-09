package com.sloth.board.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {

    private UUID id;

    private String title;

    private List<Column> columns = new ArrayList<>();

    public Board(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }
}
