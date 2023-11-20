package com.aprexi.praxis.myapplication.presentation.fragment

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.databinding.FragmentLanguagesDetailBinding
import com.aprexi.praxis.myapplication.model.BasicLanguage
import com.aprexi.praxis.myapplication.model.DeleteLanguagesUser
import com.aprexi.praxis.myapplication.model.Experience
import com.aprexi.praxis.myapplication.model.InsertLanguagesUser
import com.aprexi.praxis.myapplication.model.LanguagesUser
import com.aprexi.praxis.myapplication.model.ListBasicLanguages
import com.aprexi.praxis.myapplication.model.ListExperience
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateLanguagesUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteLanguagesUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailLanguagesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.ExperienceState
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertLanguagesUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LanguagesBasicState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LanguagesUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateLanguagesUserState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class LanguagesDetailFragment: Fragment() {

    private lateinit var binding: FragmentLanguagesDetailBinding
    private lateinit var progressBar: ProgressBar

    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idLanguagesUser: Int = 0
    private var idLanguages: Int = 0
    private var idExperience: Int = 0
    private var idFragment: Int = 0
    private val args: LanguagesDetailFragmentArgs by navArgs()
    private val languagesViewModel: DetailLanguagesViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()
    private val myUtils: Utils by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguagesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.pbLanguagesDetail
        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
        if (idFragment == myUtils.CREATE_FRAGMENT){
            initUI()
        }
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)

                if (idFragment == myUtils.MODIFICATE_FRAGMENT) {
                    languagesViewModel.getLanguagesUser(
                        idUser = idUser,
                        idLanguages = idLanguages,
                        token = loginToken
                    )
                }

                languagesViewModel.getListExperience(
                    token = loginToken
                )

                languagesViewModel.getListBasicLanguages(
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(requireContext() ,e.toString())
        }
    }

    private fun initArgs(){
        idLanguages = args.idLanguages
        idExperience = args.idExperience
        idFragment = args.idFragment
    }

    private fun initViewModel() {
        languagesViewModel.getListExperienceLiveData().observe(viewLifecycleOwner, this::handleExperienceState)
        languagesViewModel.getListBasicLangaugesLiveData().observe(viewLifecycleOwner, this::handleLanguagesBasicState)
        languagesViewModel.languagesUserLiveData().observe(viewLifecycleOwner, this::handleLanguagesState)
        languagesViewModel.insertLanguagesUserLiveData().observe(viewLifecycleOwner, this::handleInsertLanguagesUserState)
        languagesViewModel.deleteLanguagesUserLiveData().observe(viewLifecycleOwner, this::handleDeleteStudiesUserState)
        languagesViewModel.updateLanguagesUserLiveData().observe(viewLifecycleOwner, this::handleUpdateLanguagesUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun getTokenLoginPreference(){
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleExperienceState(state: ExperienceState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListExperience(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleLanguagesState(state: LanguagesUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessLanguagesUserDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleLanguagesBasicState(state: LanguagesBasicState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListBasicLanguages(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleUpdateLanguagesUserState(state: UpdateLanguagesUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessUpdateLanguages(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleInsertLanguagesUserState(state: InsertLanguagesUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessInsertLanguages(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleDeleteStudiesUserState(state: DeleteLanguagesUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessDeleteLanguages(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> myUtils.showError(requireContext() ,state.error)
            else -> {}
        }
    }

    private fun handleSuccessLanguagesUserDetail(language: LanguagesUser) {
        myUtils.showProgressBar(false, progressBar)

        idLanguagesUser = language.idLanguagesUser.toInt()
        idLanguages = language.idLanguages.toInt()
        idExperience = language.idExperience.toInt()

        initUI()
    }

    class ExperienceAdapter(
        context: Context,
        resource: Int,
        private val experience: List<Experience>
    ) : ArrayAdapter<Experience>(context, resource, experience) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val experience = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = experience?.nameExperience // Muestra el nombre del experience
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val experience = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                experience?.nameExperience // Muestra el nombre del experience en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListExperience(experience: ListExperience) {
        myUtils.showProgressBar(false, progressBar)

        val idExp: Int = idExperience

        val adapter = LanguagesDetailFragment.ExperienceAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            experience.experience
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsExperienceLangaugesDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idLanguage
        val positionOfIdType = experience.experience.indexOfFirst { it.idExperience.toInt() == idExp }

        // Establece la selección del Spinner a la posición del elemento con idLanguage
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = experience.experience[position]
                // Realiza alguna acción con el elemento seleccionado

                idExperience = selectedItem.idExperience.toInt()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    class LanguageAdapter(
        context: Context,
        resource: Int,
        private val languages: List<BasicLanguage>
    ) : ArrayAdapter<BasicLanguage>(context, resource, languages) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val languages = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = languages?.nameLanguages // Muestra el nombre del lenguaje
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val languages = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                languages?.nameLanguages // Muestra el nombre del lenguaje en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListBasicLanguages(language: ListBasicLanguages) {
        myUtils.showProgressBar(false, progressBar)

        val idLanguage: Int = idLanguages

        val adapter = LanguageAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            language.basicLanguages
        )

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsLanguageLanguagesDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idLanguage
        val positionOfIdType = language.basicLanguages.indexOfFirst { it.idLanguages.toInt() == idLanguage }

        // Establece la selección del Spinner a la posición del elemento con idLanguage
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = language.basicLanguages[position]
                // Realiza alguna acción con el elemento seleccionado
                idLanguages = selectedItem.idLanguages.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    private fun initUI() {


        binding.vBackBottomLanguages.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        when (args.idFragment) {
            1 -> { //Actualizar
                binding.tvBottonSaveLanguagesFragment.visibility = View.VISIBLE
                binding.tvBottonTrushLanguagesFragment.visibility = View.VISIBLE
                binding.btnCreateLanguagesDetailFragment.visibility = View.GONE

                binding.tvBottonSaveLanguagesFragment.setOnClickListener {
                    updateLanguages()
                }

                binding.tvBottonTrushLanguagesFragment.setOnClickListener {
                    deleteLanguages()
                }
            }

            2 -> {//Crear
                binding.tvBottonSaveLanguagesFragment.visibility = View.GONE
                binding.tvBottonTrushLanguagesFragment.visibility = View.GONE
                binding.btnCreateLanguagesDetailFragment.visibility = View.VISIBLE

                binding.btnCreateLanguagesDetailFragment.setOnClickListener {
                    createLanguages()
                }
            }

            else -> {
                // Manejo para otros valores de idFragment si es necesario
            }
        }
    }

    private fun updateLanguages() {

        if (idLanguages != 0 && idExperience != 0 && idLanguagesUser != 0 ) {
            languagesViewModel.updateLanguagesUser(
                idUser = idUser,
                idExperience = idExperience,
                idLanguages = idLanguages,
                idLanguagesUser = idLanguagesUser,
                token = loginToken
            )
        }else{
            binding.acsLanguageLanguagesDetailFragment.background = com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
            binding.acsExperienceLangaugesDetailFragment.background = com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun createLanguages() {

        if (idLanguages != 0 && idExperience != 0) {
            languagesViewModel.insertLanguagesUser(
                idUser = idUser,
                idExperience = idExperience,
                idLanguagesUser = idLanguagesUser,
                idLanguages = idLanguages,
                token = loginToken
            )
        }else{
            binding.acsLanguageLanguagesDetailFragment.background = com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
            binding.acsExperienceLangaugesDetailFragment.background = com.aprexi.praxis.myapplication.R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun deleteLanguages() {

        myUtils.showConfirmationDialog(requireContext(), requireContext().getString(com.aprexi.praxis.myapplication.R.string.message_show_dialog_delete)) { confirmed ->
            if (confirmed && idLanguagesUser != 0) {
                languagesViewModel.deleteLanguagesUser(
                    idUser = idUser,
                    idLanguagesUser = idLanguagesUser,
                    token = loginToken
                )
            }
        }
    }

    private fun handleSuccessInsertLanguages(languages: InsertLanguagesUser) {
        myUtils.showProgressBar(false, progressBar)

        if(languages.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteLanguages(languages: DeleteLanguagesUser) {
        myUtils.showProgressBar(false, progressBar)

        if(languages.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessUpdateLanguages(languges: UpdateLanguagesUser) {
        myUtils.showProgressBar(false, progressBar)

        if(languges.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessFailed() {
        myUtils.showProgressBar(false, progressBar)
        cleanTokenAndRedirectToLogin()
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        myUtils.redirectToLogin(requireContext())
    }

}