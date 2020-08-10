package org.sun.asterisk.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"emp_no"})})
@Cacheable
@Cache(region = "employeeCache",
        usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "emp_no", length = 20, nullable = false)
    private String empNo;

    @Column(name = "emp_name", length = 50, nullable = false)
    private String empName;

    @Column(name = "job", length = 30, nullable = false)
    private String job;

    @Column(name = "hire_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @Column(name = "salary", nullable = false)
    private Float salary;

    @Column(name = "image", length = 1111111)
    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "mng_id")
    private Employee manager;

    @OneToMany(mappedBy = "employee")
    private List<Timekeeper> timekeeper = new ArrayList<>(0);

    public Employee() {
    }

    public Employee(Long id, String empName, String job, Employee manager,
                    Date hireDate, Float salary, Department department) {
        this.id = id;
        this.empNo = "E" + this.id;
        this.empName = empName;
        this.job = job;
        this.manager = manager;
        this.hireDate = hireDate;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Timekeeper> getTimekeeper() {
        return timekeeper;
    }

    public void setTimekeeper(List<Timekeeper> timekeeper) {
        this.timekeeper = timekeeper;
    }
}
