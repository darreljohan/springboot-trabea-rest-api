package com.iglo.trabea.partTimeEmployee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimeEmployeeRepository extends JpaRepository<PartTimeEmployee, Integer> {


}
