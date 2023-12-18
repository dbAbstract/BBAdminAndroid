package za.co.bb.feature_input_work.view

import za.co.bb.feature_input_work.domain.usecase.GetWorkStatuses
import za.co.bb.work_hours.di.WorkHoursDependencyContainer

internal object DependencyContainer {
    val getWorkStatuses: GetWorkStatuses
        get() = GetWorkStatuses(workHoursRepository = WorkHoursDependencyContainer.workHoursRepository)
}