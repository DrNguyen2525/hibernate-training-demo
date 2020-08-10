package org.sun.asterisk.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salary_grade")
public class SalaryGrade {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "low_salary", nullable = false)
    private Float lowSalary;

    @Column(name = "high_salary", nullable = false)
    private Float highSalary;

    public SalaryGrade() {
    }

    public SalaryGrade(Integer grade, Float lowSalary, Float highSalary) {
        this.id = grade;
        this.lowSalary = lowSalary;
        this.highSalary = highSalary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLowSalary() {
        return lowSalary;
    }

    public void setLowSalary(Float lowSalary) {
        this.lowSalary = lowSalary;
    }

    public Float getHighSalary() {
        return highSalary;
    }

    public void setHighSalary(Float highSalary) {
        this.highSalary = highSalary;
    }
}
