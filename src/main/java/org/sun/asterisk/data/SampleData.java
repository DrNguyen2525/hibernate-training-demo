package org.sun.asterisk.data;

import org.sun.asterisk.entities.Department;
import org.sun.asterisk.entities.Employee;
import org.sun.asterisk.entities.SalaryGrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SampleData {

    public static final List<Department> departments = new ArrayList<>(
            Arrays.asList(
                    new Department(10, "Accounting", "New York"),
                    new Department(20, "Research", "Dallas"),
                    new Department(30, "Sales", "Chicago"),
                    new Department(40, "Operations", "Boston")
            )
    );

    public static final SalaryGrade salaryGrade = new SalaryGrade(1, 9999F, 3001F);

    public static final Employee president = new Employee(7839L, "King", "president", null, new Date(374778000000L), 5000F, departments.get(0));

    public static final List<Employee> managers = new ArrayList<>(
            Arrays.asList(
                    new Employee(7566L, "Jones", "manager", president, new Date(354992400000L), 2975F, departments.get(1)),
                    new Employee(7698L, "Blake", "manager", president, new Date(357498000000L), 2850F, departments.get(2)),
                    new Employee(7782L, "Clark", "manager", president, new Date(360867600000L), 2450F, departments.get(2))
            )
    );

    public static final List<Employee> analysts = new ArrayList<>(
            Arrays.asList(
                    new Employee(7902L, "Ford", "analyst", managers.get(0), new Date(376160400000L), 3000F, departments.get(1)),
                    new Employee(7788L, "Scott", "analyst", managers.get(0), new Date(545763600000L), 3000F, departments.get(1))
            )
    );

    public static final List<Employee> salesmen = new ArrayList<>(
            Arrays.asList(
                    new Employee(7499L, "Allen", "salesman", managers.get(1), new Date(350586000000L), 1600F, departments.get(2)),
                    new Employee(7521L, "Ward", "salesman", managers.get(1), new Date(351622800000L), 1250F, departments.get(2)),
                    new Employee(7654L, "Martin", "salesman", managers.get(1), new Date(370458000000L), 1250F, departments.get(2)),
                    new Employee(7844L, "Turner", "salesman", managers.get(1), new Date(368730000000L), 1500F, departments.get(2))
            )
    );

    public static final List<Employee> clerks = new ArrayList<>(
            Arrays.asList(
                    new Employee(7369L, "Smith", "clerk", analysts.get(1), new Date(345834000000L), 800F, departments.get(1)),
                    new Employee(7876L, "Adams", "clerk", managers.get(1), new Date(548701200000L), 1100F, departments.get(1)),
                    new Employee(7900L, "Adams", "clerk", managers.get(1), new Date(376160400000L), 950F, departments.get(2)),
                    new Employee(7934L, "Miller", "clerk", managers.get(1), new Date(380566800000L), 1300F, departments.get(0))
            )
    );
}
