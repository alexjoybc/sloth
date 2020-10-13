package com.github.bc.doMyTraining.screen;

import org.sikuli.script.Screen;

import java.text.MessageFormat;

public class ScreenHelper {

    public static String getResolution(Screen screen) {
        return MessageFormat.format("{0,number,#}x{1,number,#}", screen.getBounds().getWidth(), screen.getBounds().getHeight());
    }

}
