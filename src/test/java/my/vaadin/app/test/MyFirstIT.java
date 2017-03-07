package my.vaadin.app.test;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.screenshot.ImageFileUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class MyFirstIT extends TestBenchTestCase {
	@Rule
	public final ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this);

	@Before
	public void setup() {
//		System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
//		setDriver(driver = new ChromeDriver());
		setDriver(new JBrowserDriver());
	}

	@Test
	public void clickButtonShouldChangeTheLabel() throws Exception {
		driver.get("http://localhost:8080");
		$(ButtonElement.class).caption("Click Me").first().click();
		assertEquals("Clicked!", $(LabelElement.class).id("consoleLabel").getText());
        dumpScreenshot();
	}

/*
	@After
	public void teardown() {
		// need to wrap driver.close() in try-catch until this bug is fixed:
		// https://github.com/MachinePublishers/jBrowserDriver/issues/250
		try {
			driver.close();
		} catch (Exception ex) {
			log.info("Failed to close driver", ex);
		}
	}
*/

	private void dumpScreenshot() throws Exception {
		BufferedImage screenshotImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		// Store the screenshot in the errors directory
		ImageFileUtil.createScreenshotDirectoriesIfNeeded();
		ImageIO.write(screenshotImage, "png", new File("target/screenshot.png"));
	}
}
