package my.vaadin.app.test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TextFieldElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(BlockJUnit4ClassRunner.class)
public class MyFirstIT extends TestBenchTestCase {
	private static final Logger log = LoggerFactory.getLogger(MyFirstIT.class);

	private WebDriver driver;

	@Before
	public void setup() {
		setDriver(driver = new DriverUtil().getPreferredDriver());
	}

	@Test
	public void addNewCustomer_formShouldBeVisible() {
		driver.get("http://localhost:8080");
		$(ButtonElement.class).caption("Add new customer").first().click();
		assertTrue($(TextFieldElement.class).caption("Email").exists());
	}

	@After
	public void teardown() {
		try {
			driver.close();
		} catch (Exception ex) {
			log.info("Failed to close driver", ex);
		}
	}
}
