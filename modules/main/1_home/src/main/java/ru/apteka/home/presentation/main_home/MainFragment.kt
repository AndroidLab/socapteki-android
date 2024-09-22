package ru.apteka.home.presentation.main_home

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.apteka.components.data.utils.dp
import ru.apteka.components.ui.BaseFragment
import ru.apteka.home.R
import ru.apteka.home.databinding.MainHomeFragmentBinding


/**
 * Представляет фрагмент "Главная".
 */
@SuppressLint("ResourceType")
@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, MainHomeFragmentBinding>() {

    override val viewModel: MainViewModel by viewModels()
    override val layoutId: Int = R.layout.main_home_fragment

    private val flipEnterAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_enter_anim) as AnimatorSet
    }

    private val flipExitAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_exit_anim) as AnimatorSet
    }

    private val flipPopEnterAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_pop_enter_anim) as AnimatorSet
    }

    private val flipPopExitAnim by lazy {
        AnimatorInflater.loadAnimator(requireContext(), R.anim.flip_pop_exit_anim) as AnimatorSet
    }

    override fun onViewBindingInflated(binding: MainHomeFragmentBinding) {
        if (!viewModel.navigationManager.isHomeFront.value!!) {
            binding.flBonusNavHost.bringToFront()
        }
        binding.viewModel = viewModel

        binding.flHomeNavHost.cameraDistance = 8000.dp.toFloat()
        binding.flBonusNavHost.cameraDistance = 8000.dp.toFloat()

        viewModel.navigationManager.onFabClick = {
            if (viewModel.navigationManager.isHomeFront.value!!) {
                flipExitAnim.apply {
                    setTarget(binding.flHomeNavHost)
                    start()
                }
                flipEnterAnim.apply {
                    setTarget(binding.flBonusNavHost)
                    start()
                }
                binding.flBonusNavHost.bringToFront()
                viewModel.navigationManager.isHomeFront.value = false
            } else {
                flipPopExitAnim.apply {
                    setTarget(binding.flBonusNavHost)
                    start()
                }
                flipPopEnterAnim.apply {
                    setTarget(binding.flHomeNavHost)
                    start()
                }
                binding.flHomeNavHost.bringToFront()
                viewModel.navigationManager.isHomeFront.value = true
            }
        }
    }

    override fun onResume() {
        super.onResume()


    }
}
