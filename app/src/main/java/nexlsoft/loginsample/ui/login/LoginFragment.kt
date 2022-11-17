package nexlsoft.loginsample.ui.login

import android.R.attr.password
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nexlsoft.loginsample.R
import nexlsoft.loginsample.data.local.AppSharedPreferences
import nexlsoft.loginsample.data.repository.model.User
import nexlsoft.loginsample.databinding.FragmentLoginBinding
import nexlsoft.loginsample.utils.ValidatesField
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment(R.layout.fragment_login),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private val viewModel by viewModel<LoginViewModel>()
    private var binding: FragmentLoginBinding? = null

    private var mView: View? = null
    private var validatesField = ValidatesField()
    private var hasSpecialCharacters: Int = 0
    private var hasNumber: Int = 0

    override fun onStart() {
        super.onStart()
        AppSharedPreferences(requireContext()).registerOnChange(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (mView == null) {
            binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

            mView = binding!!.root
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.edtYourEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (validatesField.checkEmail(s.toString())) {
                    binding!!.tvEmailValid.visibility = View.GONE
                } else {
                    binding!!.tvEmailValid.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding!!.edtYourPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length >= 6 && s.toString().length <= 16) {
                    binding!!.tvPasswordValid.visibility = View.GONE
                } else {
                    binding!!.tvPasswordValid.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                hasSpecialCharacters = if (!validatesField.isLetters(s.toString())) {
                    1
                } else {
                    0
                }

                hasNumber = if (s.toString().contains("[0-9]".toRegex())) {
                    1
                } else {
                    0
                }

                when(hasNumber + hasSpecialCharacters){
                     0-> binding!!.levelPassword.text = "WEAK"
                     1-> binding!!.levelPassword.text = "FAIR"
                     2-> binding!!.levelPassword.text = "GOOD"
                }

            }
        })

        binding!!.btnSignUp.setOnClickListener {
            viewModel.login(
                user = User(
                    "trung",
                    "huynh",
                    binding!!.edtYourEmail.text.toString(),
                    binding!!.edtYourPassword.text.toString()
                ), requireContext()
            )
            lifecycleScope.launch {
                delay(300)
                if(viewModel.response!!.isSuccessful){
                    findNavController().navigate(R.id.CategoriesFragment)
                }else{
                    Toast.makeText(context,viewModel.response!!.message(),Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        AppSharedPreferences(requireContext()).unregisterOnChange(this)
    }
}
