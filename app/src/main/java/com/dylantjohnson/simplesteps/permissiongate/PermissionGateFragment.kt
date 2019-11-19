package com.dylantjohnson.simplesteps.permissiongate

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dylantjohnson.simplesteps.databinding.FragmentPermissionGateBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType


class PermissionGateFragment : Fragment() {
    private lateinit var mBinding: FragmentPermissionGateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        mBinding = FragmentPermissionGateBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            ACTIVITY_RECOGNITION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
            grantResults: IntArray) {
        when (requestCode) {
            ACTIVITY_RECOGNITION_CODE -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    signInToGoogleFit()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GOOGLE_PERMISSIONS_CODE -> navigateNextFragment()
            }
        }
    }

    private fun signInToGoogleFit() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(this, GOOGLE_PERMISSIONS_CODE, account, fitnessOptions)
        } else {
            navigateNextFragment()
        }
    }

    private fun navigateNextFragment() {
        findNavController().navigate(
            PermissionGateFragmentDirections.actionPermissionGateFragmentToStepHistoryFragment())
    }

    companion object {
        private const val ACTIVITY_RECOGNITION_CODE = 1
        private const val GOOGLE_PERMISSIONS_CODE = 2

        private val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
    }
}
