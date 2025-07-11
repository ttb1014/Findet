# Домашнее задание 5 — ШМР Android

## Новое
Миграция с Hilt на Dagger2
Экран добавления расходов/доходов
Bottom sheets для выбора аккаунта и категории (в форме создания транзакции)

## Запуск
Для запуска проекта нужно создать файл secrets.defaults.properties в корневой папке проекта и задать значения в двойныйх кавычках:
- BEARER_TOKEN
- BACKEND_URL

Также для тестирования экранов расходы/доходы/история можно изменить активный accountId в
mock-данных.

## Недоделки
Не реализовал доп. цели из второго задания:
- При возникновении ошибки в сценарии добавления или изменения расхода/дохода показывать пользователю кастомизированное уведомление о том, что что-то пошло не так и нужно повторить операцию 
- Также как вариант можно добавить поллинг на некоторое число повторений запроса, показывая при этом пользователю лоадер, пока не завершим операцию (полинг не должен заставлять пользователя долго ждать)

## Структура проекта
Проект разбит на несколько модулей.

## App
### 1. `:app`
- Основной модуль
- Реализует навигацию и объединяет фичи вместе
- Реализует логику BottomBar-а и TAB-а, т.к. они присутствуют на всех экранах. Реализация экранов
  переложена на фичи.
- **Новое**: Управляет состоянием BottomSheet и режимом редактирования TopAppBar

## Core
### 1. `:data`
- Содержит интерфейсы репозиториев. Реализации внедряются через DI.
- Реализует получение времени, мониторинг подключения к интернету и активного счёта для запросов.
- Активный счёт используется моковый, с заданным id = 54.

### 2. `:domain`
- Содержит юз-кейсы фич.
- **Изменено**: Некоторые UseCase'ы были перемещены в соответствующие feature модули

### 3. `:mock`
- Моковые данные 

### 4. `:model`
- Доменные модели для передачи информации между слоями (data-domain-presentation) и модулями

### 5. `:network`
- Реализует все запросы в сеть.
- Содержит публичный интерфейс со всеми доступными запросами. Реализация скрыта и внедряется через DI

### 6. `:presentation:model`
Содержит:
- Модели для UI
- Ресурсы приложения
- Мапперы из доменных моделей для валюты, доменных ошибок, эмодзи и денежных единиц

### 7. `:presentation:ui`
Содержит:
- Базовые компоненты UI.
- Темы, цвета, типографию

## Feature
Реализуют конкретные экраны, вложенную навигацию (где применимо).
Используют юз-кейсы