package edu.miu.cs.cs489appsd.lab1a.productmgmtapp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.miu.cs.cs489appsd.lab1.productmgmtapp.model.Product;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class ProductMgmtApp {
    public static void main(String[] args) throws Exception {
        Product[] products = seed();
        printProducts(products);
    }

    private static Product[] seed() {
        // NO named parameters; pass by position. productId as BigInteger strings.
        return new Product[] {
                new Product(new BigInteger("31288741190182539912"), "Banana", LocalDate.parse("2025-01-24"), 124, new BigDecimal("0.55")),
                new Product(new BigInteger("29274582650152771644"), "Apple",  LocalDate.parse("2024-12-09"), 18,  new BigDecimal("1.09")),
                new Product(new BigInteger("91899274600128155167"), "Carrot", LocalDate.parse("2025-03-31"), 89,  new BigDecimal("2.99")),
                new Product(new BigInteger("31288741190182539913"), "Banana", LocalDate.parse("2025-02-13"), 240, new BigDecimal("0.65"))
        };
    }

    public static void printProducts(Product[] products) throws Exception {
        Arrays.sort(products,
                Comparator.comparing(Product::getName)
                        .thenComparing(Product::getUnitPrice, Comparator.reverseOrder()));

        // JSON mapper: auto-register modules + keep ISO dates (not timestamps)
        ObjectMapper json = new ObjectMapper()
                .findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println("=== JSON ===");
        System.out.println(json.writerWithDefaultPrettyPrinter().writeValueAsString(products));

        // XML mapper: same
        XmlMapper xml = new XmlMapper();
        xml.findAndRegisterModules();
        xml.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        System.out.println("=== XML ===");
        System.out.println(xml.writerWithDefaultPrettyPrinter().writeValueAsString(products));

        // CSV output (unchanged)...
        System.out.println("=== CSV ===");
        System.out.println("productId,name,dateSupplied,quantityInStock,unitPrice");
        for (Product p : products) {
            System.out.printf("%s,%s,%s,%d,%s%n",
                    p.getProductId(), p.getName(), p.getDateSupplied(),
                    p.getQuantityInStock(), p.getUnitPrice());
        }
    }
}
