package ru.apteka.components.data.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import ru.apteka.components.R
import java.util.regex.Pattern


/**
 * Валидирует емайл.
 */
fun validateEmail(email: String): Boolean {
    val VALID_EMAIL_ADDRESS_REGEX =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    return if (email.isEmpty()) true else VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches()
}

/**
 * Возвращает span персональных данных.
 */
fun getPersonalDataSpan(context: Context): SpannableString {
    val span = object : ClickableSpan() {
        override fun onClick(textView: View) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://social-apteka.ru/about/confidentiality/")
                )
            )
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
        }
    }
    return SpannableString(context.getString(R.string.consent_personal_data)).apply {
        setSpan(span, length - 29, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

