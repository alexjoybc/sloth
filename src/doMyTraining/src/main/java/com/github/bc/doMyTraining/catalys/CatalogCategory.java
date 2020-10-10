package com.github.bc.doMyTraining.catalys;

public enum CatalogCategory {

    TECHNICAL(38);

    private int id;

    CatalogCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
