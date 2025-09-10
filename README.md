## RuStore Defold плагин для приема платежей через сторонние приложения

### [🔗 Документация разработчика][10]

Плагин “RuStoreDefoldBilling” помогает интегрировать в ваш проект механизм оплаты через сторонние приложения (например, SberPay или СБП).

Репозиторий содержит плагины “RuStoreDefoldBilling” и “RuStoreDefoldCore”, а также демонстрационное приложение с примерами использования и настроек. Поддерживаются версии Defold 1.6.2+.


### Сборка примера приложения

Вы можете ознакомиться с демонстрационным приложением содержащим представление работы всех методов sdk:
- [README](billing_example/README.md)
- [billing_example](https://gitflic.ru/project/rustore/rustore-defold-billing/file?file=billing_example)


### Установка плагина в свой проект

1. Скопируйте папки _“billing_example / extension_rustore_billing”_ и _“billing_example / extension_rustore_core”_ в корень вашего проекта.

2. В “game.project” в разделе “Android” в поле “Manifest” укажите путь к файлу “ExtendedAndroidManifest.xml” в папке _“extension_rustore_billing / manifests / android”_

### Пересборка плагина

Если вам необходимо изменить код библиотек плагинов, вы можете внести изменения и пересобрать подключаемые .jar файлы.

1. Откройте в вашей IDE проект Android из папки _“extension_libraries”_.

2. Выполните сборку проекта командой gradle assemble.

При успешном выполнении сборки в папках _“billing_example / extension_rustore_billing / lib / android”_ и _“billing_example / extension_rustore_core / lib / android”_ будут обновлены файлы:
- RuStoreDefoldBilling.jar
- RuStoreDefoldCore.jar


### История изменений

[CHANGELOG](CHANGELOG.md)


### Условия распространения

Данное программное обеспечение, включая исходные коды, бинарные библиотеки и другие файлы распространяется под лицензией MIT. Информация о лицензировании доступна в документе [MIT-LICENSE](MIT-LICENSE.txt).


### Техническая поддержка

Дополнительная помощь и инструкции доступны на странице [rustore.ru/help/](https://www.rustore.ru/help/) и по электронной почте [support@rustore.ru](mailto:support@rustore.ru).

[10]: https://www.rustore.ru/help/sdk/payments/defold/9-1-0
