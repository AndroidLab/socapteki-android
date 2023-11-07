package ru.apteka.social.presentation.scan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import ru.apteka.social.databinding.ActivityScanBinding


class ScanActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private val mScannerView: ZBarScannerView by lazy {
        binding.zbar
    }

    private val binding: ActivityScanBinding by lazy {
        ActivityScanBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(binding.root)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        setResult(
            RESULT_OK,
            Intent().putExtra(BARCODE_SCAN, rawResult?.contents)
        )
        finish()
        mScannerView.resumeCameraPreview(this)
    }

    companion object {
        const val BARCODE_SCAN = "barcodeScan"
    }

}