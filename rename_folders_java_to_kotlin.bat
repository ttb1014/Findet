@echo off
setlocal enabledelayedexpansion

REM скрипт рекурсивно переименовывает все java каталоги в kotlin.
REM Поведение не определено, если есть и java и kotlin, поэтому осторожно)
echo Ищу и переименовываю папки "java" в "kotlin"...

for /f "delims=" %%d in ('dir /ad /b /s ^| sort /R') do (

    REM Получаем имя папки без пути
    for %%i in ("%%~nd") do (
        if /I "%%~nxi"=="java" (
            set "old=%%d"
            set "new=%%~dpd\kotlin"
            ren "!old!" kotlin
            echo Переименовано: !old!
        )
    )
)

echo.
echo Готово. Нажмите любую клавишу для выхода...
pause
