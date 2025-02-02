package com.example.ozinshe.fragments.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ozinshe.R
import com.example.ozinshe.data.model.SharedPref
import com.example.ozinshe.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        viewModel.loginResponse.observe(viewLifecycleOwner){
            binding.txtErrorPass.visibility = View.GONE
            SharedPref(requireContext()).saveToken(it.accessToken)
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }


        viewModel.errorResponse.observe(viewLifecycleOwner){
            showError()
        }

        binding.icToggle.setOnClickListener {
            toggleShowPassword()
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.edTextEmail.text.toString()
            val password = binding.edTextPassword.text.toString()


            val emailIsValid = emailValidation(email)
            val passwordIsValid = validationPassword(password)

            if(emailIsValid && passwordIsValid){
                viewModel.loginUser(email, password)
            }else{
                validationEmail(email)
            }
        }
    }

    private fun toggleShowPassword(){
        val passwordEditText = binding.edTextPassword
        passwordEditText.transformationMethod = if(passwordEditText.transformationMethod == HideReturnsTransformationMethod.getInstance()){
            PasswordTransformationMethod.getInstance()
        } else
            HideReturnsTransformationMethod.getInstance()
    }

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    private fun validationEmail(email: String){
        if(email.isEmpty())
        {
            binding.txtError.visibility = View.VISIBLE
            binding.txtError.text = getString(R.string.txt_error_emptyEmail)
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_error)
        }
        else if (email.trim().matches(emailPattern.toRegex()))
        {
            binding.txtError.visibility = View.GONE
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_focus_action)
        }
        else
        {
            binding.txtError.visibility = View.VISIBLE
            binding.txtError.text = getString(R.string.txt_error)
            binding.edTextEmail.setBackgroundResource(R.drawable.custom_edittext_error)
        }

    }

    private fun emailValidation(email: String): Boolean{
        return email.trim().matches(emailPattern.toRegex())
    }

    private fun validationPassword(password: String): Boolean{
        return if(password.isEmpty())
        {
            binding.txtErrorPass.text = getString(R.string.txt_error_emptyPass)
            binding.edTextPassword.setBackgroundResource(R.drawable.custom_edittext_error)
            binding.txtErrorPass.visibility = View.VISIBLE
            false
        }
        else if(password.length < 6)
        {
            binding.txtErrorPass.text = getString(R.string.txt_error_pass)
            binding.edTextPassword.setBackgroundResource(R.drawable.custom_edittext_error)
            binding.txtErrorPass.visibility = View.VISIBLE
            false
        }

        else
        {
            binding.edTextPassword.setBackgroundResource(R.drawable.custom_edittext_focus_action)
            binding.txtErrorPass.visibility = View.GONE
            true
        }
    }

    private fun showError() {
        binding.txtErrorPass.text = "Қателік орын алды"
        binding.txtErrorPass.visibility = View.VISIBLE
    }
}