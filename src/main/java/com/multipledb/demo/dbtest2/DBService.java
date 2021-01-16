package com.multipledb.demo.dbtest2;

import com.multipledb.demo.dbtest1.Data;
import com.multipledb.demo.dbtest1.dbTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DBService {

    @Autowired
    DBRepository dbRepository1;

    public DataTest createData(String name, String dept, String location, String dob) {
        DataTest data = new DataTest(name, dept, location, dob);
        return dbRepository1.save(data);
    }
}
