package ru.rustore.defold.billing.model

data class PurchaseProductParams(
    val productId: String,
    val orderId: String?,
    val quantity: Int?,
    val payload: String?,
)
