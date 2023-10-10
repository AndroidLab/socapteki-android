package ru.apteka.social

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.common.data.services.error_notice_service.ErrorNoticeService
import ru.apteka.common.data.services.error_notice_service.models.IRequestError
import ru.apteka.common.data.services.message_notice_service.MessageNoticeService
import ru.apteka.common.data.services.message_notice_service.models.CommonDialogModel
import ru.apteka.common.data.services.message_notice_service.models.DialogModel
import ru.apteka.common.data.services.message_notice_service.showCommonDialog
import ru.apteka.common.data.utils.launchIO
import ru.apteka.common.ui.CommonDialogFragment
import ru.apteka.components.data.navigation_manager.NavigationManager
import ru.apteka.social.databinding.ActivityMainBinding
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: NavigationManager

    @Inject
    lateinit var errorNoticeService: ErrorNoticeService

    @Inject
    lateinit var messageNoticeService: MessageNoticeService


    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root
        navigationManager.generalNavController = findNavController(R.id.general_nav_host)

        /*GlobalScope.launch {
            delay(15000)
            withContext(Dispatchers.Main) {
                navigationManager.generalNavController!!.navigate(
                    MainFragmentDirections.toSecondFragment()
                )
            }
        }*/
        lifecycleScope.launchIO {
            errorNoticeService.error.collect {
                showCommonDialog(
                    CommonDialogModel(
                        fragmentManager = supportFragmentManager,
                        dialogModel = DialogModel(
                            title = it.title,
                            message = CommonDialogFragment.CommonDialogMessage(
                                message = when(it) {
                                    is IRequestError.RequestErrorResMsg -> it.msg
                                    is IRequestError.RequestErrorStringMsg -> it.msg
                                }
                            )
                        )
                    )
                )
            }
        }

        lifecycleScope.launchIO {
            messageNoticeService.commonDialog.collect { dialogModel ->
                if (dialogModel != null) {
                    showCommonDialog(
                        CommonDialogModel(
                            fragmentManager = supportFragmentManager,
                            dialogModel = dialogModel
                        )
                    )
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        navigationManager.isBottomNavigationBarNeedUpdateSingleEvent.call()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationManager.currentBottomNavControllerLiveData.value?.navigateUp() ?: false
    }

    /**
     * Переопределение popBackStack необходимо в этом случае, если приложение запускается по глубокой ссылке.
     */
    override fun onBackPressed() {
        if (navigationManager.currentBottomNavControllerLiveData.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
}