package ru.apteka.social.data.models

import androidx.annotation.Keep
import java.util.Locale

@Keep
data class Docs(
    val response: Response
) {
    @Keep
    data class Response(
        val count: Int,
        val items: List<Item>
    ) {
        @Keep
        data class Item(
            val date: Int,
            val ext: String,
            val id: Int,
            val is_unsafe: Int,
            val owner_id: Int,
            val size: Int,
            val title: String,
            val type: Int,
            val url: String
        ) {
            val formatSize
                get() = String.format(Locale.US, "%.2f", size / 1024.0 / 1024.0) + " МБ"
        }
    }
}