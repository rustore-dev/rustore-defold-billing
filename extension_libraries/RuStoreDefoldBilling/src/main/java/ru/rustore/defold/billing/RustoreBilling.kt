package ru.rustore.defold.billing

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import ru.rustore.defold.core.RuStoreCore
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.RuStoreBillingClientFactory
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.Purchase
import ru.rustore.sdk.core.feature.model.FeatureAvailabilityResult
import ru.rustore.sdk.core.tasks.OnCompleteListener


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

    /*override fun getPluginSignals(): Set<SignalInfo> {
        val signals: MutableSet<SignalInfo> = ArraySet()
        signals.add(SignalInfo(CHANNEL_CHECK_PURCHASES_AVAILABLE_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_CHECK_PURCHASES_AVAILABLE_FAILURE, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PRODUCTS_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PRODUCTS_FAILURE, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_PURCHASE_PRODUCT_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_PURCHASE_PRODUCT_FAILURE, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PURCHASES_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PURCHASES_FAILURE, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_CONFIRM_PURCHASE_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_CONFIRM_PURCHASE_FAILURE, String::class.java, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_DELETE_PURCHASE_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_DELETE_PURCHASE_FAILURE, String::class.java, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PURCHASE_INFO_SUCCESS, String::class.java))
        signals.add(SignalInfo(CHANNEL_ON_GET_PURCHASE_INFO_FAILURE, String::class.java, String::class.java))

        return signals
    }*/

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
                .addOnCompleteListener(object : OnCompleteListener<FeatureAvailabilityResult> {
                    override fun onSuccess(result: FeatureAvailabilityResult) {
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

                    override fun onFailure(throwable: Throwable) {
                        RuStoreCore.emitSignal(CHANNEL_CHECK_PURCHASES_AVAILABLE_FAILURE, gson.toJson(throwable))
                    }
                })
        }
    }

    @JvmStatic
    fun getProducts(productIds: Array<String>) {
        client?.run {
            products.getProducts(
                productIds = productIds.asList()
            ).addOnCompleteListener(object : OnCompleteListener<List<Product>> {
                override fun onSuccess(result: List<Product>) {
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PRODUCTS_SUCCESS, gson.toJson(result))
                }

                override fun onFailure(throwable: Throwable) {
                    RuStoreCore.emitSignal(CHANNEL_ON_GET_PRODUCTS_FAILURE, gson.toJson(throwable))
                }
            })
        }
    }

    @JvmStatic
    fun purchaseProduct(productId: String) {//}, params: Dictionary<String, String>) {
        //val orderId = params["order_id"]?.toString()
        //val quantity = params["quantity"] as? Int
        //val payload = params["payload"]?.toString()

        client?.run {
            purchases.purchaseProduct(
                productId = productId,
                orderId = null,//orderId,
                quantity = null,//quantity,
                developerPayload = null,//payload
            ).addOnCompleteListener(object : OnCompleteListener<PaymentResult> {
                override fun onSuccess(result: PaymentResult) {
                    val json = """{"type":"${result.javaClass.simpleName}","data":${gson.toJson(result)}}"""
                    RuStoreCore.emitSignal(CHANNEL_ON_PURCHASE_PRODUCT_SUCCESS, json)
                }

                override fun onFailure(throwable: Throwable) {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_PURCHASE_PRODUCT_FAILURE,
                        "{\"productId\": \"${productId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
            })
        }
    }

    @JvmStatic
    fun getPurchases() {
        client?.run {
            purchases.getPurchases()
                .addOnCompleteListener(object : OnCompleteListener<List<Purchase>> {
                    override fun onSuccess(result: List<Purchase>) {
                        RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASES_SUCCESS, gson.toJson(result))
                    }

                    override fun onFailure(throwable: Throwable) {
                        RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASES_FAILURE, gson.toJson(throwable))
                    }
                })
        }
    }

    @JvmStatic
    fun confirmPurchase(purchaseId: String) {
        client?.run {
            purchases.confirmPurchase(
                purchaseId = purchaseId
            ).addOnCompleteListener(object : OnCompleteListener<Unit> {
                override fun onSuccess(result: Unit) {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_CONFIRM_PURCHASE_SUCCESS,
                        "{\"purchaseId\": \"${purchaseId}\"}"
                    )
                }

                override fun onFailure(throwable: Throwable) {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_CONFIRM_PURCHASE_FAILURE,
                        "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
            })
        }
    }

    @JvmStatic
    fun deletePurchase(purchaseId: String) {
        Log.w("ssss", "Delete: $purchaseId")
        client?.run {
            purchases.deletePurchase(
                purchaseId = purchaseId
            ).addOnCompleteListener(object : OnCompleteListener<Unit> {
                override fun onSuccess(result: Unit) {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_DELETE_PURCHASE_SUCCESS,
                        "{\"purchaseId\": \"${purchaseId}\"}"
                    )
                }

                override fun onFailure(throwable: Throwable) {
                    RuStoreCore.emitSignal(
                        CHANNEL_ON_DELETE_PURCHASE_FAILURE,
                        "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                    )
                }
            })
        }
    }

    @JvmStatic
    fun getPurchaseInfo(purchaseId: String) {
        client?.run {
            purchases.getPurchaseInfo(purchaseId)
                .addOnCompleteListener(object : OnCompleteListener<Purchase> {
                    override fun onSuccess(result: Purchase) {
                        RuStoreCore.emitSignal(CHANNEL_ON_GET_PURCHASE_INFO_SUCCESS, gson.toJson(result))
                    }

                    override fun onFailure(throwable: Throwable) {
                        RuStoreCore.emitSignal(
                            CHANNEL_ON_GET_PURCHASE_INFO_FAILURE,
                            "{\"purchaseId\": \"${purchaseId}\", \"detailMessage\": \"${throwable.message}\"}"
                        )
                    }
                })
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
