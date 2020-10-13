package com.github.bc.doMyTraining.catalys.course;

import org.sikuli.script.Pattern;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public class PatternBuilder {

    private static Pattern create(String fileName) {
        Resource resource = new ClassPathResource(fileName);
        try {
            File file = resource.getFile();
            return new Pattern(file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public static Pattern buttonNext() {
        return create("buttonNext.png");
    }

    public static Pattern buttonPopUpYes() {
        return create("buttonResumeYes.png");
    }

    public static Pattern buttonPopUpNo() {
        return create("buttonResumeNo.png");
    }

    public static Pattern progressBarComplete() {
        return create("progressBarComplete.png");
    }

    public static Pattern radioButtonNotSet() {
        return create("radioButtonNotSelect.png");
    }

    public static Pattern buttonSubmit() {
        return create("buttonSubmit.png");
    }

    public static Pattern logoNtt() {
        return create("logoNtt.png");
    }

    public static Pattern LogoNttText() {
        return create("logoNttText.png");
    }

}
