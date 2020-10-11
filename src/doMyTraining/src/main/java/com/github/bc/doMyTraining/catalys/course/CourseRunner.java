package com.github.bc.doMyTraining.catalys.course;

import com.github.bc.doMyTraining.Training;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.time.Duration;

@Component
public class CourseRunner implements CommandLineRunner {

    private final WebDriver driver;

    private static Logger logger = LoggerFactory
            .getLogger(Training.class);

    public CourseRunner(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) throws Exception {

        System.setProperty("java.awt.headless", "false");

        driver.get("https://catalys.portal.nttdataservices.com/mod/scorm/player.php?a=2075&currentorg=&scoid=4251&display=popup&mode=normal&newattempt=on");

        Resource buttonNext = new ClassPathResource("buttonNext.png");
        File buttonNextPng = buttonNext.getFile();
        Pattern buttonNextPattern = new Pattern(buttonNextPng.getAbsolutePath());

        Resource buttonResume = new ClassPathResource("buttonResumeYes.png");
        File buttonResumePng = buttonResume.getFile();
        Pattern buttonResumePattern = new Pattern(buttonResumePng.getAbsolutePath());

        Resource buttonResumeNo = new ClassPathResource("buttonResumeNo.png");
        File buttonResumeNoPng = buttonResumeNo.getFile();
        Pattern buttonResumeNoPattern = new Pattern(buttonResumeNoPng.getAbsolutePath());

        Resource progressBarComplete = new ClassPathResource("progressBarComplete.png");
        File progressBarCompletePng = progressBarComplete.getFile();
        Pattern progressBarCompletePattern = new Pattern(progressBarCompletePng.getAbsolutePath());

        Resource radioButtonNotSelect = new ClassPathResource("radioButtonNotSelect.png");
        File radioButtonNotSelectPng = radioButtonNotSelect.getFile();
        Pattern radioButtonNotSelectPattern = new Pattern(radioButtonNotSelectPng.getAbsolutePath());

        Screen s = new Screen();

        Thread.sleep(Duration.ofSeconds(3).toMillis());

        logger.info("score {}", s.exists(buttonResumeNoPattern).getScore());

        if(s.exists(buttonResumeNoPattern) != null && s.exists(buttonResumeNoPattern).getScore() > 0.9) {

            s.click(buttonResumeNoPattern);
        }

        logger.info("resuming course");



        logger.info("progress complete");

        for(int i = 0; i < 20; i++) {

            if(s.exists(radioButtonNotSelectPattern, 2) != null && s.exists(radioButtonNotSelectPattern, 2).getScore() > 0.9) {

                s.findAll(radioButtonNotSelectPattern).forEachRemaining(match -> {

                    Region text = new Region(match.x, match.y, match.w + 300, match.h);

                    logger.info("test for this one: {}", text.text());

                });


            } else {

                s.wait(progressBarCompletePattern.similar(0.9), 100);
                s.click(buttonNextPattern);

            }
        }


        logger.info("next clicked");

    }
}
