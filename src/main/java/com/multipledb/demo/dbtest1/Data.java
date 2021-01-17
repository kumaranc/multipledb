package com.multipledb.demo.dbtest1;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dept")
    private String dept;

    @Column(name = "location")
    private String location;

    @Basic(optional = true)
    private Date dob;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Data(String name, String dept, String location, Date dob) {
        this.name = name;
        this.dept = dept;
        this.location = location;
        this.dob = dob;
    }

    public Data() {
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", location='" + location + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }
}
