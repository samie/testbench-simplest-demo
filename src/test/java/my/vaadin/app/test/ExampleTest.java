package my.vaadin.app.test;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.screenshot.ImageFileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * Created by se on 09/05/2017.
 */
public class ExampleTest extends TestBenchTestCase {
    public ExampleTest(WebDriver driver) {
        setDriver(driver);
    }

    public void clickButtonShouldChangeTheLabel() throws Exception {
        driver.get("http://localhost:8080");
        $(ButtonElement.class).caption("Click Me").first().click();
        assertEquals("Clicked!", $(LabelElement.class).id("consoleLabel").getText());
    }

}
