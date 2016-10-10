package org.pushio.datacenter.repository;

import org.pushio.datacenter.entity.PostFunction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostFunctionRepository extends PagingAndSortingRepository<PostFunction, Long>, JpaSpecificationExecutor<PostFunction>{

}
