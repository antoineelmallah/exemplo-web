package com.example.exemplo_web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageTest {

    private WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() throws MalformedURLException, UnknownHostException {
        ChromeOptions options = new ChromeOptions().addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void listUsers_shouldDisplayAllUsers() {

        // Perform Selenium request
        driver.get("http://localhost:" + port);

        // Expected row texts
        List<String> expectedLines = List.of(
                "Name Email Action",
                "Cris cris@email.com Edit Delete",
                "Ana ana@email.com Edit Delete",
                "Benja benja@email.com Edit Delete",
                "Fred fred@email.com Edit Delete");

        assertTableContainsAllUsers(expectedLines);
    }

    @Test
    public void addUser_shouldDisplayAllUsers() {

        // Perform Selenium request
        driver.get("http://localhost:" + port);

        // Find "Add a new user" link
        driver.findElement(By.linkText("Add a new user")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        // Wait until find form input with id "name"
        WebElement inputName = driver.findElement(By.id("name"));
        wait.until(d -> inputName.isDisplayed());

        // Insert text on "name" form input
        inputName.sendKeys("Other User");

        // Wait until find form input with id "email"
        WebElement inputEmail = driver.findElement(By.id("email"));
        inputEmail.sendKeys("other.user@email.com");

        // Find and click on "Add User" button
        driver.findElement(By.xpath("/html/body/form/input[3]")).click();

        // Wait until H2 "Users" shows up on page
        WebElement usersH2 = driver.findElement(By.tagName("h2"));
        wait.until(d -> usersH2.isDisplayed());

        // Assert that new user is present
        List<String> expectedLines = List.of(
                "Name Email Action",
                "Cris cris@email.com Edit Delete",
                "Ana ana@email.com Edit Delete",
                "Benja benja@email.com Edit Delete",
                "Fred fred@email.com Edit Delete",
                "Other User other.user@email.com Edit Delete");

        assertTableContainsAllUsers(expectedLines);
    }

    private void assertTableContainsAllUsers(List<String> expected) {
        // Find table rows. Including header
        List<WebElement> trs = driver.findElements(By.tagName("tr"));
        assertEquals(expected.size(), trs.size());

        // Extract row text
        List<String> listItems = trs.stream()
                .map(WebElement::getText)
                //.peek(System.out::println)
                .toList();

        assertLinesMatch(expected, listItems);
    }


}
