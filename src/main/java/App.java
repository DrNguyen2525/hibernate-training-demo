import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.sun.asterisk.data.SampleData;
import org.sun.asterisk.entities.Department;
import org.sun.asterisk.entities.Employee;
import org.sun.asterisk.utils.HibernateUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public final static Logger logger = Logger.getLogger(App.class);

    private static void insertSampleData() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        Transaction transaction = session.getTransaction();

        boolean isTableEmpty;

        // Insert sample Department data
        logger.info("Checking for Department data...");

        try {
            // Check if department table is empty
            isTableEmpty = session.createNativeQuery("SELECT 1 FROM department")
                    .setMaxResults(1)
                    .list()
                    .isEmpty();

            if (isTableEmpty) {
                transaction.begin();
                logger.info("Inserting sample department data...");

                for (Department department : SampleData.departments)
                    session.save(department);
                session.flush();
                session.clear();

                transaction.commit();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        // Insert sample Employee data
        logger.info("Checking for Employee data...");

        try {
            session = HibernateUtils.getSessionFactory().openSession();

            // Check if employee table is empty
            isTableEmpty = session.createNativeQuery("SELECT 1 FROM employee")
                    .setMaxResults(1)
                    .list()
                    .isEmpty();

            if (isTableEmpty) {
                List<Employee> employees = new ArrayList<>();
                employees.add(SampleData.president);
                employees.addAll(SampleData.managers);
                employees.addAll(SampleData.analysts);
                employees.addAll(SampleData.salesmen);
                employees.addAll(SampleData.clerks);

                transaction = session.getTransaction();

                transaction.begin();
                logger.info("Inserting sample employee data...");

                for (Employee employee : employees)
                    session.save(employee);
                session.flush();
                session.clear();

                transaction.commit();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        // Insert sample Salary Grade data
        logger.info("Checking for Salary Grade data...");
        try {
            session = HibernateUtils.getSessionFactory().openSession();

            // Check if salary_grade table is empty
            isTableEmpty = session.createNativeQuery("SELECT 1 FROM salary_grade")
                    .setMaxResults(1)
                    .list()
                    .isEmpty();

            if (isTableEmpty) {
                transaction = session.getTransaction();

                transaction.begin();
                logger.info("Inserting sample salary grade data...");

                session.save(SampleData.salaryGrade);
                session.flush();
                session.clear();

                transaction.commit();
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void demoFetching() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
            Root<Department> root = criteriaQuery.from(Department.class);
            criteriaQuery.select(root);
            Query<Department> query = session.createQuery(criteriaQuery);
            List<Department> departments = query.getResultList();

            for (Department department : departments) {
                System.out.println("\nDepartment: " + department.getDeptName());
                for (Employee employee : department.getEmployees())
                    System.out.printf("- Employee: %s %s\n",
                            employee.getEmpNo(),
                            employee.getEmpName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void demoObjectCaching() {
        Session session1 = HibernateUtils.getSessionFactory().openSession();
        Session session2 = HibernateUtils.getSessionFactory().openSession();

        try {
            logger.info("\n----------- Session 1 -----------\n");

            Employee employee;

            for (int i = 0; i < 5; i++) {
                System.out.printf("\n#%d:\n", i + 1);
                employee = session1.get(Employee.class, 7369L);

                System.out.printf("Employee: %s %s\n",
                        employee.getEmpNo(),
                        employee.getEmpName());
            }

            logger.info("\n----------- Session 2 -----------\n");

            employee = session2.get(Employee.class, 7369L);

            System.out.printf("\nEmployee: %s %s\n",
                    employee.getEmpNo(),
                    employee.getEmpName());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session1.close();
            session2.close();
        }
    }

    private static void demoQueryCaching() {
        Session session = HibernateUtils.getSessionFactory().openSession();

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> root = criteriaQuery.from(Employee.class);
            Predicate[] predicates = new Predicate[1];
            predicates[0] = criteriaBuilder.equal(root.get("id"), 7566L);

            criteriaQuery.select(root).where(predicates);

            Query<Employee> query = session.createQuery(criteriaQuery);
            query.setCacheable(true);
            query.setCacheRegion("employeeQueryCache");

            List<Employee> employees;

            for (int i = 0; i < 5; i++) {
                System.out.printf("\n#%d:\n", i + 1);
                employees = query.getResultList();

                for (Employee e : employees) {
                    System.out.printf("Employee: %s %s\n",
                            e.getEmpNo(),
                            e.getEmpName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {

        insertSampleData();

        Scanner input = new Scanner(System.in);
        int option = 0;

        do {
            System.out.println("\n1. Fetching");
            System.out.println("2. Object caching");
            System.out.println("3. Query caching");
            System.out.println("0. Exit");
            System.out.println("---------------------------------");
            System.out.print("Choose an option: ");

            try {
                option = input.nextInt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            switch (option) {
                case 1:
                    demoFetching();
                    break;

                case 2:
                    demoObjectCaching();
                    break;

                case 3:
                    demoQueryCaching();
                    break;

                default:
                    break;
            }

        }
        while (option != 0);

        HibernateUtils.shutdown();
        System.exit(0);
    }
}
