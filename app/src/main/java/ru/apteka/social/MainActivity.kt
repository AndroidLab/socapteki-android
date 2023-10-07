package ru.apteka.social

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.social.databinding.ActivityMainBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MaskFormatWatcher(
            MaskImpl(
                Slot.copySlotArray(PredefinedSlots.RUS_PHONE_NUMBER).apply {
                    this[3].flags = this[3].flags or Slot.RULE_FORBID_CURSOR_MOVE_LEFT
                },
                true
            )
        ).installOnAndFill(binding.myEditText)

        MaskFormatWatcher(
            MaskImpl.createTerminated(
                UnderscoreDigitSlotsParser().parseSlots("_ _ _ _")
            )
        ).installOn(binding.codeEditText)

        binding.btnPinCodeSuccess.setOnClickListener {
            binding.pinCodeView.setSuccess(true)
        }

        binding.btnPinCodeError.setOnClickListener {
            binding.pinCodeView.setError(true)
        }

    }
}