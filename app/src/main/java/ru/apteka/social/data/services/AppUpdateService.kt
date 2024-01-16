package ru.apteka.social.data.services

import android.app.DownloadManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.apteka.components.data.services.RequestHandler
import ru.apteka.components.data.services.message_notice_service.IMessageService
import ru.apteka.components.data.services.message_notice_service.models.BodyContentModel
import ru.apteka.components.data.services.message_notice_service.models.DialogModel
import ru.apteka.components.data.services.user.UserPreferences
import ru.apteka.components.data.utils.event_dispatcher.impl.EventDispatcher
import ru.apteka.components.data.utils.event_dispatcher.impl.subscribe
import ru.apteka.components.data.utils.subStringByRegex
import ru.apteka.social.BuildConfig
import ru.apteka.social.R
import ru.apteka.social.data.models.NewVersionFile
import ru.apteka.social.data.repository.IDocsApi
import ru.apteka.social.databinding.DialogNewVersionFileBinding
import java.io.File
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Представляет сервис проверки обновлений.
 */
@Singleton
class AppUpdateService @Inject constructor(
    private val docsApi: IDocsApi,
    private val requestHandler: RequestHandler,
    private val userPreferences: UserPreferences,
    private val messageService: IMessageService,
    @ApplicationContext val context: Context
) {
    companion object {
        const val DOWNLOAD_UPDATE_COMPLETE = "DOWNLOAD_UPDATE_COMPLETE"
        const val fileName = "update.apk"
        fun getUpdateDir(context: Context) = "${context.externalCacheDir!!.absolutePath}/"
    }

    /**
     * Возвращает или устанавливает идентификатор загрузки.
     */
    //var apkFileDownloadId = 0L


    private val _newVersionFile = MutableLiveData<NewVersionFile?>(null)

    /**
     * Возвращает файл новой версии.
     */
    val newVersionFile: LiveData<NewVersionFile?> = _newVersionFile

    /**
     * Возвращает флаг загрузки новой версии.
     */
    val isLoading = MutableLiveData(false)

    /**
     * Проверяет наличие обновления.
     */
    suspend fun checkUpdate() {
        requestHandler.handleApiRequest(
            onRequest = { docsApi.docsGet(ownerId = -114064265) },
            onSuccess = {
                val files = it.body()?.response?.items
                if (!files.isNullOrEmpty()) {
                    val file = files[0]
                    val regex = "\\d+\\.\\d"
                    val newVersion = file.title.subStringByRegex(regex)?.toFloat()
                    val currentVersion =
                        BuildConfig.VERSION_NAME.subStringByRegex(regex)!!.toFloat()
                    if (newVersion != null && newVersion > currentVersion) {
                        _newVersionFile.postValue(
                            NewVersionFile(
                                newVersion,
                                file
                            )
                        )
                        showNewAppVersionDialog()
                    }
                }
            },
        )
    }

    private fun showNewAppVersionDialog() {
        val lastVersionChecked = userPreferences.lastVersionChecked
        if (lastVersionChecked == -1f || newVersionFile.value!!.version > lastVersionChecked) {
            messageService.showCommonDialog(
                dialogModel = DialogModel(
                    title = R.string.new_version_file_title,
                    bodyContent = BodyContentModel(
                        layoutId = R.layout.dialog_new_version_file,
                    ) { dialog, binding ->
                        binding as DialogNewVersionFileBinding
                        binding.text = String.format(
                            context.getString(
                                R.string.new_version_file_message
                            ),
                            newVersionFile.value!!.version,
                            String.format(
                                Locale.US,
                                "%.2f",
                                newVersionFile.value!!.doc.size / 1024.0 / 1024.0
                            )
                        )
                        binding.isLoading = isLoading
                        binding.newVersionFileUpdateBtn.setOnClickListener {
                            updateApp()
                            dialog.dismiss()
                        }
                    },
                    onDismiss = {
                        userPreferences.lastVersionChecked =
                            newVersionFile.value!!.version
                    }
                )
            )
        }
    }

    /**
     * Выполняет обновление приложения.
     */
    fun updateApp(
        //onSuccess: () -> Unit = {},
        //clearAppDataBase: () -> Unit = {},
        //clearPreferences: () -> Unit = {}
    ) {
        /*ContextCompat.registerReceiver(
            context,
            downloadCompleteReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS
        )*/
        File(getUpdateDir(context), fileName).also {
            if (it.exists()) {
                it.delete()
            }
        }

        isLoading.postValue(true)

        val downloadManager = getSystemService(context, DownloadManager::class.java)

        /*apkFileDownloadId = */DownloadManager
            .Request(_newVersionFile.value!!.doc.url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(
                File(getUpdateDir(context), fileName).toUri()
            )
            .run {
                downloadManager!!.enqueue(this)
            }
    }

    init {
        EventDispatcher.subscribe<Unit>(DOWNLOAD_UPDATE_COMPLETE, {
            isLoading.postValue(false)
        })
    }


    /*private val downloadCompleteReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isLoading.postValue(false)
            Log.d("myL", "action " + intent!!.action)
            Toast.makeText(context, "Загружено", Toast.LENGTH_LONG).show()
            val completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (completeDownloadId == apkFileDownloadId) {
                //clearAppDataBase()
                //clearPreferences()
                val fileUri = FileProvider.getUriForFile(
                    context!!,
                    context.applicationContext.packageName + ".provider",
                    File(dir, fileName)
                )
                Intent(Intent.ACTION_VIEW, fileUri).apply {
                    putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                    setDataAndType(fileUri, "application/vnd.android.package-archive")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }.also {
                    context.startActivity(it)
                }

                //onSuccess()
                *//*statusBar.setProgress(100)
                statusText.setText("100%")*//*
                context.unregisterReceiver(this)
            }
        }
    }*/

}