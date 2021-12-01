package com.practice.websocket.data.model

import com.google.gson.annotations.SerializedName

data class ResponseIndodax(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("result")
	val result: Result? = null
)

data class Result(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("channel")
	val channel: String? = null
)

data class Data(

	@field:SerializedName("data")
	val data: List<List<String>>? = null,

	@field:SerializedName("offset")
	val offset: Int? = null
)
