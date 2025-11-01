package wbparser.extractor
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jsoup.Jsoup
import org.openqa.selenium.WebElement
import wbparser.config.WildberriesConfig
import wbparser.model.WildberriesFeedback
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class FeedbackExtractor(private val config: WildberriesConfig) {
    private val logger = KotlinLogging.logger {  }

    private val outputFormatter = DateTimeFormatter.ofPattern(config.dateFormat, Locale.ENGLISH)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
    fun extractFromHtml(html: String): WildberriesFeedback? {
        return try {
            val doc = Jsoup.parse(html)

            val author = doc.select(config.selectors.author).text().trim()
            val rawDate = doc.select(config.selectors.date).text().trim()
            val text = doc.select(config.selectors.reviewBody).text().trim()

            val rating = extractRatingFromClasses(doc.select(config.selectors.rating).attr("class"))
            val photoCount = doc.select(config.selectors.photo).size
            val hasVideo = doc.select(config.selectors.videoBtn).isNotEmpty()
            val tags = doc.select(config.selectors.tags)
                .map { it.text().trim() }
                .filter { it.isNotEmpty() }

            WildberriesFeedback(
                publicationDate = normalizeDate(rawDate),
                author = author,
                text = text,
                rating = rating,
                photoCount = photoCount,
                hasVideo = hasVideo,
                tags = tags
            )
        } catch (e: Exception) {
            val doc = Jsoup.parse(html)
            val author = doc.select(config.selectors.author).text().trim()
            logger.error { "❌ Критическая ошибка парсинга карточки автора '$author': ${e.message}" }
            null
        }
    }

    private fun extractRatingFromClasses(classes: String?): Int {
        return when {
            classes?.contains("star5") == true -> 5
            classes?.contains("star4") == true -> 4
            classes?.contains("star3") == true -> 3
            classes?.contains("star2") == true -> 2
            classes?.contains("star1") == true -> 1
            else -> 0
        }
    }

    private fun normalizeDate(input: String): String {
        val now = LocalDate.now()
        return when {
            input.startsWith("Сегодня") -> {
                val timePart = input.substringAfter(", ").trim()
                val localTime = LocalTime.parse(timePart, timeFormatter)
                now.atTime(localTime).format(outputFormatter)
            }
            input.startsWith("Вчера") -> {
                val timePart = input.substringAfter(", ").trim()
                val localTime = LocalTime.parse(timePart, timeFormatter)
                now.minusDays(1).atTime(localTime).format(outputFormatter)
            }
            else -> {
                val cleanInput = input.substringBefore(" · ").trim()
                val parts = cleanInput.split(",", limit = 2).map { it.trim() }
                if (parts.size != 2) {
                    logger.warn { "⚠️ Неизвестный формат даты: '$input'" }
                    return "Не указана"
                }
                val datePart = parts[0]
                val timePart = parts[1]

                val day = datePart.substringBefore(" ").toInt()
                val month = when (val monthName = datePart.substringAfter(" ").lowercase()) {
                    "января" -> 1
                    "февраля" -> 2
                    "марта" -> 3
                    "апреля" -> 4
                    "мая" -> 5
                    "июня" -> 6
                    "июля" -> 7
                    "августа" -> 8
                    "сентября" -> 9
                    "октября" -> 10
                    "ноября" -> 11
                    "декабря" -> 12
                    else -> {
                        logger.warn { "⚠️ Неизвестный месяц: '$monthName'" }
                        return "Не указана"
                    }
                }

                val year = if (month > now.monthValue || (month == now.monthValue && day > now.dayOfMonth)) {
                    now.year - 1
                } else {
                    now.year
                }

                val localTime = LocalTime.parse(timePart, timeFormatter)
                LocalDateTime.of(year, month, day, localTime.hour, localTime.minute)
                    .format(outputFormatter)
            }
        }
    }
}