package ru.apteka.contacts.presentation

import android.content.Intent
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.RawValue
import ru.apteka.components.data.services.message_notice_service.models.BottomSheetModel
import ru.apteka.components.data.services.message_notice_service.models.CommonBottomSheetModel
import ru.apteka.components.data.services.message_notice_service.showBottomSheet
import ru.apteka.components.data.utils.NAVIGATE_REQUEST_KEY_TO_HOME
import ru.apteka.components.data.utils.navigateWithAnim
import ru.apteka.components.ui.BaseFragment
import ru.apteka.contacts.R
import ru.apteka.contacts.databinding.ContactsContactUsDialogBinding
import ru.apteka.contacts.databinding.ContactsFragmentBinding
import ru.apteka.feedback_api.api.FEEDBACK_REQUEST_KEY_SUCCESS
import ru.apteka.pharmacies_map_api.api.PHARMACIES_MAP_TYPE_INTERACTION
import ru.apteka.pharmacies_map_api.api.TypeInteraction


/**
 * Представляет фрагмент "Контакты".
 */
@AndroidEntryPoint
class ContactsFragment : BaseFragment<ContactsViewModel, ContactsFragmentBinding>() {
    override val viewModel: ContactsViewModel by viewModels()
    override val layoutId: Int = R.layout.contacts_fragment

    override fun onViewBindingInflated(binding: ContactsFragmentBinding) {
        setFragmentResultListener(FEEDBACK_REQUEST_KEY_SUCCESS) { _, _ ->
            setFragmentResult(NAVIGATE_REQUEST_KEY_TO_HOME, bundleOf())
            viewModel.navigationManager.generalNavController.popBackStack()
        }
        binding.viewModel = viewModel

        binding.contactsCharity.setOnClickListener {

        }

        binding.contactsPharmacyMap.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.pharmacies_map_api.R.id.pharmacies_map_graph, bundleOf(
                    PHARMACIES_MAP_TYPE_INTERACTION to TypeInteraction.NAVIGATION
                )
            )
        }

        binding.contactsAddress.setOnClickListener {

        }

        binding.llContactsReception.setOnClickListener {
            call(binding.tvContactsReception.text.toString())
        }
        binding.llContactsDrugReference.setOnClickListener {
            call(binding.tvContactsDrugReference.text.toString())
        }

        binding.contactsWriteToUsCommonQuestion.item.setOnClickListener {
            mail("assist@farmp.ru")
        }

        binding.contactsWriteToUsWorkInCompany.item.setOnClickListener {
            mail("personal@social-apteka.ru")
        }

        binding.contactsWriteToUsClaims.item.setOnClickListener {
            mail("info@social-apteka.ru")
        }

        binding.contactsContactUs.setOnClickListener {
            showBottomSheet(
                commonBottomSheet = CommonBottomSheetModel(
                    fragmentManager = parentFragmentManager,
                    bottomSheetModel = BottomSheetModel(
                        layoutId = R.layout.contacts_contact_us_dialog,
                        onLayoutInflate = { binding, dialog, behavior ->
                            binding as ContactsContactUsDialogBinding
                            binding.contactsContactUsCancel.setOnClickListener {
                                dialog.dismiss()
                            }
                            binding.contactsContactUsHelpDeskItem.setOnClickListener {
                                call(binding.tvContactsContactUsHelpDeskPhone.text.toString())
                            }
                            binding.contactsContactUsOnlinePharmacyItem.setOnClickListener {
                                call(binding.tvContactsContactUsOnlinePharmacyPhone.text.toString())
                            }
                            binding.contactsContactUsTelegramItem.setOnClickListener {
                                openSocNetwork("https://t.me/socialaptekaru")
                            }
                            binding.contactsContactUsVKItem.setOnClickListener {
                                openSocNetwork("https://vk.com/socialapteka")
                            }
                            binding.contactsContactUsOKItem.setOnClickListener {
                                openSocNetwork("https://ok.ru/socialapteka")
                            }
                        }
                    )
                )
            )
        }

        binding.mbContacts.setOnClickListener {
            viewModel.navigationManager.generalNavController.navigateWithAnim(
                ru.apteka.feedback_api.R.id.feedback_graph
            )
        }
    }

    private fun call(number: String) {
        startActivity(
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        )
    }

    private fun mail(mail: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$mail")).apply {
            putExtra(Intent.EXTRA_SUBJECT, "")
            putExtra(Intent.EXTRA_TEXT, "")
        }
        startActivity(Intent.createChooser(emailIntent, "Chooser"))
    }

    private fun openSocNetwork(network: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(network)
            )
        )
    }

    override fun onResume() {
        super.onResume()
        binding.contactsToolbar.apply {
            tvToolbarTitle.text = getString(R.string.contacts_title)
        }
    }
}