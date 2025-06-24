package me.ckn.music.net

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.JsonObject
import top.wangchenyan.common.model.CommonResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

/**
 * WhisperPlay Music Player
 *
 * Original: Created by wangchenyan.top on 2023/8/28
 * Modified: Modified for WhisperPlay by ckn on 2025-06-11
 *
 * 文件描述：网络工具类
 * File Description: Network utility class
 *
 * @author wangchenyan (original), ckn (modified)
 * @since 2025-06-11
 * @version 2.3.0
 */
object NetUtils {
    val CONTENT_TYPE_JSON = "application/json".toMediaType()

    fun Map<String, String>.toJsonBody(): RequestBody {
        return GsonUtils.toJson(this).toRequestBody(CONTENT_TYPE_JSON)
    }

    fun parseErrorResponse(
        exception: Throwable?,
        codeField: String = "code",
        msgField: String = "message"
    ): CommonResult<Unit> {
        var code = -1
        var msg = exception?.message
        if (exception is HttpException) {
            code = exception.code()
            msg = exception.message()
            val body = exception.response()?.errorBody()?.string().orEmpty()
            if (body.isNotEmpty()) {
                kotlin.runCatching {
                    val json = GsonUtils.fromJson(body, JsonObject::class.java)
                    if (json.has(codeField)) {
                        code = json.get(codeField).asInt
                    }
                    if (json.has(msgField)) {
                        msg = json.get(msgField).asString
                    }
                }
            }
        }
        return CommonResult.fail(code, msg)
    }
}