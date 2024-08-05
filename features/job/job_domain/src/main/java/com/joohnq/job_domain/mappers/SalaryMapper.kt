package com.joohnq.job_domain.mappers

import com.joohnq.job_domain.entities.Salary

object SalaryMapper {
				fun getFormattedSalary(salary: Salary): String = salary.run {
								return "$symbol$entry - $end/$time"
				}
}