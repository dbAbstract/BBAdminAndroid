package za.co.bb.wages.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import za.co.bb.wages.data.wages.WageRepositoryImpl
import za.co.bb.wages.domain.repository.WageRepository

object WagesDependencyContainer {
    private val firebaseFirestore by lazy {
        Firebase.firestore
    }

    val wageRepository: WageRepository by lazy {
        WageRepositoryImpl(firebaseFirestore = firebaseFirestore)
    }
}