package ru.apteka.components.data.services.account


import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.utils.PreferencesDelegate
import ru.apteka.components.data.services.account.models.Account
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Представляет аккаунт сервис.
 * @param context Контекст приложения.
 */
@Singleton
class AccountsPreferences @Inject constructor(
    @ApplicationContext context: Context
) {

    companion object {
        private const val ACCOUNT = "account"
    }

    private val accountPref =
        context.getSharedPreferences(AccountsPreferences::class.java.simpleName, Context.MODE_PRIVATE)

    /**
     * Возвращет текущий аккаунт.
     */
    var account: Account? by PreferencesDelegate(
        accountPref,
        ACCOUNT,
        null, {
            it?.let { Gson().toJson(it) } ?: ""
        }, {
            if (it == "") null else Gson().fromJson(it, Account::class.java)
        }
    )

}