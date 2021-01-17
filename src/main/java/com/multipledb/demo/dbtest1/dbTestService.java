package com.multipledb.demo.dbtest1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class dbTestService {

    @Autowired
    dbTestRepository dbTestRepository;

    public Data createData(String name, String dept, String location, Date dob) {
        Data data = new Data(name, dept, location, dob);
        return dbTestRepository.save(data);
    }

    public List<Data> getData(String dept, Date dob, String location, String name, Pageable pageable) {

        return  dbTestRepository.findAll(new Specification<Data>() {
            @Override
            public Predicate toPredicate(Root<Data> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(Objects.nonNull(dept)) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(root.get("dept"), "%" + dept +"%"));
                }

                if(Objects.nonNull(location)) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(root.get("location"), "%" + location +"%"));
                }

                if(Objects.nonNull(name)) {
                    predicate = criteriaBuilder.and(predicate,
                            criteriaBuilder.like(root.get("name"), "%" + name +"%"));
                }

                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")), criteriaBuilder.asc(root.get("id")));
                return predicate;
            }
        }, pageable).getContent();
    }
}
