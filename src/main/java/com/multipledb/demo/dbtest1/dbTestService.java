package com.multipledb.demo.dbtest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class dbTestService {

    @Autowired
    dbTestRepository dbTestRepository;

    public Data createData(String name, String dept, String location, String dob) {
        Data data = new Data(name, dept, location, dob);
        return dbTestRepository.save(data);
    }
}
