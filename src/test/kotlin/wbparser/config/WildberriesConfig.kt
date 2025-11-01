package wbparser.config

data class WildberriesConfig(
    val feedbackUrl: String,
    val outputCsvPath: String,
    val dateFormat: String,
    val scraping: ScrapingConfig,
    val selectors: SelectorsConfig
)

data class ScrapingConfig(
    val scrollDelayMs: Long,
    val maxScrollAttempts: Int,
    val waitForFeedbacksTimeoutSec: Long
)

data class SelectorsConfig(
    val feedbackCard: String,
    val author: String,
    val date: String,
    val reviewBody: String,
    val rating: String,
    val photo: String,
    val videoBtn: String,
    val tags: String
)