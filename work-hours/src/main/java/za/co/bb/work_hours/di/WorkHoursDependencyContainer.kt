package za.co.bb.work_hours.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import za.co.bb.work_hours.data.WorkHoursRepositoryImpl
import za.co.bb.work_hours.domain.WorkHoursRepository

object WorkHoursDependencyContainer {
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val workHoursRepository: WorkHoursRepository by lazy {
        WorkHoursRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }
}