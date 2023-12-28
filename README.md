## RuStore Defold плагин для приема платежей через сторонние приложения

### [🔗 Документация разработчика](https://www.rustore.ru/help/sdk/payments)

Плагин “RuStoreDefoldBilling” помогает интегрировать в ваш проект механизм оплаты через сторонние приложения (например, SberPay или СБП).

Репозиторий содержит плагины “RuStoreDefoldBilling” и “RuStoreDefoldCore”, а также демонстрационное приложение с примерами использования и настроек. Поддерживаются версии Defold 1.6.2+.

### Сборка плагина

1. Откройте в вашей IDE проект Android из папки _“extension_libraries”_.

2. Выполните сборку проекта командой gradle assemble.

При успешном выполнении сборки в папках _“billing_example / extension_rustore_billing / lib / android”_ и _“billing_example / extension_rustore_core / lib / android”_ будут созданы файлы:
- RuStoreDefoldBilling.jar
- RuStoreDefoldCore.jar


### Установка плагина в свой проект

1. Выполните шаги раздела “Сборка плагина”.

2. Скопируйте папки _“billing_example / extension_rustore_billing”_ и _“billing_example / extension_rustore_core”_ в корень вашего проекта.


### Настройка примера приложения

Для проверки работы приложения вы можете воспользоваться функционалом [тестовых платежей](https://www.rustore.ru/help/developers/monetization/sandbox).

1. В файле _“billing_example / main / main.script”_ в параметре "APPLICATION_ID" укажите код приложения из консоли разработчика RuStore. Пример: адрес страницы приложения https://console.rustore.ru/apps/123456, код приложения - 123456.

2. В файле _“billing_example / main / main.script”_ в параметре "DEEPLINK_SCHEME" укажите URL-адрес для использования deeplink. В качестве названия может быть использовано любое уникальное имя. Пример: example.

3. В файле _“billing_example / main / main.script”_ в параметре "PRODUCT_IDS" перечислите [подписки](https://www.rustore.ru/help/developers/monetization/create-app-subscription/) и [разовые покупки](https://www.rustore.ru/help/developers/monetization/create-paid-product-in-application/) доступные в вашем приложении.

4. В файле _“billing_example / extension_rustore_billing / manifests / android / AndroidManifest.xml”_ в параметре "data android:scheme" укажите URL-адрес для использования deeplink (должен совпадать с параметром DEEPLINK_SCHEME из пункта 2).


### Сборка примера приложения

1. Выполните шаги раздела “Сборка плагина”. Собранные файлы (.jar) будут автоматически скопированы в проект-пример.

2. Откройте проект _“game.project”_ из папки _“billing_example”_.

3. В меню "Bundle Application" (Project → Bundle → Android Application...) выполните настройку параметров "Keystore", "Keystore Password", "Key Password".

4. Выполните сборку проекта командой “Create Bundle...” (Project → Bundle → Android Application... → Create Bundle...) и проверьте работу приложения.


### Техническая поддержка

Дополнительная помощь и инструкции доступны на странице [help.rustore.ru](https://help.rustore.ru/).
