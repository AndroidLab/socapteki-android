package ru.apteka.components.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.apteka.components.data.services.barcode_scan.BarCodeScanService
import ru.apteka.components.data.services.barcode_scan.IBarCodeScanService
import ru.apteka.components.data.services.bottom_sheet_service.BottomSheetService
import ru.apteka.components.data.services.bottom_sheet_service.IBottomSheetService
import ru.apteka.components.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.components.data.services.error_notice_service.IErrorNoticeService
import ru.apteka.components.data.services.message_notice_service.IMessageNoticeService
import ru.apteka.components.data.services.message_notice_service.MessageNoticeService
import ru.apteka.components.data.services.navigation_manager.NavigationManager

@Module
@InstallIn(SingletonComponent::class)
interface ComponentsModule {

    @Binds
    fun bindsErrorNoticeService(
        errorNoticeService: ErrorNoticeService,
    ): IErrorNoticeService

    @Binds
    fun bindsMessageNoticeService(
        messageNoticeService: MessageNoticeService,
    ): IMessageNoticeService

    @Binds
    fun bindsBottomSheetService(
        bottomSheetService: BottomSheetService,
    ): IBottomSheetService

    @Binds
    fun bindsBarCodeScanService(
        barCodeScanService: BarCodeScanService,
    ): IBarCodeScanService

}
