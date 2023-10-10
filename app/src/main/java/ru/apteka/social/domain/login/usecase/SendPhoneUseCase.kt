package ru.apteka.social.domain.login.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import ru.apteka.social.data.repository.LoginRepository
import javax.inject.Inject


/**
 * Представляет действие отправки номера телефона.
 * @param loginRepository Репозиторий авторизации.
 */
@ViewModelScoped
class SendPhoneUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(phoneNumber: String) = loginRepository.sendNumber(phoneNumber)
}