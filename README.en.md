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
               background:linear-gradient(180deg,#e9edf2,#ffffff);
               box-shadow:inset 0 2px 6px rgba(0,0,0,.10);">
    EN
  </span>

</div>
<!-- ────────────────────────────────────────────────────────────────── -->

> ⚠️ Do not use the "Code → Download" button on the GitFlic website – this method does not download files from Git LFS. [Instructions for cloning](README_CLONE.en.md).

### <span style="color:red;">BillingClient SDK is marked as deprecated</span>

The **BillingClient SDK** continues to work, but resolving issues affecting payment functionality may take more time. New features are no longer being added to the SDK. We recommend using the Pay SDK in new and existing projects. To migrate to the Pay SDK, follow the [migration guide](https://www.rustore.ru/help/en/sdk/pay/migration).

## RuStore Defold plugin for payments via third-party apps

### [🔗 Developer documentation][10]

The “RuStoreDefoldBilling” plugin helps integrate payment processing through third-party applications (such as SberPay or SBP).

The repository includes the “RuStoreDefoldBilling” and “RuStoreDefoldCore” plugins, as well as a demo application with usage examples and configurations. Defold versions 1.9+ are supported.

### Building the example app

You can explore the demo application that demonstrates all SDK methods:
- [README](billing_example/README.en.md)
- [billing_example](https://gitflic.ru/project/rustore/rustore-defold-billing/file?file=billing_example)

### Installing the plugin into your project

1. Copy the folders _“billing_example / extension_rustore_billing”_ and _“billing_example / extension_rustore_core”_ to the root of your project.

2. In “game.project”, under the “Android” section, specify the path to the file “ExtendedAndroidManifest.xml” in the folder _“extension_rustore_billing / manifests / android”_.

### Rebuilding the plugin

If you need to modify the plugin library code, you can make changes and rebuild the included .jar files.

1. Open the Android project in your IDE from the _“extension_libraries”_ folder.

2. Build the project using the gradle assemble command.

Upon successful build, the following files will be updated in the folders _“billing_example / extension_rustore_billing / lib / android”_ and _“billing_example / extension_rustore_core / lib / android”_:
- RuStoreDefoldBilling.jar
- RuStoreDefoldCore.jar

### Change log

[CHANGELOG](CHANGELOG.en.md)

### Licensing Terms

This software, including source codes, binary libraries, and other files, is distributed under the MIT license. Licensing information is available in the [MIT-LICENSE](MIT-LICENSE.txt) document.

### Technical Support

Additional help and instructions are available at [rustore.ru/help/](https://www.rustore.ru/help/en/) and by email [support@rustore.ru](mailto:support@rustore.ru).

[10]: https://www.rustore.ru/help/en/sdk/payments/defold/10-3-1

[ru]: README.md
[en]: README.en.md
