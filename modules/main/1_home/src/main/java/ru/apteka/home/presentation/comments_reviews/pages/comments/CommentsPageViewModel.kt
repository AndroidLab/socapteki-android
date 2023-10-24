package ru.apteka.home.presentation.comments_reviews.pages.comments

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.account.AccountsPreferences
import ru.apteka.components.data.services.navigation_manager.NavigationManager
import ru.apteka.main_common.ui.MainScreenBaseViewModel
import javax.inject.Inject


/**
 * Представляет модель представления "Страница комментариев".
 */
@HiltViewModel
class CommentsPageViewModel @Inject constructor(
    private val requestHandler: RequestHandler,
    navigationManager: NavigationManager,
    accountsPreferences: AccountsPreferences
) : MainScreenBaseViewModel(
    accountsPreferences,
    navigationManager
) {



    init {
        /*viewModelScope.launchIO {
            requestHandler.handleApiRequest(
                onRequest = { loginRepository.getSubscriptions() },
                onSuccess = { subscriptions ->
                    mainThread {

                    }
                },
                isLoading = _isLoading
            )
        }*/
    }




}