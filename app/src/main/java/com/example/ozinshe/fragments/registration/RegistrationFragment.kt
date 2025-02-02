package com.example.ozinshe.fragments.registration

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.R
import com.example.ozinshe.data.model.SharedPref
import com.example.ozinshe.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.registrationResponse.observe(viewLifecycleOwner){
            binding.txtErrorPass.visibility = View.GONE
            Toast.makeText(requireContext(), "Тіркелу өтті", Toast.LENGTH_SHORT).show()
            SharedPref(requireContext()).saveToken(it.accessToken)
        }


        viewModel.errorResponse.observe(viewLifecycleOwner){
            showError()
        }

        binding.btnRegistr.setOnClickListener {
            val email = binding.edTextEmail.text.toString()
            val password = binding.edTextPass.text.toString()
            val rePassword = binding.edTextRepeatPass.text.toString()


            val emailIsValid = emailValidation(email)
            val passwordIsValid = validationPassword(password, rePassword)

            if(emailIsValid && passwordIsValid){
                viewModel.registrationUser(email, password)
            }else{
                validationEmail(email)
            }
        }

        binding.icToggleRegistr.setOnClickListener {
            toggleShowPassword()
        }
        binding.icToggleRegistrRepeat.setOnClickListener {
            toggleShowRePass()
        }


        binding.signInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private fun emailValidation(email: String): Boolean{
        return email.trim().matches(emailPattern.toRegex())
    }
    private fun validationEmail(email: String){
        if(email.isEmpty())
        {
            binding.txtErrorEmail.visibility = View.VISIBLE
            binding.txtErrorEmail.text = getString(R.string.txt_error_emptyEmail)
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_error)
        }
        else if (email.trim().matches(emailPattern.toRegex()))
        {
            binding.txtErrorEmail.visibility = View.GONE
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_focus_action)
        }
        else
        {
            binding.txtErrorEmail.visibility = View.VISIBLE
            binding.txtErrorEmail.text = getString(R.string.txt_error)
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_error)
        }

    }


    private fun validationPassword(password: String, rePassword: String): Boolean{
        return if(password.isEmpty()){
            binding.txtErrorPass.visibility = View.VISIBLE
            binding.txtErrorPass.text = getString(R.string.txt_error_emptyPass)
            binding.edTextPass.setBackgroundResource(R.drawable.custom_edittext_error)
            false
        }

        else if(password.length < 6){
            binding.txtErrorPass.text = getString(R.string.txt_error_pass)
            binding.edTextPass.setBackgroundResource(R.drawable.custom_edittext_error)
            binding.txtErrorPass.visibility = View.VISIBLE
            false
        }
        else if(password != rePassword){
            binding.txtErrorPass.text = getString(R.string.check_pass)
            binding.edTextPass.setBackgroundResource(R.drawable.custom_edittext_error)
            binding.edTextRepeatPass.setBackgroundResource(R.drawable.custom_edittext_error)
            binding.txtErrorPass.visibility = View.VISIBLE
            false
        }

        else {
            binding.edTextPass.setBackgroundResource(R.drawable.custom_edittext_focus_action)
            binding.txtErrorPass.visibility = View.GONE
            true
        }
    }
    private fun showError() {
        binding.txtErrorPass.text = "Қателік орын алды"
        binding.txtErrorPass.visibility = View.VISIBLE
    }

    private fun toggleShowPassword(){
        val passwordEditText = binding.edTextPass
        passwordEditText.transformationMethod = if(passwordEditText.transformationMethod == HideReturnsTransformationMethod.getInstance()){
            PasswordTransformationMethod.getInstance()
        } else
            HideReturnsTransformationMethod.getInstance()
    }

    private fun toggleShowRePass(){
        val passRepeatEditText = binding.edTextRepeatPass
        passRepeatEditText.transformationMethod = if(passRepeatEditText.transformationMethod == HideReturnsTransformationMethod.getInstance()){
            PasswordTransformationMethod.getInstance()
        } else
            HideReturnsTransformationMethod.getInstance()
    }

}