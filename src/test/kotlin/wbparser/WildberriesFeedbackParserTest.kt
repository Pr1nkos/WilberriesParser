package wbparser


import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import wbparser.config.AppConfig
import wbparser.config.ConfigLoader
import wbparser.parser.FeedbackParser
import wbparser.writer.CsvWriter

class WildberriesFeedbackParserTest {

    private val logger = KotlinLogging.logger { }

    private lateinit var driver: WebDriver
    private lateinit var config: AppConfig

    @BeforeEach
    fun setUp() {
        config = ConfigLoader.load()

        System.setProperty("webdriver.chrome.driver", config.selenium.chromeDriverPath)

        val options = ChromeOptions().apply {
            if (config.selenium.headless) addArguments("--headless=new")
            addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080")
        }

        driver = ChromeDriver(options)
    }

    @Test
    fun `parse and save Wildberries feedbacks`() {
        val parser = FeedbackParser(driver, config.wildberries)
        val feedbacks = parser.parse(config.wildberries.feedbackUrl)

        logger.info { "Спарсено отзывов: ${feedbacks.size}" }
        CsvWriter.write(feedbacks, config.wildberries.outputCsvPath)
        logger.info { "Результат сохранён в: ${config.wildberries.outputCsvPath}" }
    }


    @AfterEach
    fun tearDown() {
        driver.quit()
    }
}