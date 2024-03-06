package ru.rustore.defold.billing.model

data class PurchaseProductParams(
    val orderId: String? = null,
    val quantity: Int? = null,
    val payload: String? = null,
)
