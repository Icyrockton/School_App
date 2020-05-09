package com.icyrockton.school_app.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.icyrockton.school_app.R
import com.icyrockton.school_app.databinding.FragmentBasicProfileBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BasicProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by sharedViewModel()
    private lateinit var binding: FragmentBasicProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasicProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=this
        binding.vm=viewModel
        initInfo()
    }

    private fun initInfo() {
//        viewModel.profileInfo.observe(viewLifecycleOwner, Observer {
//            binding.basicStuAdmissionForm.text = it.admission_form
//            binding.basicStuAdmissionSource.text =it.admission_source
//            binding.basicStuBirthDate.text =it.birth_date
//            binding.basicStuCampus.text =it.campus
//            binding.basicStuCity.text =it.city
//            binding.basicStuClassName.text =it.class_name
//            binding.basicStuGender.text =it.gender
//            binding.basicStuGrade.text =it.grade
//            binding.basicStuGraduatedSchool.text =it.graduated_school
//            binding.basicStuHomeAddress.text =it.home_address
//            binding.basicStuID.text =it.student_ID
//            binding.basicStuInstitute.text =it.institute
//            binding.basicStuIDNumber.text =
//                binding.basicStuMajor.text =
//                binding.basicStuName.text =
//                binding.basicStuCity.text =
//                binding.basicStuNational.text =
//                binding.basicStuNativePlace.text =
//                binding.basicStuPassportName.text =
//                binding.basicStuPhoneNumber.text =
//                binding.basicStuPoliticalStatus.text =
//                binding.basicStuProvince.text =
//                binding.basicStuState.text =
//                binding.basicStuStudentCategory.text =
//                binding.basicStuStudentTag.text =
//                binding.basicStuSubject.text =
//                binding.basicStuStudentSource.text =
//                binding.basicStuTravelRange.text =
//        })
    }

    companion object {
        private const val TAG = "BasicProfileFragment"
    }
}