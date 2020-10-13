package com.github.bc.doMyTraining.catalys.course;

import com.github.bc.doMyTraining.Training;
import com.github.bc.doMyTraining.screen.ScreenHelper;
import org.apache.commons.lang3.StringUtils;
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
    private final QuizzService quizzService;

    private static Logger logger = LoggerFactory
            .getLogger(Training.class);

    public CourseRunner(WebDriver driver, QuizzService quizzService) {
        this.driver = driver;
        this.quizzService = quizzService;
    }


    @Override
    public void run(String... args) throws Exception {

        String quizzId = "2075";

        System.setProperty("java.awt.headless", "false");

        driver.get("https://catalys.portal.nttdataservices.com/mod/scorm/player.php?a=" + quizzId +"&currentorg=&scoid=4251&display=popup&mode=normal&newattempt=on");

        Screen s = new Screen();
        String resolution = ScreenHelper.getResolution(s);
        logger.info("Monitor Size: {}", resolution);

        Pattern buttonNextPattern = PatternBuilder.buttonNext(resolution);
        Pattern buttonResumePattern = PatternBuilder.buttonPopUpYes(resolution);
        Pattern buttonResumeNoPattern = PatternBuilder.buttonPopUpNo(resolution);
        Pattern progressBarCompletePattern = PatternBuilder.progressBarComplete(resolution);
        Pattern radioButtonNotSelectPattern = PatternBuilder.radioButtonNotSet(resolution);
        Pattern buttonSubmitPattern = PatternBuilder.buttonSubmit(resolution);
        Pattern popUpTopRight = PatternBuilder.popUpTopRight(resolution);
        Pattern popUpTopLeft = PatternBuilder.popUpTopLeft(resolution);




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

                Match matchLogo = s.find(PatternBuilder.logoNtt(resolution));
                Match matchLogoText = s.find(PatternBuilder.LogoNttText(resolution));

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

                    Optional<QuizzQuestion> quizzQuestion = quizzService.get(quizzId, question.text().hashCode());

                    question.highlightOff();

                    if(quizzQuestion.isPresent()) {

                    } else {

                        Optional<Match> randomAnswer = answerList.stream().findAny();

                        Region text = new Region(randomAnswer.get().x, randomAnswer.get().y, matchLogoText.getTopRight().getX() - randomAnswer.get().getX(), randomAnswer.get().h);
                        String captureAnswer = text.text();

                        if(randomAnswer.isPresent()) {
                            s.click(randomAnswer.get());
                            s.click(buttonSubmitPattern);
                        }

                        Match popUpTopRightMatch = s.find(popUpTopRight);
                        Match popUpTopLeftMatch = s.find(popUpTopLeft);

                        Region questionResult = new Region(
                                popUpTopRightMatch.getTopRight().getX(),
                                popUpTopRightMatch.getY(),
                                popUpTopLeftMatch.getBottomRight().getX() - popUpTopRightMatch.getX(),
                                popUpTopLeftMatch.getH());


                        questionResult.highlight();

                        if(StringUtils.equalsIgnoreCase("Correct", questionResult.text())) {

                            QuizzQuestion quizzQuestionCorrect = new QuizzQuestion(quizzId, question.text());
                            quizzQuestionCorrect.setAnswer(captureAnswer);
                            quizzService.put(quizzQuestionCorrect);

                        } else {

                            QuizzQuestion quizzQuestionIncorect = new QuizzQuestion(quizzId, question.text());
                            quizzQuestionIncorect.addBadAnswer(captureAnswer);
                            quizzService.put(quizzQuestionIncorect);

                        }




                    }

//                    s.findAll(radioButtonNotSelectPattern).forEachRemaining(match -> {
//
//
//
//                        text.highlight();
//
//                        logger.info("test for this one: {}", text.text());
//
//                        text.highlightOff();
//
//                    });

                }

            } else {

                //       s.wait(progressBarCompletePattern.similar(0.9), 100);
                s.click(s.findBest(buttonNextPattern));
                s.mouseMove(0, 100);

            }
        }


        logger.info("next clicked");

    }
}
