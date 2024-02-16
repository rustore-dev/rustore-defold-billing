package ru.rustore.defold.billing

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import ru.rustore.defold.billing.model.PurchaseProductParams
import ru.rustore.defold.core.RuStoreCore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult

object RuStoreBilling {
    const val CANCELLED = "cancelled"
    const val SUCCESS = "success"
    const val FAILURE = "failure"

    const val CHANNEL_CHECK_PURCHASES_AVAILABLE_SUCCESS = "rustore_check_purchases_available_success"
    const val CHANNEL_CHECK_PURCHASES_AVAILABLE_FAILURE = "rustore_check_purchases_available_failure"
    const val CHANNEL_ON_GET_PRODUCTS_SUCCESS = "rustore_on_get_products_success"
    const val CHANNEL_ON_GET_PRODUCTS_FAILURE = "rustore_on_get_products_failure"
    const val CHANNEL_ON_PURCHASE_PRODUCT_SUCCESS = "rustore_on_purchase_product_success"
    const val CHANNEL_ON_PURCHASE_PRODUCT_FAILURE = "rustore_on_purchase_product_failure"
    const val CHANNEL_ON_GET_PURCHASES_SUCCESS = "rustore_on_get_purchases_success"
    const val CHANNEL_ON_GET_PURCHASES_FAILURE = "rustore_on_get_purchases_failure"
    const val CHANNEL_ON_CONFIRM_PURCHASE_SUCCESS = "rustore_on_confirm_purchase_success"
    const val CHANNEL_ON_CONFIRM_PURCHASE_FAILURE = "rustore_on_confirm_purchase_failure"
    const val CHANNEL_ON_DELETE_PURCHASE_SUCCESS = "rustore_on_delete_purchase_success"
    const val CHANNEL_ON_DELETE_PURCHASE_FAILURE = "rustore_on_delete_purchase_failure"
    const val CHANNEL_ON_GET_PURCHASE_INFO_SUCCESS = "rustore_on_get_purchase_info_success"
    const val CHANNEL_ON_GET_PURCHASE_INFO_FAILURE = "rustore_on_get_purchase_info_failure"

    private var client: RuStoreBillingClient? = null
    private val gson = Gson()
    private var isInitialized = false

    @JvmStatic
    fun getBillingClient(): RuStoreBillingClient? {
        return client
    }

    @JvmStatic
    fun init(activity: Activity, id: String, scheme: String) {
        client = RuStoreBillingClientFactory.create(
            context = activity.application,
            consoleApplicationId = id,
            deeplinkScheme = scheme,
            internalConfig = mapOf(
                "type" to "defold"
            ),
            themeProvider = RuStoreBillingClientThemeProviderImpl
        )

        if (!isInitialized) {
            RuStoreIntentFilterActivity.setGameActivityClass(activity.javaClass)
            val newIntent = Intent(activity, RuStoreIntentFilterActivity::class.java)
            activity.startActivity(newIntent)
        }

        isInitialized = true
    }

    @JvmStatic
    fun checkPurchasesAvailability() {
        client?.run {
            purchases.checkPurchasesAvailability()
                .addOnSuccessListener { result ->
                    when (result) {
                        is FeatureAvailabilityResult.Available -> {
                            RuStoreCore.emitSignal(
                                CHANNEL_CHECK_PURCHASES_AVAILABLE_SUCCESS,
                                "{\"isAvailable\": true, \"detailMessage\": \"\"}"
                            )
                        }
                        is FeatureAvailabilityResult.Unavailable -> {
                            RuStoreCore.emitSignal(
                                CHANNEL_CHECK_PURCHASES_AVAILABLE_SUCCESS,
                                "{\"isAvailable\": false, \"detailMessage\": \"${result.cause.message}\"}"
                            )
                        }
                    }
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(CHANNEL_CHECK_PURCHASES_AVAILABLE_FAILURE, gson.toJson(throwable))
                }
        }
    }

    @JvmStatic
    fun getProducts(productIds: Array<String>) {
        client?.run {
            products.getProducts(productIds.asList())
                .addOnSuccessListener { result ->
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PRODUCTS_SUCCESS, gson.toJson(result))
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PRODUCTS_FAILURE, gson.toJson(throwable))
                }
            }
    }

    @JvmStatic
    fun purchaseProduct(jsonParams: String) {
        val params = gson.fromJson(jsonParams, PurchaseProductParams::class.java)

        client?.run {
            purchases.purchaseProduct(
                productId = params.productId,
                orderId = params.orderId,
                quantity = params.quantity,
                developerPayload = params.payload
            )
                .addOnSuccessListener { result ->
                    val json = """{"type":"${result.javaClass.simpleName}","data":${gson.toJson(result)}}"""
                    RuStoreCore.emitSignal(CHANNEL_ON_PURCHASE_PRODUCT_SUCCESS, json)
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_PURCHASE_PRODUCT_FAILURE,
                        "{\"productId\": \"${params.productId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
        }
    }

    @JvmStatic
    fun getPurchases() {
        client?.run {
            purchases.getPurchases()
                .addOnSuccessListener { result ->
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASES_SUCCESS, gson.toJson(result))
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASES_FAILURE, gson.toJson(throwable))
                }
        }
    }

    @JvmStatic
    fun confirmPurchase(purchaseId: String) {
        client?.run {
            purchases.confirmPurchase(
                purchaseId = purchaseId
            )
                .addOnSuccessListener {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_CONFIRM_PURCHASE_SUCCESS,
                        "{\"purchaseId\": \"${purchaseId}\"}"
                    )
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_CONFIRM_PURCHASE_FAILURE,
                        "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
        }
    }

    @JvmStatic
    fun deletePurchase(purchaseId: String) {
        client?.run {
            purchases.deletePurchase(
                purchaseId = purchaseId
            )
                .addOnSuccessListener {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_DELETE_PURCHASE_SUCCESS,
                        "{\"purchaseId\": \"${purchaseId}\"}"
                    )
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_DELETE_PURCHASE_FAILURE,
                        "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
        }
    }

    @JvmStatic
    fun getPurchaseInfo(purchaseId: String) {
        client?.run {
            purchases.getPurchaseInfo(purchaseId)
                .addOnSuccessListener { result ->
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASE_INFO_SUCCESS, gson.toJson(result))
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_GET_PURCHASE_INFO_FAILURE,
                        "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
        }
    }

    @JvmStatic
    fun setTheme(themeCode: Int) {
        RuStoreBillingClientThemeProviderImpl.setTheme(themeCode)
    }

    @JvmStatic
    fun onNewIntent(intent: Intent) {
        if (!isInitialized) return
        client?.onNewIntent(intent)
    }
}
