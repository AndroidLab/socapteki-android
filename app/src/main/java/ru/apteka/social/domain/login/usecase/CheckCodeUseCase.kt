package ru.apteka.social.domain.login.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import ru.apteka.social.data.repository.LoginRepository
import javax.inject.Inject


/**
 * Представляет действие подтверждения кода.
 * @param loginRepository Репозиторий авторизации.
 */
@ViewModelScoped
class CheckCodeUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(code: String) = loginRepository.checkCode(code)
}