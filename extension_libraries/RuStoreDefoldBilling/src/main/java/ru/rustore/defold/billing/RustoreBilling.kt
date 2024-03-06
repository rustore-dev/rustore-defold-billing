package ru.rustore.defold.billing

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import ru.rustore.defold.billing.model.PurchaseProductParams
import ru.rustore.defold.core.JsonBuilder
import ru.rustore.defold.core.RuStoreCore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.provider.logger.ExternalPaymentLogger
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult

object RuStoreBilling : ExternalPaymentLogger {
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
    const val CHANNEL_ON_PAYMENT_LOGGER_DEBUG = "rustore_on_payment_logger_debug"
    const val CHANNEL_ON_PAYMENT_LOGGER_ERROR = "rustore_on_payment_logger_error"
    const val CHANNEL_ON_PAYMENT_LOGGER_INFO = "rustore_on_payment_logger_info"
    const val CHANNEL_ON_PAYMENT_LOGGER_VERBOSE = "rustore_on_payment_logger_verbose"
    const val CHANNEL_ON_PAYMENT_LOGGER_WARNING = "rustore_on_payment_logger_warning"

    private var client: RuStoreBillingClient? = null
    private val gson = Gson()
    private var tag = ""
    private var allowErrorHandling: Boolean = false
    private var isInitialized = false

    @JvmStatic
    fun setErrorHandling(allowErrorHandling: Boolean) {
        this.allowErrorHandling = allowErrorHandling
    }

    @JvmStatic
    fun getErrorHandling() : Boolean {
        return allowErrorHandling
    }

    private fun handleError(throwable: Throwable) {
        // TO DO
    }

    @JvmStatic
    fun getBillingClient(): RuStoreBillingClient? {
        return client
    }

    @JvmStatic
    fun init(activity: Activity, id: String, scheme: String, debugLogs: Boolean) {
        client = RuStoreBillingClientFactory.create(
            context = activity.application,
            consoleApplicationId = id,
            deeplinkScheme = scheme,
            internalConfig = mapOf(
                "type" to "defold"
            ),
            themeProvider = RuStoreBillingClientThemeProviderImpl,
            debugLogs = debugLogs,
            externalPaymentLoggerFactory = if (debugLogs) { tag -> this.tag = tag; this } else null
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
                                """{"isAvailable": true}"""
                            )
                        }
                        is FeatureAvailabilityResult.Unavailable -> {
                            val cause = JsonBuilder.toJson(result.cause)
                            val json = """{"isAvailable": false, "cause": $cause}"""
                            handleError(result.cause)
                            RuStoreCore.emitSignal(CHANNEL_CHECK_PURCHASES_AVAILABLE_SUCCESS, json)
                        }
                    }
                }
                .addOnFailureListener { throwable ->
                    RuStoreCore.emitSignal(CHANNEL_CHECK_PURCHASES_AVAILABLE_FAILURE, JsonBuilder.toJson(throwable))
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
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PRODUCTS_FAILURE, JsonBuilder.toJson(throwable))
                }
            }
    }

    @JvmStatic
    fun purchaseProduct(productId: String, jsonParams: String) {
        val params = gson.fromJson(jsonParams, PurchaseProductParams::class.java)

        client?.run {
            purchases.purchaseProduct(
                productId = productId,
                orderId = params.orderId,
                quantity = params.quantity,
                developerPayload = params.payload
            )
                .addOnSuccessListener { result ->
                    val json = """{"type":"${result.javaClass.simpleName}","data":${gson.toJson(result)}}"""
                    RuStoreCore.emitSignal(CHANNEL_ON_PURCHASE_PRODUCT_SUCCESS, json)
                }
                .addOnFailureListener { throwable ->
                    val cause = JsonBuilder.toJson(throwable)
                    val json = """{"productId": "$productId", "cause": $cause}"""
                    handleError(throwable)
                    RuStoreCore.emitSignal(CHANNEL_ON_PURCHASE_PRODUCT_FAILURE, json)
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
                    handleError(throwable)
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASES_FAILURE, JsonBuilder.toJson(throwable))
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
                    val cause = JsonBuilder.toJson(throwable)
                    val json = """{"purchaseId": "$purchaseId", "cause": $cause}"""
                    handleError(throwable)
                    RuStoreCore.emitSignal(CHANNEL_ON_CONFIRM_PURCHASE_FAILURE, json)
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
                    val cause = JsonBuilder.toJson(throwable)
                    val json = """{"purchaseId": "$purchaseId", "cause": $cause}"""
                    handleError(throwable)
                    RuStoreCore.emitSignal(CHANNEL_ON_DELETE_PURCHASE_FAILURE, json)
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
                    val cause = JsonBuilder.toJson(throwable)
                    val json = """{"purchaseId": "$purchaseId", "cause": $cause}"""
                    handleError(throwable)
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASE_INFO_FAILURE, json)
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

    override fun d(e: Throwable?, message: () -> String) {
        RuStoreCore.emitSignal(CHANNEL_ON_PAYMENT_LOGGER_DEBUG, SignalConverter.paymentLogger(e, message()))
    }

    override fun e(e: Throwable?, message: () -> String) {
        RuStoreCore.emitSignal(CHANNEL_ON_PAYMENT_LOGGER_ERROR, SignalConverter.paymentLogger(e, message()))
    }

    override fun i(e: Throwable?, message: () -> String) {
        RuStoreCore.emitSignal(CHANNEL_ON_PAYMENT_LOGGER_INFO, SignalConverter.paymentLogger(e, message()))
    }

    override fun v(e: Throwable?, message: () -> String) {
        RuStoreCore.emitSignal(CHANNEL_ON_PAYMENT_LOGGER_VERBOSE, SignalConverter.paymentLogger(e, message()))
    }

    override fun w(e: Throwable?, message: () -> String) {
        RuStoreCore.emitSignal(CHANNEL_ON_PAYMENT_LOGGER_WARNING, SignalConverter.paymentLogger(e, message()))
    }
}
