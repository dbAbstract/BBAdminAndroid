package za.co.bb.work_status.di

import za.co.bb.work_hours.di.WorkHoursDependencyContainer
import za.co.bb.work_status.domain.usecase.GetWorkStatuses

internal object DependencyContainer {
    val getWorkStatuses: GetWorkStatuses
        get() = GetWorkStatuses(workHoursRepository = WorkHoursDependencyContainer.workHoursRepository)
}