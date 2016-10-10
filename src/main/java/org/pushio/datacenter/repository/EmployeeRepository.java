package org.pushio.datacenter.repository;

import org.pushio.datacenter.entity.Employee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{
}
