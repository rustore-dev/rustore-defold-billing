## RuStore Defold плагин для оценок и отзывов

### [🔗 Документация разработчика](https://www.rustore.ru/help/sdk/payments)

- [Условия работы SDK](#Условия-работы-SDK)
- [Подготовка требуемых параметров](#Подготовка-требуемых-параметров)
- [Настройка примера приложения](#Настройка-примера-приложения)
- [Сценарий использования](#Сценарий-использования)
- [Условия распространения](#Условия-распространения)
- [Техническая поддержка](#Техническая-поддержка)


### Условия работы SDK

Для работы SDK оценок и отзывов необходимо соблюдение следующих условий:

1. На устройстве пользователя установлено приложение RuStore.

2. Пользователь авторизован в приложении RuStore.

3. Пользователь и приложение не должны быть заблокированы в RuStore.

4. Для приложения включена возможность покупок в системе [RuStore Консоль](https://console.rustore.ru/).

> ⚠️ Сервис имеет ограничения на работу за пределами РФ.


### Подготовка требуемых параметров

1. `applicationId` - уникальный идентификатор приложения в системе Android в формате обратного доменного имени (пример: ru.rustore.sdk.example).

2. `*.keystore` - файл ключа, который используется для [подписи и аутентификации Android приложения](https://www.rustore.ru/help/developers/publishing-and-verifying-apps/app-publication/apk-signature/).


### Настройка примера приложения

1. Выполните шаги раздела [“Сборка плагина”](../README.md). Собранные файлы (.jar) будут автоматически скопированы в проект-пример.

2. Откройте проект _“game.project”_ в папке _“billing_example”_.

3. В файле _“game.project”_ в разделе "Android" укажите значение `applicationId` в поле "Package".

4. В меню "Bundle Application" (Project → Bundle → Android Application...) выполните установку значений полей "Keystore", "Keystore Password", "Key Password", указав расположение и параметры ранее подготовленного файла `*.keystore`.

5. Выполните сборку проекта командой “Create Bundle...” (Project → Bundle → Android Application... → Create Bundle...) и проверьте работу приложения.


### Настройка примера приложения

Для проверки работы приложения вы можете воспользоваться функционалом [тестовых платежей](https://www.rustore.ru/help/developers/monetization/sandbox).

1. Выполните шаги раздела [“Сборка плагина”](../README.md). Собранные файлы (.jar) будут автоматически скопированы в проект-пример.

2. Откройте проект _“game.project”_ из папки _“billing_example”_.

3. В файле _“billing_example / main / main.script”_ в параметре "APPLICATION_ID" укажите код приложения из консоли разработчика RuStore. Пример: адрес страницы приложения https://console.rustore.ru/apps/123456, код приложения - 123456.

4. В файле _“billing_example / main / main.script”_ в параметре "DEEPLINK_SCHEME" укажите URL-адрес для использования deeplink. В качестве названия может быть использовано любое уникальное имя. Пример: example.

5. В файле _“billing_example / main / main.script”_ в параметре "PRODUCT_IDS" перечислите [подписки](https://www.rustore.ru/help/developers/monetization/create-app-subscription/) и [разовые покупки](https://www.rustore.ru/help/developers/monetization/create-paid-product-in-application/) доступные в вашем приложении.

6. В файле _“billing_example / extension_rustore_billing / manifests / android / AndroidManifest.xml”_ в параметре "data android:scheme" укажите URL-адрес для использования deeplink (должен совпадать с параметром DEEPLINK_SCHEME из пункта 3).

7. В меню "Bundle Application" (Project → Bundle → Android Application...) выполните настройку параметров "Keystore", "Keystore Password", "Key Password".

8. Выполните сборку проекта командой “Create Bundle...” (Project → Bundle → Android Application... → Create Bundle...) и проверьте работу приложения.


### Сценарий использования

#### Проверка доступности работы с платежами

Начальный экран приложения не содержит загруженных данных и уведомлений. Тап по кнопке `Availability` выполняет [проверку доступности платежей](https://www.rustore.ru/help/sdk/payments/checkpurchasesavailability).

![Проверка доступности платежей](images/02_check_purchases_availability.png)


#### Получение списка продуктов

Тап по кнопке `Products` выполняет получение и отображение [списка продуктов](https://www.rustore.ru/help/sdk/payments/getproducts).

![Получение списка продуктов](images/03_update_products_list.png)


#### Покупка продукта

Тап по кнопке `Buy` выполняет запуск сценария [покупки продукта](https://www.rustore.ru/help/sdk/payments/purchaseproduct) с отображением шторки выбора метода оплаты.

![Покупка продукта](images/04_purchase.png)


### Условия распространения

Данное программное обеспечение, включая исходные коды, бинарные библиотеки и другие файлы распространяется под лицензией MIT. Информация о лицензировании доступна в документе [MIT-LICENSE](../MIT-LICENSE.txt).


### Техническая поддержка

Дополнительная помощь и инструкции доступны на странице [rustore.ru/help/](https://www.rustore.ru/help/) и по электронной почте [support@rustore.ru](mailto:support@rustore.ru).
