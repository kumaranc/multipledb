package com.multipledb.demo.dbtest1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface dbTestRepository extends JpaRepository<Data, Integer>, JpaSpecificationExecutor<Data> {
}
