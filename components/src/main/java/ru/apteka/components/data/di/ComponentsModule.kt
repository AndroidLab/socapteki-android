package ru.apteka.components.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import ru.apteka.components.data.navigation_manager.INavigationManager
import ru.apteka.components.data.navigation_manager.NavigationManager

@Module
@InstallIn(SingletonComponent::class)
interface ComponentsModule {

    @Binds
    fun bindsNavigationManager(
        navigationManager: NavigationManager,
    ): INavigationManager

}
