package com.aprexi.praxis.myapplication.presentation.fragment

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aprexi.praxis.myapplication.R
import com.aprexi.praxis.myapplication.databinding.FragmentStudiesDetailBinding
import com.aprexi.praxis.myapplication.model.DeleteStudiesUser
import com.aprexi.praxis.myapplication.model.InsertStudiesUser
import com.aprexi.praxis.myapplication.model.ListNameStudies
import com.aprexi.praxis.myapplication.model.ListProfessionalFamilies
import com.aprexi.praxis.myapplication.model.ListSchool
import com.aprexi.praxis.myapplication.model.ListTypeStudies
import com.aprexi.praxis.myapplication.model.NameStudy
import com.aprexi.praxis.myapplication.model.ProfessionalFamily
import com.aprexi.praxis.myapplication.model.ResourceState
import com.aprexi.praxis.myapplication.model.School
import com.aprexi.praxis.myapplication.model.StudiesUser
import com.aprexi.praxis.myapplication.model.TypeStudy
import com.aprexi.praxis.myapplication.model.UpdateStudiesUser
import com.aprexi.praxis.myapplication.presentation.SplashActivity
import com.aprexi.praxis.myapplication.presentation.viewmodel.DeleteStudiesUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.DetailStudiesViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.InsertStudiesState
import com.aprexi.praxis.myapplication.presentation.viewmodel.NameStudiesState
import com.aprexi.praxis.myapplication.presentation.viewmodel.ProfessionalFamiliesState
import com.aprexi.praxis.myapplication.presentation.viewmodel.SchoolState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenDetailState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TokenViewModel
import com.aprexi.praxis.myapplication.presentation.viewmodel.StudiesUserState
import com.aprexi.praxis.myapplication.presentation.viewmodel.TypeStudiesState
import com.aprexi.praxis.myapplication.presentation.viewmodel.UpdateStudiesUserState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class StudiesDetailFragment : Fragment() {

    private val binding: FragmentStudiesDetailBinding by lazy {
        FragmentStudiesDetailBinding.inflate(layoutInflater)
    }

    private var loginToken: String = ""
    private var succesToken: Boolean = false
    private var idUser: Int = 0
    private var idStudiesUs: Int = 0
    private var idProfessionalFamilies: Int = 0
    private var idTypeStudies: Int = 0
    private var idSchool: Int = 0
    private var idNameStudies: Int = 0
    private var initDate: String = ""
    private var endDate: String? = null
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        calendar.time = Calendar.getInstance().time
        return binding.root
    }

    private val args: StudiesDetailFragmentArgs by navArgs()
    private val studiesViewModel: DetailStudiesViewModel by activityViewModel()
    private val tokenViewModel: TokenViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgs()
        getTokenLoginPreference()
        initViewModel()
        handleAuthentication()

    }

    private fun initArgs(){
        idStudiesUs = args.idStudies
        idProfessionalFamilies = args.professionalFamilies
        idTypeStudies = args.typeStudies
        idSchool = args.idSchool
        idNameStudies = args.idNameStudies
        initDate = args.startYear
        endDate = args.endYear
    }

    private fun handleAuthentication() {
        try {
            if (succesToken) {
                tokenViewModel.fetchCheckToken(loginToken)
                studiesViewModel.getStudiesUser(
                    idUser = idUser,
                    idStudiesUser = idStudiesUs,
                    token = loginToken
                )

                studiesViewModel.getListSchool(
                    token = loginToken
                )

                studiesViewModel.getListTypeStudies(
                    token = loginToken
                )

                studiesViewModel.getListProfessionalFamilies(
                    token = loginToken
                )

            } else {
                cleanTokenAndRedirectToLogin()
            }
        } catch (e: Exception) {
            showErrorDialog(e.toString())
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
        studiesViewModel.schoolLiveData().observe(viewLifecycleOwner, this::handleSchoolState)
        studiesViewModel.professionalFamiliesLiveData().observe(viewLifecycleOwner, this::handleProfessionalFamiliesState)
        studiesViewModel.typeStudiesLiveData().observe(viewLifecycleOwner, this::handleTypeStudiesState)
        studiesViewModel.nameStudiesLiveData().observe(viewLifecycleOwner, this::handleNameStudiesState)
        studiesViewModel.studiesUserLiveData().observe(viewLifecycleOwner, this::handleStudiesUserState)
        studiesViewModel.insertStudiesLiveData().observe(viewLifecycleOwner, this::handleInsertStudiesUserState)
        studiesViewModel.updateStudiesUserLiveData().observe(viewLifecycleOwner, this::handleUpdateStudiesUserState)
        studiesViewModel.deleteStudiesUserLiveData().observe(viewLifecycleOwner, this::handleDeleteStudiesUserState)
        tokenViewModel.getTokenLiveData().observe(viewLifecycleOwner, this::handleTokenState)
    }

    private fun handleTokenState(state: TokenDetailState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> showProgressBar(false)
            is ResourceState.Error -> showErrorDialog(state.error) { cleanTokenAndRedirectToLogin() }
            else -> {}
        }
    }

    private fun handleStudiesUserState(state: StudiesUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessOfferDetail(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleUpdateStudiesUserState(state: UpdateStudiesUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessUpdateStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleDeleteStudiesUserState(state: DeleteStudiesUserState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessDeleteStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleInsertStudiesUserState(state: InsertStudiesState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessInsertStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleTypeStudiesState(state: TypeStudiesState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessListTypeStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleNameStudiesState(state: NameStudiesState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessListNameStudies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleProfessionalFamiliesState(state: ProfessionalFamiliesState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessListProfessionalFamilies(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    private fun handleSchoolState(state: SchoolState) {
        when (state) {
            is ResourceState.Loading -> showProgressBar(true)
            is ResourceState.Success -> handleSuccessListSchool(state.result)
            is ResourceState.SuccessFaild -> handleSuccessFailed()
            is ResourceState.Error -> showErrorDialog(state.error)
            else -> {}
        }
    }

    class SchoolAdapter(context: Context, resource: Int, private val schools: List<School>) :
        ArrayAdapter<School>(context, resource, schools) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val school = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = school?.nameSchool // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val school = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                school?.nameSchool // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListSchool(school: ListSchool) {
        showProgressBar(false)

        val argSchool: Int = args.idSchool

        val adapter = SchoolAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            school.school
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsSchoolStudiesDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idSchool
        val positionOfIdSchool = school.school.indexOfFirst { it.idSchool.toInt() == argSchool }

        // Establece la selección del Spinner a la posición del elemento con idSchool
        if (positionOfIdSchool != -1) {
            spinner.setSelection(positionOfIdSchool)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = school.school[position]
                // Realiza alguna acción con el elemento seleccionado
                idSchool = selectedItem.idSchool.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }


    class ProfessionalFamiliesAdapter(
        context: Context,
        resource: Int,
        private val profFamily: List<ProfessionalFamily>
    ) : ArrayAdapter<ProfessionalFamily>(context, resource, profFamily) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val profFamily = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = profFamily?.nameFamily // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val profFamily = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                profFamily?.nameFamily // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListProfessionalFamilies(professionalFamilies: ListProfessionalFamilies) {
        showProgressBar(false)

        val profFamilies: Int = args.professionalFamilies

        val adapter = ProfessionalFamiliesAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            professionalFamilies.professionalFamilies
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsProfessionalFamiliesStudiesDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idProfessionalFamilies
        val positionOfIdProfessionalFamilies = professionalFamilies.professionalFamilies.indexOfFirst { it.idProfessionalFamilies.toInt() == profFamilies }

        // Establece la selección del Spinner a la posición del elemento con idProfessionalFamilies
        if (positionOfIdProfessionalFamilies != -1) {
            spinner.setSelection(positionOfIdProfessionalFamilies)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = professionalFamilies.professionalFamilies[position]
                // Realiza alguna acción con el elemento seleccionado
                idProfessionalFamilies = selectedItem.idProfessionalFamilies.toInt()

                getListNameStudies()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }


    class TypeStudiesAdapter(
        context: Context,
        resource: Int,
        private val typeStudy: List<TypeStudy>
    ) : ArrayAdapter<TypeStudy>(context, resource, typeStudy) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val typeStudy = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = typeStudy?.nameTypeStudies // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val typeStudy = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                typeStudy?.nameTypeStudies // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListTypeStudies(typeStudies: ListTypeStudies) {
        showProgressBar(false)

        val idType: Int = args.typeStudies

        val adapter = TypeStudiesAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            typeStudies.typeStudies
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsTypeStudiesDetailFragment
        spinner.adapter = adapter

        // Busca la posición del elemento con idType
        val positionOfIdType = typeStudies.typeStudies.indexOfFirst { it.idTypeStudies.toInt() == idType }

        // Establece la selección del Spinner a la posición del elemento con idType
        if (positionOfIdType != -1) {
            spinner.setSelection(positionOfIdType)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = typeStudies.typeStudies[position]
                // Realiza alguna acción con el elemento seleccionado

                idTypeStudies = selectedItem.idTypeStudies.toInt()

                getListNameStudies()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    class NameStudiesAdapter(
        context: Context,
        resource: Int,
        private val nameStudy: List<NameStudy>
    ) : ArrayAdapter<NameStudy>(context, resource, nameStudy) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            val nameStudy = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text = nameStudy?.nameStudies // Muestra el nombre de la escuela
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            val nameStudy = getItem(position)
            val textView = view.findViewById<TextView>(android.R.id.text1)
            textView.text =
                nameStudy?.nameStudies // Muestra el nombre de la escuela en la lista desplegable
            return view
        }
    }

    private fun handleSuccessListNameStudies(nameStudies: ListNameStudies) {
        showProgressBar(false)



        val adapter = NameStudiesAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            nameStudies.nameStudies
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.acsNameStudiesDetailFragment
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = nameStudies.nameStudies[position]
                // Realiza alguna acción con el elemento seleccionado
                idNameStudies = selectedItem.idNameStudies.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada
            }
        }
    }

    private fun handleSuccessUpdateStudies(studies: UpdateStudiesUser) {
        showProgressBar(false)

        if(studies.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessDeleteStudies(studies: DeleteStudiesUser) {
        showProgressBar(false)

        if(studies.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido borrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessInsertStudies(studies: InsertStudiesUser) {
        showProgressBar(false)

        if(studies.success){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }else{
            Toast.makeText(context, "No se ha podido insertar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSuccessOfferDetail(studies: StudiesUser) {
        showProgressBar(false)
        initUI(studies)
    }

    private fun initUI(studies: StudiesUser) {

        Toast.makeText(
            requireContext(),
            "idFragment: " + args.idFragment + "  idStudies: " + args.idStudies,
            Toast.LENGTH_SHORT
        ).show()

        binding.tieDateInitStudiesDetailFragment.text = args.startYear

        if (args.endYear.isNotEmpty()){
            binding.tieDateEndStudiesDetailFragment.text = args.endYear
        }

        binding.vBackBottomStudies.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tieDateInitStudiesDetailFragment.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tieDateInitStudiesDetailFragment.setText(selectedDate)
                initDate = selectedDate
            }
        }

        binding.tieDateEndStudiesDetailFragment.setOnClickListener {
            showDatePicker { selectedDate ->
                binding.tieDateEndStudiesDetailFragment.setText(selectedDate)
                endDate = selectedDate
            }
        }

        when (args.idFragment) {
            1 -> { //Actualizar
                binding.tvBottonSaveStudiesFragment.visibility = View.VISIBLE
                binding.tvBottonTrushStudiesFragment.visibility = View.VISIBLE
                binding.btnCreateStudiesDetailFragment.visibility = View.GONE

                binding.tvBottonSaveStudiesFragment.setOnClickListener {
                    updateStudies()
                }

                binding.tvBottonTrushStudiesFragment.setOnClickListener {
                    deleteStudies()
                }
            }

            2 -> {//Crear
                binding.tvBottonTrushStudiesFragment.visibility = View.GONE
                binding.tvBottonSaveStudiesFragment.visibility = View.GONE
                binding.btnCreateStudiesDetailFragment.visibility = View.VISIBLE

                binding.btnCreateStudiesDetailFragment.setOnClickListener {
                    createStudies()
                }
            }

            else -> {
                // Manejo para otros valores de idFragment si es necesario
            }
        }
    }

    private fun updateStudies() {

        if (idTypeStudies != 0 && idProfessionalFamilies != 0 && idSchool != 0 && idNameStudies != 0 && initDate.isNotEmpty()) {
            studiesViewModel.updateStudiesUser(
                idUser = idUser,
                idNameStudies = idNameStudies,
                startYear = initDate,
                endYear = endDate ?: "",
                idSchool = idSchool,
                idStudiesUser = idStudiesUs,
                token = loginToken
            )
        }else{
            binding.acsTypeStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsNameStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsSchoolStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsProfessionalFamiliesStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.tieDateInitStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun createStudies() {

        if (idTypeStudies != 0 && idProfessionalFamilies != 0 && idSchool != 0 && idNameStudies != 0 && initDate.isNotEmpty()) {
            studiesViewModel.insertStudies(
                idUser = idUser,
                idNameStudies = idNameStudies,
                startYear = initDate,
                endYear = endDate ?: "",
                idSchool = idSchool,
                idStudiesUser = 18,
                token = loginToken
            )
        }else{
            binding.acsTypeStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsNameStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsSchoolStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.acsProfessionalFamiliesStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
            binding.tieDateInitStudiesDetailFragment.background = R.drawable.spinner_background_error.toDrawable()
        }
    }

    private fun deleteStudies() {

        if (args.idStudies != 0) {
            studiesViewModel.deleteStudiesUser(
                idUser = idUser,
                idStudiesUser = args.idStudies,
                token = loginToken
            )
        }
    }

    private fun getListNameStudies() {

        if (idTypeStudies != 0 && idProfessionalFamilies != 0) {
            studiesViewModel.getListNameStudies(
                idTypeStudies = idTypeStudies,
                idProfessionalFamilies = idProfessionalFamilies,
                token = loginToken
            )
        }
    }

    private fun handleSuccessFailed() {
        showProgressBar(false)
        cleanTokenAndRedirectToLogin()
    }

    private fun showProgressBar(show: Boolean) {
        binding.pbStudiesDetail.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun cleanTokenAndRedirectToLogin() {
        tokenViewModel.cleanTokenPreferences()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
    }

    fun showDatePicker(listener: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = sdf.format(selectedDate.time)

                listener.invoke(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    private fun showErrorDialog(error: String, action: (() -> Unit)? = null) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(com.aprexi.praxis.myapplication.R.string.error)
            .setMessage(error)
            .setPositiveButton(com.aprexi.praxis.myapplication.R.string.action_ok) { _, _ -> action?.invoke() }
            .show()
    }

}