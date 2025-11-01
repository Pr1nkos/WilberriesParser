package wbparser.writer

import com.opencsv.CSVWriter
import wbparser.model.WildberriesFeedback
import java.io.FileWriter
import java.nio.charset.StandardCharsets

object CsvWriter {
    fun write(feedbacks: List<WildberriesFeedback>, path: String) {
        FileWriter(path, StandardCharsets.UTF_8).use { fw ->
            CSVWriter(fw).use { writer ->
                writer.writeNext(arrayOf(
                    "Дата публикации",
                    "Автор",
                    "Текст отзыва",
                    "Оценка",
                    "Количество фотографий",
                    "Наличие видео",
                    "Теги"
                ))
                feedbacks.forEach { fb ->
                    writer.writeNext(arrayOf(
                        fb.publicationDate,
                        fb.author,
                        fb.text,
                        fb.rating.toString(),
                        fb.photoCount.toString(),
                        if (fb.hasVideo) "Да" else "Нет",
                        fb.tags.joinToString("; ")
                    ))
                }
            }
        }
    }
}