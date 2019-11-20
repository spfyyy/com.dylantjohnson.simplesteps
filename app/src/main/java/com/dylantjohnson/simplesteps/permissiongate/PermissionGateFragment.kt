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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dylantjohnson.simplesteps.databinding.FragmentPermissionGateBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType

/**
 * This fragment is the first page of the app, acting as an entry barrier.
 * <p>
 * Its purpose is to make sure that the necessary permissions are enabled and that the user has
 * allowed the app to access their Google account fitness data. If the user has denied activity
 * permissions or refuses to sign in to their Google account, the app will not continue and display
 * a reasoning message.
 */
class PermissionGateFragment : Fragment() {
    private lateinit var mViewModel: PermissionGateViewModel
    private lateinit var mBinding: FragmentPermissionGateBinding

    /**
     * Set up ViewModel binding and begin checking for required app permissions.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        mViewModel = ViewModelProviders.of(this).get(PermissionGateViewModel::class.java)
        mBinding = FragmentPermissionGateBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            ACTIVITY_RECOGNITION_CODE)
        return mBinding.root
    }

    /**
     * Check the results of a permission request. If the permission was granted, begin retrieving
     * Google OAuth credentials.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
            grantResults: IntArray) {
        when (requestCode) {
            ACTIVITY_RECOGNITION_CODE -> {
                if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    signInToGoogleFit()
                } else {
                    mViewModel.notifyFinishedLoading()
                }
            }
        }
    }

    /**
     * Check the results of another Activity. If the result was a successful Google login, move on
     * to the next fragment.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_PERMISSIONS_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    navigateNextFragment()
                } else {
                    mViewModel.notifyFinishedLoading()
                }
            }
        }

    }

    /**
     * Check if our app already has Google credentials. If yes, move on. Otherwise, present the user
     * with a Google OAuth consent screen.
     */
    private fun signInToGoogleFit() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (!GoogleSignIn.hasPermissions(account, FITNESS_OPTIONS)) {
            GoogleSignIn.requestPermissions(this, GOOGLE_PERMISSIONS_CODE, account, FITNESS_OPTIONS)
        } else {
            navigateNextFragment()
        }
    }

    /**
     * Navigate to the StepHistory page.
     */
    private fun navigateNextFragment() {
        findNavController().navigate(
            PermissionGateFragmentDirections.actionPermissionGateFragmentToStepHistoryFragment())
    }

    companion object {
        private const val ACTIVITY_RECOGNITION_CODE = 1
        private const val GOOGLE_PERMISSIONS_CODE = 2

        private val FITNESS_OPTIONS = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .build()
    }
}
