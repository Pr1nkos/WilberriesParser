# Wildberries Feedback Parser

## Что это?
Простой Kotlin-проект для парсинга отзывов с Wildberries. Использует Selenium для скроллинга страницы, Jsoup для извлечения данных (автор, текст, дата, рейтинг, фото/видео, теги) и OpenCSV для сохранения в CSV. Логи через SLF4J/Logback. Весь код в тестах — запуск через JUnit.

## Как запустить?
1. **Требования**: JDK 21+, Gradle 8+, Chrome + ChromeDriver (укажите путь в конфиге).
2. **Конфиг**: Создайте `src/main/resources/config.yaml` (пример в репозитории). Укажите URL продукта, путь к CSV и селекторы.
3. **Запуск**: `.\gradlew clean test --tests "wbparser.WildberriesFeedbackParserTest" --info"`
    - Тест парсит отзывы, логирует результат и сохраняет в CSV.
    - Если в консоли - "кракозябры", то (для PowerShell) нужно ввести команду `[Console]::OutputEncoding = [System.Text.Encoding]::UTF8`
4. **Headless**: В конфиге `selenium.headless=true` — без открытия браузера.
5. **Логи**: В консоли и в файле `logs/app.log`. Для более точных настроек используйте `logback.xml`.

## Зависимости
В `build.gradle.kts` (Gradle Kotlin DSL):

- `selenium-java:4.38.0` — браузер-автоматизация.
- `jsoup:1.21.2` — HTML-парсинг.
- `opencsv:5.12.0` — CSV-запись.
- `slf4j-api:2.0.17` + `logback-classic:1.5.12` + `kotlin-loggin:7.0.3` — логи.
- `junit-jupiter:5.10.1` — тесты.
- `kotlin-stdlib:2.2.20` — Kotlin.

Обновить: `./gradlew dependencies`.

## Что улучшить?
- **Быстро**: Добавить retry на ошибки сети; фильтр дубликатов.
- **Средне**: Асинхронный парсинг (Coroutines); хранение в БД (SQLite).
- **Долго**: Docker-контейнер; поддержка Ozon; веб-UI для URL.

Версия: 1.0 | Дата: 01.11.2025