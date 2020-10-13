package com.github.bc.doMyTraining.catalys.course;

import org.sikuli.script.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class PatternBuilder {

    private static Pattern create(String resolution, String fileName) {

        Resource resource = new ClassPathResource(MessageFormat.format("{0}/{1}", resolution, fileName));
        try {
            File file = resource.getFile();
            return new Pattern(file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }

    }

    public static Pattern buttonNext(String resolution) {
        return create(resolution, "buttonNext.png");
    }

    public static Pattern buttonPopUpYes(String resolution) {
        return create(resolution, "buttonResumeYes.png");
    }

    public static Pattern buttonPopUpNo(String resolution) {
        return create(resolution, "buttonResumeNo.png");
    }

    public static Pattern progressBarComplete(String resolution) {
        return create(resolution, "progressBarComplete.png");
    }

    public static Pattern radioButtonNotSet(String resolution) {
        return create(resolution, "radioButtonNotSelect.png");
    }

    public static Pattern buttonSubmit(String resolution) {
        return create(resolution, "buttonSubmit.png");
    }

    public static Pattern logoNtt(String resolution) {
        return create(resolution, "logoNtt.png");
    }

    public static Pattern LogoNttText(String resolution) {
        return create(resolution, "logoNttText.png");
    }

    public static Pattern popUpTopRight(String resolution) {
        return create(resolution, "popUpTopRight.png");
    }

    public static Pattern popUpTopLeft(String resolution) {
        return create(resolution, "popUpTopLeft.png");
    }

}
