package com.moapps.newsapp.breakingnews.ui.fragments.settings.view

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.databinding.FragmentSettingsBinding
import com.moapps.newsapp.breakingnews.ui.fragments.settings.viewmodel.SettingsViewModel
import com.moapps.newsapp.breakingnews.utils.Status
import com.moapps.newsapp.breakingnews.utils.showTSnackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val viewmodel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)


        binding.imgEditInterests.setOnClickListener {
            editInterests()
        }
        darkMode()


        return binding.root
    }


    private fun editInterests() {
        val view = LayoutInflater.from(activity).inflate(R.layout.edit_interests_layout, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)

        val alertDialog = builder.create()
        alertDialog.show()

        val interest1 = view.findViewById<EditText>(R.id.interest_1)
        val interest2 = view.findViewById<EditText>(R.id.interest_2)
        val interest3 = view.findViewById<EditText>(R.id.interest_3)
        val interest4 = view.findViewById<EditText>(R.id.interest_4)
        val updateInterests = view.findViewById<Button>(R.id.update_interests)

        viewmodel.getInterests().observe(viewLifecycleOwner, Observer {

            interest1.setText(it?.interest1)
            interest2.setText(it?.interest2)
            interest3.setText(it?.interest3)
            interest4.setText(it?.interest4)
            progressDialog.dismiss()

        })

        updateInterests.setOnClickListener {


            val interest1Text = interest1.text.toString()
            val interest2Text = interest2.text.toString()
            val interest3Text = interest2.text.toString()
            val interest4Text = interest2.text.toString()

            if (interest1Text.isEmpty() || interest1Text.length > 17) {
                interest1.error = "Too Long"
            } else if (interest2Text.isEmpty() || interest2Text.length > 17) {
                interest2.error = "Too Long"
            } else if (interest3Text.isEmpty() || interest3Text.length > 17) {
                interest3.error = "Too Long"
            } else if (interest4Text.isEmpty() || interest4Text.length > 17) {
                interest4.error = "Too Long"
            } else {
                viewmodel.editInterests(
                    interest1.text.toString(),
                    interest2.text.toString(),
                    interest3.text.toString(),
                    interest4.text.toString()
                ).observe(viewLifecycleOwner, Observer {

                    when (it.status) {
                        Status.LOADING -> {
                            progressDialog.show()
                            progressDialog.setTitle("Updating..")
                        }
                        Status.SUCCESS -> {
                            progressDialog.dismiss()
                            showTSnackbar(binding.root,"Updated.")
                            alertDialog.dismiss()

                        }
                        Status.ERROR -> {
                            progressDialog.dismiss()
                            Snackbar.make(binding.root, it.message + "", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }

                })
            }


        }


    }

    private fun darkMode() {
        val appSettingPrefs: SharedPreferences =
            requireActivity().getSharedPreferences("AppSettingPrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        binding.switchDarkMode.isChecked = isNightModeOn

        val sharedPrefsEdit: SharedPreferences.Editor = appSettingPrefs.edit()

        binding.switchDarkMode.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefsEdit.putBoolean("NightMode", true)
                sharedPrefsEdit.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefsEdit.putBoolean("NightMode", false)
                sharedPrefsEdit.apply()
            }
        }

    }
}