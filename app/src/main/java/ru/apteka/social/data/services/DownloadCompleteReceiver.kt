package ru.apteka.social.data.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import ru.apteka.components.data.utils.event_dispatcher.impl.EventDispatcher
import ru.apteka.components.data.utils.event_dispatcher.impl.call
import java.io.File

class DownloadCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        EventDispatcher.call(AppUpdateService.DOWNLOAD_UPDATE_COMPLETE)
        //val completeDownloadId = intent!!.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (intent?.action == "android.intent.action.DOWNLOAD_COMPLETE") {
            //clearAppDataBase()
            //clearPreferences()
            val fileUri = FileProvider.getUriForFile(
                context!!,
                context.applicationContext.packageName + ".provider",
                File(AppUpdateService.getUpdateDir(context), AppUpdateService.fileName)
            )
            Intent(Intent.ACTION_VIEW, fileUri).apply {
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                setDataAndType(fileUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }.also {
                context.startActivity(it)
            }
        }
    }

}