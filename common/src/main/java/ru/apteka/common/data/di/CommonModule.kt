package ru.apteka.common.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.apteka.common.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.common.data.services.error_notice_service.IErrorNoticeService
import ru.apteka.common.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.common.data.services.message_notice_service.MessageNoticeService

@Module
@InstallIn(SingletonComponent::class)
interface CommonModule {

    @Binds
    fun bindsErrorNoticeService(
        errorNoticeService: ErrorNoticeService,
    ): IErrorNoticeService

    @Binds
    fun bindsMessageNoticeService(
        messageNoticeService: MessageNoticeService,
    ): IMessageNoticeService

}
