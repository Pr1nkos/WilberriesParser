package wbparser.page

import io.github.oshai.kotlinlogging.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import wbparser.config.WildberriesConfig
import java.time.Duration

class WildberriesFeedbackPage(
    private val driver: WebDriver,
    private val config: WildberriesConfig
) {
    private val logger = KotlinLogging.logger {  }

    private val wait = WebDriverWait(driver, Duration.ofSeconds(30))
    private val selectors = config.selectors

    fun waitForFeedbacksLoaded() {
        var lastCount = 0
        var attempts = 1
        val maxAttempts = config.scraping.maxScrollAttempts

        while (attempts <= maxAttempts) {
            (driver as JavascriptExecutor).executeScript("window.scrollTo(0, document.body.scrollHeight);")

            Thread.sleep(config.scraping.scrollDelayMs)

            val currentCards = driver.findElements(By.cssSelector(selectors.feedbackCard))
            val currentCount = currentCards.size

            logger.debug { "Попытка $attempts: найдено карточек = $currentCount (было $lastCount)" }  // Отладка

            if (currentCount == lastCount) break

            lastCount = currentCount
            attempts++
        }

        wait.until { d -> d.findElements(By.cssSelector(selectors.feedbackCard)).isNotEmpty() }

        logger.debug { "Итогово загружено отзывов: $lastCount" }
    }

    fun getFeedbackCardsHtml(): List<String> {
        val cards: List<WebElement> = driver.findElements(
            By.cssSelector(config.selectors.feedbackCard)
        )
        return cards.map { it.getAttribute("outerHTML") as String }
    }
}
