package my.vaadin.app.test;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.UserAgent;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.screenshot.ImageFileUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class MyFirstIT extends TestBenchTestCase {
	/**
	 * This will also close the Selenium Driver.
	 */
	@Rule
	public final ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this);

	private int count = 0;

	@Before
	public void setup() {
		// Chrome needs ChromeDriver installed.
		// Ubuntu users: sudo apt install chromium-chromedriver
//		System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
//		setDriver(driver = new ChromeDriver());

		// JavaFX browser comes preinstalled with JavaFX
		// UserAgent needs to be tuned though: https://github.com/MachinePublishers/jBrowserDriver/issues/260
		setDriver(createNewDriver());
	}

	@Test
	public void clickButtonShouldChangeTheLabel() throws Exception {
		driver.get("http://localhost:8080");
		$(ButtonElement.class).caption("Click Me").first().click();
		assertEquals("Clicked!", $(LabelElement.class).id("consoleLabel").getText());
        dumpScreenshot();
	}

	private void dumpScreenshot() throws Exception {
		BufferedImage screenshotImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
		// Store the screenshot in the errors directory
		ImageFileUtil.createScreenshotDirectoriesIfNeeded();
		ImageIO.write(screenshotImage, "png", new File("target/screenshot.png"));
	}


	@Test
	public void testParallel() {
		int testCount = 10;
		ExecutorService executor = Executors.newFixedThreadPool(testCount);
		List<Callable<Object>> tests = new ArrayList<Callable<Object>>();
		for (int i = 0; i <testCount;i++) {
			final int testNumber = i;
			tests.add(() -> {
				try {
					System.out.println("Starting parallel test #" + testNumber +"..." );
					System.out.flush();
					ExampleTest test = new ExampleTest(createNewDriver());
					test.clickButtonShouldChangeTheLabel();
					System.out.println("Done #" + testNumber +"." );
					System.out.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			});
		}
		try {
			executor.invokeAll(tests);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private WebDriver createNewDriver() {
		return new JBrowserDriver(Settings.builder()
				.requestHeaders(RequestHeaders.CHROME)
				.userAgent(new UserAgent(
						UserAgent.Family.WEBKIT,
						"Google Inc.",
						"Win32",
						"Windows NT 6.1",
						"5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2869.0 Safari/537.36",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2869.0 Safari/537.36"))
				.build());
	}
}
