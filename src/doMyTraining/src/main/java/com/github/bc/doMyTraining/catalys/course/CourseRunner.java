package com.github.bc.doMyTraining.catalys.course;

import com.github.bc.doMyTraining.Training;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        Pattern buttonNextPattern = PatternBuilder.buttonNext();
        Pattern buttonResumePattern = PatternBuilder.buttonPopUpYes();
        Pattern buttonResumeNoPattern = PatternBuilder.buttonPopUpNo();
        Pattern progressBarCompletePattern = PatternBuilder.progressBarComplete();
        Pattern radioButtonNotSelectPattern = PatternBuilder.radioButtonNotSet();
        Pattern buttonSubmitPattern = PatternBuilder.buttonSubmit();

        Screen s = new Screen();

        Thread.sleep(Duration.ofSeconds(3).toMillis());

        logger.info("score {}", s.exists(buttonResumeNoPattern).getScore());

        if (s.exists(buttonResumeNoPattern) != null && s.exists(buttonResumeNoPattern).getScore() > 0.9) {

            s.click(buttonResumeNoPattern);

        }

        logger.info("resuming course");

        logger.info("progress complete");

        for (int i = 0; i < 20; i++) {

            if (s.exists(radioButtonNotSelectPattern, 2) != null && s.exists(radioButtonNotSelectPattern, 2).getScore() > 0.9) {

                // capture question

                Match matchLogo = s.find(PatternBuilder.logoNtt());
                Match matchLogoText = s.find(PatternBuilder.LogoNttText());

                List<Match> answerList = new ArrayList<>();

                s.findAll(radioButtonNotSelectPattern).forEachRemaining(match -> answerList.add(match));

                Optional<Match> upperAnswer = answerList
                        .stream()
                        .sorted(Comparator.comparingInt(match -> match.getTopLeft().getX()))
                        .findFirst();

                if (upperAnswer.isPresent()) {

                    Region question = new Region(matchLogo.getX(), matchLogo.getBottomLeft().getY(), matchLogoText.getX() - matchLogo.getX() + matchLogoText.getW(), upperAnswer.get().getY() - matchLogo.getBottomLeft().getY());

                    question.highlight();

                    logger.info(question.text());

                    s.findAll(radioButtonNotSelectPattern).forEachRemaining(match -> {

                        Region text = new Region(match.x, match.y, match.w + 300, match.h);

                        logger.info("test for this one: {}", text.text());

                    });

                }

            } else {

                //       s.wait(progressBarCompletePattern.similar(0.9), 100);
                s.click(s.findBest(buttonNextPattern));
                s.mouseMove(20, 0);

            }
        }


        logger.info("next clicked");

    }
}
