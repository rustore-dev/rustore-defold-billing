<!-- ── Language switch (RU active) ──────────────────────────────────── -->
<div align="left" style="margin:0 0 14px 0;">

  <span style="display:inline-block;
               padding:.28rem .6rem;
               border:1px solid rgba(0,0,0,.18);
               border-radius:10px 0 0 10px;
               font-weight:400;
               font-size:12px;
               letter-spacing:.06em;
               color:#111827;
               background:linear-gradient(180deg,#e9edf2,#ffffff);
               box-shadow:inset 0 2px 6px rgba(0,0,0,.10);">
    RU
  </span><span style="display:inline-block;
               margin-left:-1px;
               padding:.28rem .6rem;
               border:1px solid rgba(0,0,0,.14);
               border-radius:0 10px 10px 0;
               font-weight:400;
               font-size:12px;
               letter-spacing:.06em;
               background:linear-gradient(180deg,#ffffff,#f3f4f6);
               box-shadow:0 1px 0 rgba(0,0,0,.06);">
    [EN][en]
  </span>

</div>
<!-- ────────────────────────────────────────────────────────────────── -->

## История изменений

### Release 10.3.1
- Версия SDK billing 10.3.1.


### Release 10.3.0
- Версия SDK billing 10.3.0.
- Добавлен глобальный объект `RuStoreBillingEnums` — содержит наборы перечислений для типизации параметров:
  - `ProductStatus` — статус продукта,
  - `ProductType` — тип продукта,
  - `PurchaseState` — состояние покупки,
  - `Theme` — цветовая тема платежной шторки.


### Release 10.2.0
- Версия SDK billing 10.2.0.
- Пакет протестирован и поддерживается на Defold 1.9.2+.


### Release 10.1.0
- Версия SDK billing 10.1.0.
- Удалён устаревший метод `set_error_handling`.


### Release 9.1.1
- Версия SDK billing 9.1.0.
- Обновлен класс `RuStoreIntentFilterActivity` и правила его объявления в `AndroidManifest.xml` для корректной обработки deeplink.


### Release 9.1.0
- Версия SDK billing 9.1.0.
- Добавлен метод проверки авторизации пользователя в RuStore — `get_authorization_status`.
- Метод `check_purchases_availability` помечен как устаревший.
- Метод `set_error_handling` помечен как устаревший.


### Release 6.1.0
- Версия SDK billing 6.1.0.


### Release 6.0.0
- Версия SDK billing 6.0.0.


### Release 5.1.1
- Версия SDK billing 5.1.1.


### Release 5.0.1
- Версия SDK billing 5.0.1.
- Исправлен баг с отсутствием ссылок на изображения в описании продуктов.


### Release 5.0
- Версия SDK billing 5.0.0.

[en]: CHANGELOG.en.md
