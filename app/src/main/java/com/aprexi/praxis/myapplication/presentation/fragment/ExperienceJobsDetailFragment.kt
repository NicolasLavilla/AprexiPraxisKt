package com.aprexi.praxis.myapplication.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentExperienceJobDetailBinding
import com.aprexi.praxis.myapplication.model.Category
import com.aprexi.praxis.myapplication.model.DeleteExperienceJobUser
import com.aprexi.praxis.myapplication.model.ExperienceJobUser
import com.aprexi.praxis.myapplication.model.InsertExperienceJobUser
import com.aprexi.praxis.myapplication.model.LevelJob
import com.aprexi.praxis.myapplication.model.ListBasicCompany
import com.aprexi.praxis.myapplication.model.ListCategory
import com.aprexi.praxis.myapplication.model.ListLevelJob
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.UpdateExperienceJobUser
import com.aprexi.praxis.myapplication.presentation.utils.Utils
import com.aprexi.praxis.myapplication.presentation.viewmodel.CategoryState
import com.aprexi.praxis.myapplication.presentation.viewmodel.CompanyBasicState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteExperienceJobUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailExperienceJobViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.ExperienceJobUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertExpereienceJobUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.LevelJobState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateExperienceJobUserState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ExperienceJobsDetailFragment : Fragment() {

    private lateinit var binding: FragmentExperienceJobDetailBinding
    private lateinit var progressBar: ProgressBar
    private val myUtils: Utils by inject()

    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idFragment: Int = 0
    private var idExperienceJobs: Int = 0
    private var idLevelJob: Int = 0
    private var idCategory: Int = 0
    private var idCompany: Int = 0
    private val args: ExperienceJobsDetailFragmentArgs by navArgs()
    private val detailExperienceJobViewModel: DetailExperienceJobViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExperienceJobDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = binding.pbExperienceJobDetail
        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()
    }

    private fun initArgs() {
        idExperienceJobs = args.idExperienceJob
        idFragment = args.idFragment
        idCompany = args.idCompany
        idLevelJob = args.idLevelJob
        idCategory = args.idCategory
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                detailExperienceJobViewModel.getExperienceJobUser(
                    idUser = idUser,
                    idExperienceJobUser = idExperienceJobs,
                    token = loginToken
                )

                detailExperienceJobViewModel.getListCategory(
                    token = loginToken
                )

                detailExperienceJobViewModel.getListLevelJob(
                    token = loginToken
                )

                detailExperienceJobViewModel.getListCompany(
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            myUtils.showError(context = requireContext(),e.toString())
        }
    }

    private fun getTokenLoginPreference() {
        lifecycleScope.launch {
            val uni = tokenViewModel.fetchLoginTokenPreferences()
            loginToken = uni.token
            succesToken = uni.success
            idUser = uni.idUser
        }
    }

    private fun initViewModel() {
        detailExperienceJobViewModel.experienceJobUserLiveData()
            .observe(viewLifecycleOwner, this::handleExperienceJobUserState)
        detailExperienceJobViewModel.companyBasicLiveData()
            .observe(viewLifecycleOwner, this::handleCompanyBasicState)
        detailExperienceJobViewModel.categoryLiveData()
            .observe(viewLifecycleOwner, this::handleCategoryUserState)
        detailExperienceJobViewModel.levelJobLiveData()
            .observe(viewLifecycleOwner, this::handleLevelJobUserState)
        detailExperienceJobViewModel.insertExperienceJobUserLiveData()
            .observe(viewLifecycleOwner, this::handleInsertExperienceUserState)
        detailExperienceJobViewModel.updateExperienceJobUserLiveData()
            .observe(viewLifecycleOwner, this::handleUpdateExperienceJobUserState)
        detailExperienceJobViewModel.deleteExperienceJobUserLiveData()
            .observe(viewLifecycleOwner, this::handleDeleteExperienceJobUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> myUtils.showProgressBar(false, progressBar)
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleDeleteExperienceJobUserState(state: DeleteExperienceJobUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessDeleteStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleUpdateExperienceJobUserState(state: UpdateExperienceJobUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessUpdateStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleInsertExperienceUserState(state: InsertExpereienceJobUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessInsertStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleLevelJobUserState(state: LevelJobState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListLevel(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleCategoryUserState(state: CategoryState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListCategory(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleCompanyBasicState(state: CompanyBasicState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessListNameCompany(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleExperienceJobUserState(state: ExperienceJobUserState) {
        when (state) {
            is ResourceState.Loading -> myUtils.showProgressBar(true, progressBar)
            is ResourceState.Success -> handleSuccessExperienceJobUser(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error ->  myUtils.showError(context = requireContext(),state.error)
            else -> {}
        }
    }

    private fun handleSuccessListCategory(categoryListSuccess: ListCategory) {
        desplegableListCategory(categoryListSuccess)
        myUtils.showProgressBar(false, progressBar)
    }

    private fun handleSuccessListLevel(levelJobSuccess: ListLevelJob) {
        desplegableListLevel(levelJobSuccess)
        myUtils.showProgressBar(false, progressBar)
    }

    private fun handleSuccessListNameCompany(companyListSuccess: ListBasicCompany) {
        desplegableListNameCompany(companyListSuccess)
        myUtils.showProgressBar(false, progressBar)
    }

    private fun handleSuccessExperienceJobUser(experience: ExperienceJobUser) {
        myUtils.showProgressBar(false, progressBar)
        initUI(experience)
    }

    private fun initUI(varExperience: ExperienceJobUser) {
        var initDate: String = myUtils.changeDateFormatEUR(varExperience.initDate)
        var endDate: String = myUtils.changeDateFormatEUR(varExperience.endDate)


        binding.vBackBottomExperienceJob.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tieInitDateExperienceJobDetailFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieInitDateExperienceJobDetailFragment.setText(selectedDate)
                initDate = selectedDate
            }
        }

        binding.tieEndDateExperienceJobDetailFragment.setOnClickListener {
            myUtils.showDatePicker(requireContext()) { selectedDate ->
                binding.tieEndDateExperienceJobDetailFragment.setText(selectedDate)
                endDate = selectedDate
            }
        }

        when (idFragment) {
            1 -> { //Actualizar
                binding.tvBottonSaveExperienceJobFragment.visibility = View.VISIBLE
                binding.tvBottonTrushExperienceJobFragment.visibility = View.VISIBLE
                binding.btnCreateExperienceJobDetailFragment.visibility = View.GONE
                binding.tieInitDateExperienceJobDetailFragment.text = initDate
                binding.tieDescriptionJobExperienceJobDetailFragment.setText(varExperience.descriptionJob)
                binding.tieNameJobExperienceJobDetailFragment.setText(varExperience.nameJobs)
                binding.actvCompanyExperienceJobDetailFragment.setText(varExperience.nameCompany)

                if (endDate.isNotEmpty()) {
                    binding.tieEndDateExperienceJobDetailFragment.text = endDate
                }
            }

            2 -> {//Crear
                binding.tvBottonTrushExperienceJobFragment.visibility = View.GONE
                binding.tvBottonSaveExperienceJobFragment.visibility = View.GONE
                binding.btnCreateExperienceJobDetailFragment.visibility = View.VISIBLE
            }

            else -> {
                // Manejo para otros valores de idFragment si es necesario
            }
        }

        binding.tvBottonSaveExperienceJobFragment.setOnClickListener {
            updateExperienceJobUser(
                nameJobs = myUtils.capitalizeFirstLetter(binding.tieNameJobExperienceJobDetailFragment.text.toString()),
                idLevelJob = idLevelJob,
                idCategory = idCategory,
                descriptionJob = myUtils.capitalizeFirstLetter(binding.tieDescriptionJobExperienceJobDetailFragment.text.toString()),
                idCompany = idCompany,
                initDate = initDate,
                endDate = endDate
            )
        }

        binding.btnCreateExperienceJobDetailFragment.setOnClickListener {
            createExperienceJobUser(
                nameJobs = myUtils.capitalizeFirstLetter(binding.tieNameJobExperienceJobDetailFragment.text.toString()),
                idLevelJob = idLevelJob,
                idCategory = idCategory,
                descriptionJob = myUtils.capitalizeFirstLetter(binding.tieDescriptionJobExperienceJobDetailFragment.text.toString()),
                idCompany = idCompany,
                initDate = initDate,
                endDate = endDate
            )
        }

        binding.tvBottonTrushExperienceJobFragment.setOnClickListener {
            deleteExperienceJobUser()
        }
    }

    private fun updateExperienceJobUser(
        nameJobs: String,
        idLevelJob: Int,
        idCategory: Int,
        descriptionJob: String,
        idCompany: Int,
        initDate: String,
        endDate: String
    ) {

        if ((nameJobs.isNotEmpty() || nameJobs.isNotBlank()) && (descriptionJob.isNotEmpty() || descriptionJob.isNotBlank())
            && idLevelJob != 0 && idCategory != 0 && idCompany != 0 && initDate.isNotEmpty() && idExperienceJobs != 0
        ) {
            detailExperienceJobViewModel.updateExperienceJobUser(
                idUser = idUser,
                nameJobs = nameJobs,
                level = idLevelJob,
                initDate = myUtils.changeDateFormatEU(initDate),
                endDate = myUtils.changeDateFormatEU(endDate),
                category = idCategory,
                descriptionJob = descriptionJob,
                idCompany = idCompany,
                idExperienceJobUser = idExperienceJobs,
                token = loginToken
            )
        } else {
            if (nameJobs.isEmpty() || nameJobs.isBlank()) {
                binding.tieNameJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (descriptionJob.isEmpty() || descriptionJob.isBlank()) {
                binding.tieDescriptionJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idLevelJob == 0) {
                binding.acsLevelJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idCategory == 0) {
                binding.acsCategoryExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idCompany == 0) {
                binding.actvCompanyExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (initDate.isEmpty()) {
                binding.tieInitDateExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun createExperienceJobUser(
        nameJobs: String,
        idLevelJob: Int,
        idCategory: Int,
        descriptionJob: String,
        idCompany: Int,
        initDate: String,
        endDate: String
    ) {

        if ((nameJobs.isNotEmpty() || nameJobs.isNotBlank()) && (descriptionJob.isNotEmpty() || descriptionJob.isNotBlank())
            && idLevelJob != 0 && idCategory != 0 && idCompany != 0 && initDate.isNotEmpty() && idExperienceJobs != 0
        ) {
            detailExperienceJobViewModel.insertExperienceJobUser(
                idUser = idUser,
                nameJobs = nameJobs,
                level = idLevelJob,
                initDate = myUtils.changeDateFormatEU(initDate),
                endDate = myUtils.changeDateFormatEU(endDate),
                category = idCategory,
                descriptionJob = descriptionJob,
                idCompany = idCompany,
                idExperienceJobUser = idExperienceJobs,
                token = loginToken
            )
        } else {
            if (nameJobs.isEmpty() || nameJobs.isBlank()) {
                binding.tieNameJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (descriptionJob.isEmpty() || descriptionJob.isBlank()) {
                binding.tieDescriptionJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idLevelJob == 0) {
                binding.acsLevelJobExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idCategory == 0) {
                binding.acsCategoryExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (idCompany == 0) {
                binding.actvCompanyExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }

            if (initDate.isEmpty()) {
                binding.tieInitDateExperienceJobDetailFragment.background =
                    R.drawable.spinner_background_error.toDrawable()
            }
        }
    }

    private fun deleteExperienceJobUser() {

        myUtils.showConfirmationDialog(requireContext(), requireContext().getString(R.string.message_show_dialog_delete)) { confirmed ->
            if (confirmed && idExperienceJobs != 0) {
                detailExperienceJobViewModel.deleteExperienceJobUser(
                    idUser = idUser,
                    idExperienceJobUser = idExperienceJobs,
                    token = loginToken
                )
            }
        }
    }

    private fun desplegableListNameCompany(companyListSuccess: ListBasicCompany) {
        val autoCompleteTextView = binding.actvCompanyExperienceJobDetailFragment

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            companyListSuccess.basicCompany.map { it.nameCompany })
        autoCompleteTextView.setAdapter(adapter)

        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val enteredText = autoCompleteTextView.text.toString()
            val matchingCompanies = companyListSuccess.basicCompany.filter {
                it.nameCompany.equals(enteredText, ignoreCase = true)
            }

            if (matchingCompanies.isNotEmpty()) {
                val selectedCompany = matchingCompanies.first()
                idCompany = selectedCompany.idCompany.toInt()
                // Aquí puedes acceder a la empresa seleccionada y sus propiedades (idCompany, nameCompany)
                // selectedCompany.idCompany
                // selectedCompany.nameCompany
            }
        }

        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val enteredText = s.toString()
                val matchingCompanies = companyListSuccess.basicCompany.filter {
                    it.nameCompany.contains(
                        enteredText,
                        ignoreCase = true
                    )
                }

                if (matchingCompanies.isEmpty()) {
                    // Aquí puedes guardar el texto que ha escrito el usuario en una variable
                    var nameCompany = enteredText
                    // Realiza alguna acción con `userInput`, como guardarlo en una variable global
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


    class CategoryAdapter(
        context: Context,
        resource: Int,
        private val category: List<Category>
    ) : ArrayAdapter<Category>(context, resource, category) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val category = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = category?.nameCategory // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val category = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                category?.nameCategory // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun desplegableListCategory(categoryListSuccess: ListCategory) {
        val adapter = CategoryAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryListSuccess.category
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsCategoryExperienceJobDetailFragment
        spinner.adapter = adapter

        val positionOfIdType =
            categoryListSuccess.category.indexOfFirst { it.idCategory.toInt() == idCategory }

        // Establece la selección del Spinner a la posición del elemento con idType
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = categoryListSuccess.category[position]
                // Realiza alguna acción con el elemento seleccionado
                idCategory = selectedItem.idCategory.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    class LevelJobAdapter(
        context: Context,
        resource: Int,
        private val levelJob: List<LevelJob>
    ) : ArrayAdapter<LevelJob>(context, resource, levelJob) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val levelJob = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = levelJob?.nameLevelJob // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val levelJob = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                levelJob?.nameLevelJob // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun desplegableListLevel(levelJobSuccess: ListLevelJob) {
        val adapter = LevelJobAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            levelJobSuccess.levelJob
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsLevelJobExperienceJobDetailFragment
        spinner.adapter = adapter

        val positionOfIdType =
            levelJobSuccess.levelJob.indexOfFirst { it.idLevelJob.toInt() == idLevelJob }

        // Establece la selección del Spinner a la posición del elemento con idType
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = levelJobSuccess.levelJob[position]
                // Realiza alguna acción con el elemento seleccionado
                idLevelJob = selectedItem.idLevelJob.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    private fun handleSuccessInsertStudies(studies: InsertExperienceJobUser) {
        myUtils.showProgressBar(false, progressBar)

        if (studies.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessUpdateStudies(studies: UpdateExperienceJobUser) {
        myUtils.showProgressBar(false, progressBar)

        if (studies.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteStudies(studies: DeleteExperienceJobUser) {
        myUtils.showProgressBar(false, progressBar)

        if (studies.success) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        } else {
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
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