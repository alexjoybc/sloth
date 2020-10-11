package com.github.bc.doMyTraining.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverConfig {

    static String IE_DRIVER_PATH = "C:\\selenium\\IEDriverServer.exe";
    static String CHROME_DRIVER_PATH = "C:\\selenium\\chromedriver.exe";
    private static final String WEBDRIVER_IE_DRIVER = "webdriver.ie.driver";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    @Bean
    public WebDriver webDriver() {

        System.setProperty(WEBDRIVER_IE_DRIVER, IE_DRIVER_PATH);
        WebDriver driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        return driver;
    }



}
