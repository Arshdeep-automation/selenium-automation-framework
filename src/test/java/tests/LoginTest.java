package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import login.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    public void invalidLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("wrong_user", "wrong_pass");

        String errorMessage = driver.findElement(
                By.cssSelector("h3[data-test='error']")
        ).getText();

        Assert.assertTrue(errorMessage.contains("Epic sadface"));
    }

    @Test
    public void addToCartTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();

        Assert.assertEquals(cartCount, "1");
    }

    @Test
    public void checkoutTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // Add product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Go to cart
        driver.findElement(By.className("shopping_cart_link")).click();

        // Click checkout
        driver.findElement(By.id("checkout")).click();

        // Enter details
        driver.findElement(By.id("first-name")).sendKeys("John");
        driver.findElement(By.id("last-name")).sendKeys("Doe");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.id("continue")).click();

        driver.findElement(By.id("finish")).click();

        Assert.assertTrue(driver.getPageSource().contains("Thank you"));
    }

    @Test
    public void logoutTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        driver.findElement(By.id("react-burger-menu-btn")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.id("logout_sidebar_link")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"));
    }
}