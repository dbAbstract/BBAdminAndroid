package za.co.bb.wages.di

import za.co.bb.core.di.CoreDependencyContainer
import za.co.bb.wages.data.WageRepositoryImpl
import za.co.bb.wages.domain.WageRepository

object DependencyContainer {
    val wageRepository: WageRepository by lazy {
        WageRepositoryImpl(firebaseFirestore = CoreDependencyContainer.firebaseFirestore)
    }
}