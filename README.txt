            Тестовое задание
Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
если курс по отношению к USD за сегодня стал выше вчерашнего,
то отдаем рандомную отсюда https://giphy.com/search/rich
если ниже - отсюда https://giphy.com/search/broke

            Ссылки
REST API курсов валют https://docs.openexchangerates.org/
REST API гифок https://developers.giphy.com/docs/api#quick-start-guide

            Must Have
- Сервис на Spring Boot 2 + Java / Kotlin
- Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
- Для взаимодействия с внешними сервисами используется Feign
- Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
- На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
- Для сборки должен использоваться Gradle
- Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
            Nice to Have
- Сборка и запуск Docker контейнера с этим сервисом

            Инструкция по запуску:
1. Перейти в директорию проекта
2. Получим jar файл пиложения (в директори по улочанию)
    ./gradlew build
3. Создадим образ с названием test_task
    docker build . -t test_task
4. Запустим приложение из образа test_task в контенейре с названием test_container
    docker run -p 8080:8080 --name test_container -t test_task
5. Остановить работу контейнера test_container (после завершения проверки)
    docker stop test_container
Без использования docker: запустить ./gradlew bootRun, остановить ./gradlew --stop

Поле запуска приложения будут доступны два endpoint'та:

Получение gif файла используя api.giphy.com/v1/gifs/search
http://localhost:8080/gif-by-search/last-change/exchange-rate/currency/{shot-name}

Получение gif файла используя api.giphy.com/v1/gifs/random
http://localhost:8080/gif-by-tag/last-change/exchange-rate/currency/{shot-name}

В лучае, если курс валюты не менялся, то выдается результат аналогичный как для его понижения.

shot-name - сокращенное наименоваие валюты можно получить по ссылке https://openexchangerates.org/api/currencies.json

{
  "AED": "United Arab Emirates Dirham",
  "AFN": "Afghan Afghani",
  "ALL": "Albanian Lek",
  "AMD": "Armenian Dram",
  "ANG": "Netherlands Antillean Guilder",
  "AOA": "Angolan Kwanza",
  "RUB": "Russian Ruble",
  ...
}