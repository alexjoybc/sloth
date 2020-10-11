package com.github.bc.doMyTraining.catalys.catalog;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PageParser {

    private static final String PRIMARY_NODE = "primaryNode";
    public static final String TABLE_HEADERS = "Info Course Mode Language Duration Points";

    public static List<WebElement> primaryNodes(WebDriver webDriver) {

        List<WebElement> result = new ArrayList<>();

        for(WebElement element: webDriver.findElements(By.className(PRIMARY_NODE))) {

            if(!isCourseTable(element)) result.add(element);

        }

        return result;
    }

    private static boolean isCourseTable(WebElement x) {
        return StringUtils.startsWith(x.getText(), TABLE_HEADERS);
    }

    public static void toggleExpander(WebElement webElement) {
        webElement.findElement(By.className("tvExpander")).click();
    }

}
