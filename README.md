﻿<!-- -*- coding: utf-8 -*- -->

# Учбовий проект "Бібліотека", створений на Spring Boot
Суть така:
1. Створені відповідні сутності (класи) для книг і користувачів.
2. Реалізовано зв'язок між книгами і користувачами (один до багатьох).
3. Реалізована логіка для можливості користувачів брати та повертати книги.
4. Реалізована логіка, що якщо людина затримає здачу книжки, вона підсвітиться червоним кольором.

## Технології

* Spring Boot
* Postgres
* Maven

## Запуск сайту на своєму ПК

1. Встановіть Docker.
2. Знаходячись в корені цього проекту, виконайте команду:
    ```shell
    docker compose up
    ```
3. Підключіться до порожньої (поки що) бази даних (яка мала з'явитися після виконання пункту 2), тобто
   здійсніть з'єднання. Дані для підключення дивіться тут:
   `books_and_people_spring_boot\src\main\resources\application.properties`.
4. Здійсніть імпорт у нашу локальну базу даних, що знаходиться в Docker, за допомогою файлу дампу, який знаходиться тут:
   `books_and_people_spring_boot\src\main\resources\sql\DumpBooksPeople.sql` (клацніть правою кнопкою миші
   в IntelliJ IDEA на базі даних, виберіть команду "Restore with psql").
5. У файлі
   `books_and_people_spring_boot\src\main\java\logvinov\springcourse\Project2MyBoot\Project2MyBootApplication.java`
   запустіть метод `main`.
6. У браузері перейдіть за адресою [http://localhost:8080/people](http://localhost:8080/people) (за умови, що у вас
   налаштований порт за замовчуванням 8080).