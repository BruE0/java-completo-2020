package application;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=".repeat(10) + " TEST 1: seller findById " + "=".repeat(10));
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n" + "=".repeat(10) + " TEST 2: seller findByDepartmentId " + "=".repeat(10));
        List<Seller> sellersWithDepId2 = sellerDao.findByDepartmentId(2);
        sellersWithDepId2.forEach(System.out::println);

        System.out.println("\n" + "=".repeat(10) + " TEST 3: seller findAll " + "=".repeat(10));
        List<Seller> allSellers = sellerDao.findAll();
        allSellers.forEach(System.out::println);

        System.out.println("\n" + "=".repeat(10) + " TEST 4: seller insert " + "=".repeat(10));
        Department noNameDep = new Department(2, null);
        seller = new Seller(null, "Greg", "greg@gmail.com", LocalDate.of(2020, 1, 1), 4000.0,
                noNameDep);
        sellerDao.insert(seller);
        System.out.println("Inserted! New id = " + seller.getId());

        System.out.println("\n" + "=".repeat(10) + " TEST 5: seller update " + "=".repeat(10));
        seller = sellerDao.findById(1);
        seller.setName("Martha Wayne");
        sellerDao.update(seller);
        System.out.println("Update completed! Changed seller to\n" + seller);

        System.out.println("\n" + "=".repeat(10) + " TEST 6: seller delete " + "=".repeat(10));
        int idToDelete = 8;
        sellerDao.deleteById(idToDelete);
        System.out.println("Delete completed. Deleted id = " + idToDelete);
    }
}
