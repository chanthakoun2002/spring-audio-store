package com.example.demo.bootstrap;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.OutsourcedPartRepository;
import com.example.demo.repositories.PartRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Component
public class BootStrapData implements CommandLineRunner {

    private final PartRepository partRepository;
    private final ProductRepository productRepository;

    private final OutsourcedPartRepository outsourcedPartRepository;

    public BootStrapData(PartRepository partRepository, ProductRepository productRepository, OutsourcedPartRepository outsourcedPartRepository) {
        this.partRepository = partRepository;
        this.productRepository = productRepository;
        this.outsourcedPartRepository=outsourcedPartRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Load data only when tables are empty
        if (partRepository.count() == 0 &&  productRepository.count() == 0) {
            OutsourcedPart tonearm = new OutsourcedPart();
            tonearm.setName("Tonearm");
            tonearm.setPrice(99.99);
            tonearm.setInv(10);
            tonearm.setMin(0);
            tonearm.setMax(100);
            tonearm.setCompanyName("SpinCo");

            OutsourcedPart platter = new OutsourcedPart();
            platter.setName("Platter");
            platter.setPrice(59.99);
            platter.setInv(8);
            platter.setMin(0);
            platter.setMax(100);
            platter.setCompanyName("SpinCo");

            OutsourcedPart cartridge = new OutsourcedPart();
            cartridge.setName("Cartridge");
            cartridge.setPrice(49.99);
            cartridge.setInv(15);
            cartridge.setMin(0);
            cartridge.setMax(100);
            cartridge.setCompanyName("HiFi Labs");

            OutsourcedPart motor = new OutsourcedPart();
            motor.setName("Motor");
            motor.setPrice(15.99);
            motor.setInv(20);
            motor.setMin(0);
            motor.setMax(100);
            motor.setCompanyName("Analog Supplies");

            OutsourcedPart dustCover = new OutsourcedPart();
            dustCover.setName("Dust Cover");
            dustCover.setPrice(29.99);
            dustCover.setInv(5);
            dustCover.setMin(0);
            dustCover.setMax(100);
            dustCover.setCompanyName("Vinyl Essentials");

            // all parts are saved to db
            outsourcedPartRepository.saveAll(
                    List.of(tonearm, platter, cartridge, motor, dustCover)
            );

            // product with price and current inventory
            Product basic = new Product("Basic Player", 300.00, 4);
            Product hifi = new Product("Hi-Fi Turntable", 450.00, 2);
            Product dj = new Product("DJ Record Plater", 399.99, 3);
            Product audiophile = new Product("Audiophile Setup", 699.99, 1);
            Product portable = new Product("Portable Record Player", 149.00, 6);

            productRepository.saveAll(List.of(basic, hifi, dj, audiophile, portable));
            // Create product first then add parts to them as we add onto them

            var allProducts = List.of(basic, hifi, dj, audiophile, portable);
            var allParts = List.of(tonearm, platter, cartridge, motor, dustCover);
            // All products get an associated part and vice versa
            for (Product prod : allProducts) {
                prod.getParts().addAll(allParts);
            }
            for (Part part : allParts) {
                part.getProducts().addAll(allProducts);
            }

            partRepository.saveAll(allParts);

            //partRepository.saveAll(List.of(tonearm, platter, cartridge, motor, dustCover));


            System.out.println("----------Inventory loaded-------------");
        }



       /*
        OutsourcedPart o= new OutsourcedPart();
        o.setCompanyName("Western Governors University");
        o.setName("out test");
        o.setInv(5);
        o.setPrice(20.0);
        o.setId(100L);
        outsourcedPartRepository.save(o);
        OutsourcedPart thePart=null;
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            if(part.getName().equals("out test"))thePart=part;
        }

        System.out.println(thePart.getCompanyName());
        */
        List<OutsourcedPart> outsourcedParts=(List<OutsourcedPart>) outsourcedPartRepository.findAll();
        for(OutsourcedPart part:outsourcedParts){
            System.out.println(part.getName()+" "+part.getCompanyName());
        }

        /*
        Product bicycle= new Product("bicycle",100.0,15);
        Product unicycle= new Product("unicycle",100.0,15);
        productRepository.save(bicycle);
        productRepository.save(unicycle);
        */

        System.out.println("Started in Bootstrap");
        System.out.println("Number of Products"+productRepository.count());
        System.out.println(productRepository.findAll());
        System.out.println("Number of Parts"+partRepository.count());
        System.out.println(partRepository.findAll());

    }
}
