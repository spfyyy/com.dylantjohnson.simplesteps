package com.dylantjohnson.simplesteps.data

import android.content.Context
import android.util.Log
import com.dylantjohnson.simplesteps.models.StepsStat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.HistoryClient
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

/**
 * A useful utility class for dealing with the Google Fit API.
 * <p>
 * This class assumes that the user has already signed in to Google and given the necessary
 * permissions. If not, an exception will be thrown.
 * Since the API can take some time to get results, I've wrapped it in Kotlin coroutines for easy
 * concurrency.
 */
class FitnessData(context: Context) {
    private val mClient: HistoryClient

    init {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        mClient = Fitness.getHistoryClient(context, account!!)
    }

    suspend fun getStepHistory(): List<StepsStat> = withContext(Dispatchers.IO) {
        val calendar = Calendar.getInstance().apply {
            time = Date()
        }
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.DATE, -14)
        val startTime = calendar.timeInMillis

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .bucketByTime(1, TimeUnit.DAYS)
            .enableServerQueries()
            .build()

        val response = Tasks.await(mClient.readData(readRequest))
        response.buckets.map { bucket ->
            val day = Date(bucket.getEndTime(TimeUnit.MILLISECONDS))
            val dataSet = bucket.dataSets[0]
            val total = if (dataSet.dataPoints.size > 0) {
                dataSet.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
            } else {
                0
            }
            StepsStat(day, total)
        }
    }
}
