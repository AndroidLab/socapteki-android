package ru.apteka.components.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import ru.apteka.components.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.IErrorNoticeService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.INavigationManager
import ru.apteka.components.data.services.navigation_manager.NavigationManager

@Module
@InstallIn(SingletonComponent::class)
interface ComponentsModule {

    @Binds
    fun bindsNavigationManager(
        navigationManager: NavigationManager,
    ): INavigationManager

    @Binds
    fun bindsErrorNoticeService(
        errorNoticeService: ErrorNoticeService,
    ): IErrorNoticeService

    @Binds
    fun bindsMessageNoticeService(
        messageNoticeService: MessageNoticeService,
    ): IMessageNoticeService

}
