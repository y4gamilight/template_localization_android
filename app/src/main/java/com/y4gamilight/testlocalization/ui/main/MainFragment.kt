package com.y4gamilight.testlocalization.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.y4gamilight.testlocalization.R
import com.y4gamilight.testlocalization.App



class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var btnSwitch:Button
    private lateinit var textMessage: TextView
    private var language:String = LocaleManager.LANGUAGE_ENGLISH
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSwitch = view.findViewById(R.id.btn_switch) as Button
        textMessage = view.findViewById(R.id.message) as TextView

        btnSwitch.setOnClickListener {
            if (language == LocaleManager.LANGUAGE_ENGLISH) {
                language = LocaleManager.LANGUAGE_VIETNAMESE
            } else {
                language = LocaleManager.LANGUAGE_ENGLISH
            }
            context?.let {
                val newContext = App.localeManager.setNewLocale(it, language)
                btnSwitch.text = newContext?.getText(R.string.text_name_language)
            }
        }

    }


}
