package com.github.bc.doMyTraining.catalys;

import com.github.bc.doMyTraining.Keys;
import com.github.bc.doMyTraining.catalys.CatalogCategory;

import java.text.MessageFormat;

public class Catalys {

    public static String CATALYS_CATALOG = "course/catalogue.php";

    private String main;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getCatalogLink(CatalogCategory catalogCategory) {
        return MessageFormat.format("{0}/{1}?id={2}", main, CATALYS_CATALOG, catalogCategory.getId());
    }

}
