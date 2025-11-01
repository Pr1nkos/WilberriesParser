package wbparser.parser


import org.openqa.selenium.WebDriver
import wbparser.config.WildberriesConfig
import wbparser.extractor.FeedbackExtractor
import wbparser.model.WildberriesFeedback
import wbparser.page.WildberriesFeedbackPage

class FeedbackParser(
    private val driver: WebDriver,
    private val config: WildberriesConfig
) {
    fun parse(url: String): List<WildberriesFeedback> {
        driver.get(url)

        val page = WildberriesFeedbackPage(driver, config)
        page.waitForFeedbacksLoaded()

        val cardHtmlList = page.getFeedbackCardsHtml()

        val extractor = FeedbackExtractor(config)
        return cardHtmlList.mapNotNull { html ->
            extractor.extractFromHtml(html)
        }

    }
}