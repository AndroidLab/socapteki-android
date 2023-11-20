package ru.apteka.components.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.apteka.components.data.services.message_notice_service.BottomSheetService
import ru.apteka.components.data.services.message_notice_service.IBottomSheetService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService

@Module
@InstallIn(SingletonComponent::class)
interface ComponentsModule {

    @Binds
    fun bindsMessageNoticeService(
        messageNoticeService: MessageNoticeService,
    ): IMessageNoticeService

    @Binds
    fun bindsBottomSheetService(
        bottomSheetService: BottomSheetService,
    ): IBottomSheetService

}
