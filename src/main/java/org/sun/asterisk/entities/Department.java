package org.sun.asterisk.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"dept_no"})})
@Cacheable
@Cache(region = "departmentCache",
        usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "dept_no", length = 20, nullable = false)
    private String deptNo;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "department")
    @Fetch(FetchMode.SUBSELECT)
    private List<Employee> employees = new ArrayList<>(0);

    public Department() {
    }

    public Department(Integer id, String deptName, String location) {
        this.id = id;
        this.deptNo = "D" + this.id;
        this.deptName = deptName;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
