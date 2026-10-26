# Smmlaba (com.smmlaba) v1.3 — восстановленный проект

Android-приложение Smmlaba (Java, minSdk 28), восстановленное из установленного APK, т.к. исходники были утеряны.

## Как восстановлено
- Код декомпилирован из APK с помощью jadx 1.5.6. Поэтому в коде **нет оригинальных комментариев и имён локальных переменных**, местами стиль кода «машинный».
- В декомпилированный код внесены минимальные правки, чтобы он компилировался (неоднозначные ссылки `product.*`, лишний `break`, `throws` в `onClick`, тип ViewHolder в адаптере).
- Ресурсы (layout, drawable, strings, styles, menu, mipmap) и assets взяты из APK; ресурсы сторонних библиотек (AndroidX, Material, YooKassa и др.) удалены — они подтягиваются через Gradle.
- Зависимости (Gson, Picasso, Material, AppCompat, YooKassa SDK, AVLoadingIndicatorView, commons-io/codec) подобраны по импортам.

## Нативная библиотека
`app/src/main/jniLibs/*/libnative-lib.so` — **готовый бинарник**, извлечённый из APK. Исходник на C++ восстановить нельзя.

## Сборка
Нужны JDK 17+ и Android SDK (platform 34). Укажите путь к SDK в `local.properties` (`sdk.dir=...`) или откройте проект в Android Studio.

    ./gradlew assembleDebug

APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Важно
Бэкенд приложения больше не работает, поэтому приложение собирается и запускается, но данные/оплата работать не будут. Проект предназначен для демонстрации кода.
