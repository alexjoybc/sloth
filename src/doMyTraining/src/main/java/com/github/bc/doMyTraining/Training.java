package com.github.bc.doMyTraining;

import com.github.bc.doMyTraining.catalys.CatalogCategory;
import com.github.bc.doMyTraining.catalys.catalog.PageParser;
import com.github.bc.doMyTraining.config.NttProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
public class Training implements CommandLineRunner {

    private static Logger logger = LoggerFactory
            .getLogger(Training.class);


    private final NttProperties nttProperties;

    static String IE_DRIVER_PATH = "C:\\selenium\\IEDriverServer.exe";
    static String CHROME_DRIVER_PATH = "C:\\selenium\\chromedriver.exe";
    private static final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    public Training(NttProperties nttProperties) {
        this.nttProperties = nttProperties;
    }

    @Override
    public void run(String... args) throws Exception {

      //  System.setProperty(WEBDRIVER_IE_DRIVER, IE_DRIVER_PATH);
        System.setProperty(CHROME_DRIVER_PATH, WEBDRIVER_CHROME_DRIVER);


        logger.info("start training");

       // WebDriver driver = new InternetExplorerDriver();
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        logger.info("getting to catalog");
        driver.get(nttProperties.getCatalys().getCatalogLink(CatalogCategory.TECHNICAL));
        logger.info(driver.getTitle() + " page open");

        logger.info("expending all");

        Thread.sleep(Duration.ofSeconds(2).toMillis());

        for (WebElement primaryNode: PageParser.primaryNodes(driver)
             ) {

//            PageParser.toggleExpander(primaryNode);

            WebElement test = primaryNode.findElement(By.className("nodeText"));

            logger.info("find new category: {}", test.getText());

            test.click();

            int maxAttempt = 10;
            int retry = 0;

            while (primaryNode.findElements(By.className("subNode")).stream().findAny().isPresent() && maxAttempt < retry ) {
                Thread.sleep(Duration.ofSeconds(1).toMillis());
                retry++;
            }

            logger.info("found {} subNodes", primaryNode.findElements(By.className("subNode")).stream().count());


            logger.info(primaryNode.findElement(By.className("nodeText")).getText());
        }


        logger.info("all node expended");


    }

}
