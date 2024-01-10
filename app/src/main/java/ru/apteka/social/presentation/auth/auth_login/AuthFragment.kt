package ru.apteka.social.presentation.auth.auth_login

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.models.PhoneInputModel.Companion.setPhoneMask
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.data.utils.setSoftInputModeResize
import ru.apteka.components.ui.BaseFragment
import ru.apteka.social.R
import ru.apteka.components.R as ComponentsR
import ru.apteka.social.databinding.AuthFragmentBinding
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Представляет фрагмент "Авторизация".
 */
@AndroidEntryPoint
class AuthFragment : BaseFragment<AuthViewModel, AuthFragmentBinding>() {
    override val viewModel: AuthViewModel by viewModels()
    override val layoutId: Int = R.layout.auth_fragment

    private val clickableSpanPrivacyPolicy = object : ClickableSpan() {
        override fun onClick(textView: View) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://social-apteka.ru/about/confidentiality/")
                )
            )
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
        }
    }

    private val clickableSpanAdvertNews = object : ClickableSpan() {
        override fun onClick(textView: View) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://social-apteka.ru/about/confidentiality/")
                )
            )
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
        }
    }

    override fun onViewBindingInflated(binding: AuthFragmentBinding) {
        binding.viewModel = viewModel

        binding.cbAuthPrivacyPolicy.apply {
            text = SpannableString(getString(R.string.auth_privacy_policy)).apply {
                setSpan(clickableSpanPrivacyPolicy, length - 88, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        binding.cbPersonalData.apply {
            text = SpannableString(getString(R.string.auth_advert_news)).apply {
                setSpan(clickableSpanAdvertNews, length - 63, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        binding.authConfirmPhone.setOnClickListener {
            findNavController().navigateWithAnim(
                AuthFragmentDirections.toAuthConfirmFragment(viewModel.phoneInput.getPhoneRaw())
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setSoftInputModeResize()
        binding.authToolbar.apply {
            toolbar.setNavigationIcon(ComponentsR.drawable.ic_navigation_back)
            tvToolbarTitle.text = getString(R.string.auth_title)
            toolbar.setNavigationOnClickListener {
                mActivity.finish()
            }
        }
    }

}