<!-- ── Language switch (EN active) ──────────────────────────────────── -->
<div align="left" style="margin:0 0 14px 0;">

  <span style="display:inline-block;
               padding:.28rem .6rem;
               border:1px solid rgba(0,0,0,.18);
               border-radius:10px 0 0 10px;
               font-weight:400;
               font-size:12px;
               letter-spacing:.06em;
               color:#111827;
               background:linear-gradient(180deg,#ffffff,#e9edf2);
               box-shadow:0 1px 0 rgba(0,0,0,.06);">
    [RU][ru]
  </span><span style="display:inline-block;
               margin-left:-1px;
               padding:.28rem .6rem;
               border:1px solid rgba(0,0,0,.14);
               border-radius:0 10px 10px 0;
               font-weight:400;
               font-size:12px;
               letter-spacing:.06em;
               background:linear-gradient(180deg,#f3f4f6,#ffffff);
               box-shadow:inset 0 2px 6px rgba(0,0,0,.10);">
    EN
  </span>

</div>
<!-- ────────────────────────────────────────────────────────────────── -->

## Changelog

### Release 10.3.1
- SDK billing version 10.3.1.


### Release 10.3.0
- SDK billing version 10.3.0.
- Added global object `RuStoreBillingEnums` — contains sets of enumerations for parameter typing:
  - `ProductStatus` — product status,
  - `ProductType` — product type,
  - `PurchaseState` — purchase state,
  - `Theme` — color theme of the payment sheet.


### Release 10.2.0
- SDK billing version 10.2.0.
- The package has been tested and is supported on Defold 1.9.2+.


### Release 10.1.0
- SDK billing version 10.1.0.
- Deprecated method `set_error_handling` has been removed.


### Release 9.1.1
- SDK billing version 9.1.0.
- Updated class `RuStoreIntentFilterActivity` and its declaration rules in `AndroidManifest.xml` for correct deeplink handling.


### Release 9.1.0
- SDK billing version 9.1.0.
- Added method to check user authorization status in RuStore — `get_authorization_status`.
- Method `check_purchases_availability` is marked as deprecated.
- Method `set_error_handling` is marked as deprecated.


### Release 6.1.0
- SDK billing version 6.1.0.


### Release 6.0.0
- SDK billing version 6.0.0.


### Release 5.1.1
- SDK billing version 5.1.1.


### Release 5.0.1
- SDK billing version 5.0.1.
- Fixed a bug with missing image links in product descriptions.


### Release 5.0
- SDK billing version 5.0.0.

[ru]: CHANGELOG.md
[en]: CHANGELOG.en.md
