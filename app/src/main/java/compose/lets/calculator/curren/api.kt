package compose.lets.calculator.curren

import compose.lets.calculator.curren.data.dattares
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface api {
    @GET("convert")
    @Headers("apikey:76a3DzBgrlrQSWYWnLKwhQCPyJr9B6ba")
    suspend fun getRates(@Query("to") to:String, @Query("from") from:String, @Query("amount") amount:String):Response<dattares>
}