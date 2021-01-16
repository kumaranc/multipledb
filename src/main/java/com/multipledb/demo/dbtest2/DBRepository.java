package com.multipledb.demo.dbtest2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBRepository extends JpaRepository<DataTest, Integer> {
}
