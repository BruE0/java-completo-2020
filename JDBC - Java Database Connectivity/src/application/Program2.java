package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=".repeat(10) + " TEST 1: department findById " + "=".repeat(10));
        Department dep = departmentDao.findById(3);
        System.out.println(dep);

        System.out.println("=".repeat(10) + " TEST 2: department findAll " + "=".repeat(10));
        List<Department> allDeps = departmentDao.findAll();
        allDeps.forEach(System.out::println);

        System.out.println("=".repeat(10) + " TEST 3: department insert " + "=".repeat(10));
        dep = new Department(null, "Rocketry");
        departmentDao.insert(dep);
        System.out.println("Inserted! New id = " + dep.getId());

        System.out.println("=".repeat(10) + " TEST 5: department update " + "=".repeat(10));
        dep = departmentDao.findById(6);
        dep.setName("Astrophysics");
        departmentDao.update(dep);
        System.out.println("Update completed! Changed department to\n" + dep);

        System.out.println("=".repeat(10) + " TEST 6: department delete " + "=".repeat(10));
        int idToDelete = 7;
        departmentDao.deleteById(idToDelete);
        System.out.println("Delete completed. Deleted id = " + idToDelete);

    }
}
