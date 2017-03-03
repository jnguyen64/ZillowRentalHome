package controller;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdditionalConditions {

public static ExpectedCondition<Boolean> jQueryAJAXCallsHaveCompleted() {
    return new ExpectedCondition<Boolean>() {

        @Override
        public Boolean apply(WebDriver driver) {
            return (Boolean) ((JavascriptExecutor) driver).executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
        }
    };
}
}