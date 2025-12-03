package com.example.vendoapp.ui.profile.selectlanguage

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.findNavController
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentSelectLanguageBinding
import com.example.vendoapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SelectLanguageFragment : BaseFragment<FragmentSelectLanguageBinding>(
    FragmentSelectLanguageBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager
    private var selectedLanguage = "en"

    override fun onViewCreateFinish() {
        setupUi()
        initSelectedLanguage()
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.englishConstraint.setOnClickListener {
            selectLanguage("en")
        }

        binding.arabicConstraint.setOnClickListener {
            selectLanguage("ar")
        }
    }

    private fun updateUiForSelection(languageCode: String) {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.black)
        val unselectedColor = ContextCompat.getColor(requireContext(), R.color.grey_9f)

        if (languageCode == "en") {
            binding.tvEnglish.setTextColor(selectedColor)
            binding.tvArabic.setTextColor(unselectedColor)
            binding.ivSelectedEnglish.visibility = View.VISIBLE
            binding.ivSelectedArabic.visibility = View.GONE
        } else {
            binding.tvArabic.setTextColor(selectedColor)
            binding.tvEnglish.setTextColor(unselectedColor)
            binding.ivSelectedArabic.visibility = View.VISIBLE
            binding.ivSelectedEnglish.visibility = View.GONE
        }
    }

    private fun initSelectedLanguage() {
        selectedLanguage = tokenManager.getLanguage()
        updateUiForSelection(selectedLanguage)
    }

    private fun selectLanguage(lang: String) {
        selectedLanguage = lang
        tokenManager.saveLanguage(lang)
        tokenManager.applyLocale(requireContext(), lang)
        updateUiForSelection(lang)
        requireActivity().recreate()
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, systemBars.bottom)
                insets
            }
        }
    }
}