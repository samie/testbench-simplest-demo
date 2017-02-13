package my.vaadin.app.test;

import static org.junit.Assert.assertTrue;

import com.vaadin.testbench.screenshot.ImageFileUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

@RunWith(BlockJUnit4ClassRunner.class)
public class MyFirstIT extends TestBenchTestCase {
	private static final Logger log = LoggerFactory.getLogger(MyFirstIT.class);

	private WebDriver driver;

	@Before
	public void setup() {
		setDriver(driver = new DriverUtil().getPreferredDriver());
	}

	@Test
	public void addNewCustomer_formShouldBeVisible() throws Exception {
		driver.get("http://localhost:8080");
		$(ButtonElement.class).caption("Add new customer").first().click();
		assertTrue($(TextFieldElement.class).caption("Email").exists());
        dumpScreenshot();
	}

	@After
	public void teardown() {
		// need to wrap driver.close() in try-catch until this bug is fixed:
		// 
		try {
			driver.close();
		} catch (Exception ex) {
			log.info("Failed to close driver", ex);
		}
	}

	private void dumpScreenshot() throws Exception {
		BufferedImage screenshotImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		// Store the screenshot in the errors directory
		ImageFileUtil.createScreenshotDirectoriesIfNeeded();
		ImageIO.write(screenshotImage, "png", new File("target/screenshot.png"));
	}
}
