package org.pushio.datacenter.repository;

import org.pushio.datacenter.entity.EmployeePosts;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeePostsRepository extends PagingAndSortingRepository<EmployeePosts, Long>, JpaSpecificationExecutor<EmployeePosts>{

}
