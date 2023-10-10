package ru.apteka.social.domain.login.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import ru.apteka.social.data.repository.LoginRepository
import javax.inject.Inject


/**
 * Представляет действие повторного получения кода.
 * @param loginRepository Репозиторий авторизации.
 */
@ViewModelScoped
class NewCodeUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(phoneNumber: String) = loginRepository.getNewCode(phoneNumber)
}