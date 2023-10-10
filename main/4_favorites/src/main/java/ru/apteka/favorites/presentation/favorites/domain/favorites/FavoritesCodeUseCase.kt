package ru.apteka.favorites.presentation.favorites.domain.favorites

import dagger.hilt.android.scopes.ViewModelScoped
import ru.apteka.favorites.presentation.favorites.data.repository.favorites.FavoritesRepository
import javax.inject.Inject


/**
 * Представляет действие получения избранного.
 * @param favoritesRepository Репозиторий избранного.
 */
@ViewModelScoped
class FavoritesCodeUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend fun execute() = favoritesRepository.getFavorites()
}