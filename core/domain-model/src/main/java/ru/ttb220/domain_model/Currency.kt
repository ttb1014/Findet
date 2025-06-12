package ru.ttb220.domain_model

enum class Currency(
    val fullName: String,
    val symbol: Char,
) {
    EURO(
        "Евро",
        '€'
    ),
    DOLLAR(
        "Американский доллар",
        '$'
    ),
    RUBLE(
        "Российский рубль",
        '₽'
    ),
    POUND(
        "Фунт стерлингов",
        '£'
    ),
    YEN(
        "Японская иена",
        '¥'
    ),
    YUAN(
        "Китайский юань",
        '¥'
    ),
    FRANC(
        "Швейцарский франк",
        '₣'
    ),
    WON(
        "Южнокорейская вона",
        '₩'
    ),
    RUPEE(
        "Индийская рупия",
        '₹'
    );
}
