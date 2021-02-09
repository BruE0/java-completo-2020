package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
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

    }
}
