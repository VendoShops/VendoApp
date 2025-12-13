package com.example.vendoapp.ui.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.graphics.Matrix
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.vendoapp.R
import com.example.vendoapp.ui.base.BaseFragment
import com.example.vendoapp.databinding.FragmentProfileBinding
import com.example.vendoapp.utils.Resource
import com.example.vendoapp.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    @Inject lateinit var tokenManager: TokenManager
    private val viewModel: ProfileViewModel by viewModels()

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult

            try {
                val inputStream = requireContext().contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                if (bitmap == null) {
                    Toast.makeText(requireContext(), "Şəkil oxuna bilmədi. Zəhmət olmasa başqa şəkil seçin.", Toast.LENGTH_LONG).show()
                    return@registerForActivityResult
                }
                if (bitmap.width == 0 || bitmap.height == 0) {
                    Toast.makeText(requireContext(), "Şəkil boş və ya ölçüləri sıfırdır. Zəhmət olmasa başqa şəkil seçin.", Toast.LENGTH_LONG).show()
                    return@registerForActivityResult
                }

                val targetWidth = 1200
                val targetHeight = 400

                val croppedForAspectBitmap = cropToTargetAspectRatio(bitmap, targetWidth.toFloat() / targetHeight.toFloat())
                val resizedBitmap = Bitmap.createScaledBitmap(croppedForAspectBitmap, targetWidth, targetHeight, true)
                Log.d("profileimg", "Resized Bitmap Dimensions: ${resizedBitmap.width}x${resizedBitmap.height}")


                val baos = ByteArrayOutputStream()
                var quality = 100
                var currentBytes: ByteArray? = null
                var tempCurrentBytes: ByteArray? = null

                do {
                    baos.reset()
                    val compressedSuccessfully = resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos)
                    if (!compressedSuccessfully) {
                        Log.e("ImageError", "Bitmap compression failed for quality: $quality")
                        tempCurrentBytes = null
                        break
                    }
                    tempCurrentBytes = baos.toByteArray()
                    currentBytes = baos.toByteArray()

                    Log.d("profileimg", "Compression Loop: Quality = $quality, Current Bytes Size = ${tempCurrentBytes?.size} bytes")

                    quality -= 5
                    if (quality < 0) {
                        Log.d("profileimg", "Compression Loop: Quality reached below 0, breaking.")

                        break
                    }
                } while (tempCurrentBytes != null && tempCurrentBytes.size > 2 * 1024 * 1024)

                val finalBytes = tempCurrentBytes ?: throw IllegalStateException("Şəkil sıxılmasından sonra baytlar boş ola bilməz.")

                Log.d("profileimg", "Final Bytes size: ${finalBytes.size / (1024 * 1024)} MB, actual bytes: ${finalBytes.size}")

                if (finalBytes.isEmpty() && resizedBitmap.byteCount == 0) {
                    Toast.makeText(requireContext(), "Şəkil sıxılarkən problem yarandı.", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                viewModel.updateAvatar(
                    userId = tokenManager.getUserId(),
                    fileBytes = finalBytes,
                    mimeType = "image/jpeg"
                )
            } catch(e:Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Şəkil yüklənməsində xəta: ${e.message}", Toast.LENGTH_LONG).show()
            }

        }

    override fun onViewCreateFinish() {
        setupUi()
        tvLogoutClick()
        observes()

        val userId = tokenManager.getUserId()
        if (userId != -1) {
            viewModel.loadUserProfile(userId)
        } else {
            findNavController().navigate(R.id.loginFragment)
            Toast.makeText(requireContext(),  getString(R.string.user_id_not_found), Toast.LENGTH_SHORT).show()
        }

        //  Navigations
        binding.termsAndConditionsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_termsAndConditionsFragment)
        }
        binding.languageConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_selectLanguageFragment)
        }
        binding.personalInfoConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_personalInformationFragment)
        }
        binding.addressesConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addressesFragment)
        }
        binding.myPaymentMethodsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_paymentMethodFragment)
        }
        binding.myOrderConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myOrdersFragment)
        }
        binding.myReturnsConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myReturnsFragment)
        }
        binding.ivProfile.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }

    fun cropToTargetAspectRatio(bitmap: Bitmap, targetAspectRatio: Float): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val originalAspectRatio = originalWidth.toFloat() / originalHeight.toFloat()

        var startX = 0
        var startY = 0
        var newWidth = originalWidth
        var newHeight = originalHeight

        if (originalAspectRatio > targetAspectRatio) {
            newWidth = (originalHeight * targetAspectRatio).toInt()
            startX = (originalWidth - newWidth) / 2
        } else if (originalAspectRatio < targetAspectRatio) {
            newHeight = (originalWidth / targetAspectRatio).toInt()
            startY = (originalHeight - newHeight) / 2
        }

        if (startX == 0 && startY == 0 && newWidth == originalWidth && newHeight == originalHeight) {
            return bitmap
        }

        return Bitmap.createBitmap(bitmap, startX, startY, newWidth, newHeight)
    }


    private fun observes() {
        viewModel.profile.observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Idle -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    val profile = resource.data
                    binding.tvProfileName.text = profile?.fullName ?: getString(R.string.no_name)
                    binding.tvProfileEmail.text = "User ID: ${profile?.userId}"
                    Glide.with(this)
                        .load(profile?.avatarUrl)
                        .placeholder(R.drawable.profile)
                        .into(binding.ivProfile)
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    binding.ivProfile.setImageResource(R.color.red_09)
                }
            }
        }

        viewModel.avatar.observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Idle -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is Resource.Success -> {
                    binding.progressBar.isVisible = false

                    Glide.with(this)
                        .load(resource.data?.avatarUrl)
                        .placeholder(R.drawable.profile)
                        .into(binding.ivProfile)

                    Toast.makeText(requireContext(), getString(R.string.avatar_updated), Toast.LENGTH_SHORT).show()

                }

                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), resource.message ?: "Avatar update failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun tvLogoutClick() {
        binding.tvLogOut.setOnClickListener {
            showLogoutDialog(requireContext()) {
                tokenManager.clearTokens()
            }
        }
    }

    fun showLogoutDialog(context: Context, onLogoutConfirmed: () -> Unit) {
        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null)
        dialog.setContentView(view)

        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        btnLogout.setOnClickListener {
            dialog.dismiss()
            onLogoutConfirmed()
            findNavController().navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
            )
        }


        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun setupUi() {
        binding.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(0, systemBars.top, 0, 0)
                insets
            }
        }
    }
}