package com.practice.websocket.data.model

import com.google.gson.annotations.SerializedName

data class ResponseCoinbase(

	@field:SerializedName("side")
	val side: String? = null,

	@field:SerializedName("last_size")
	val lastSize: String? = null,

	@field:SerializedName("best_ask")
	val bestAsk: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("sequence")
	val sequence: Long? = null,

	@field:SerializedName("trade_id")
	val tradeId: Int? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("product_id")
	val productId: String? = null,

	@field:SerializedName("best_bid")
	val bestBid: String? = null,

	@field:SerializedName("volume_24h")
	val volume24h: String? = null,

	@field:SerializedName("low_24h")
	val low24h: String? = null,

	@field:SerializedName("high_24h")
	val high24h: String? = null,

	@field:SerializedName("volume_30d")
	val volume30d: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("open_24h")
	val open24h: String? = null
)
