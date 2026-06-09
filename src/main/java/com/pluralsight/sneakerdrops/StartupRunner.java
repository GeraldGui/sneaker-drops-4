package com.pluralsight.sneakerdrops;

import com.pluralsight.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrops.service.DropService;
import com.pluralsight.sneakerdrops.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final SneakerRepository sneakerRepository;

    private final DropService dropService;
    private final InventoryService inventoryService;

    @Autowired
    public StartupRunner(BrandRepository brandRepository, SneakerRepository sneakerRepository, DropService dropService, InventoryService inventoryService) {
        this.brandRepository = brandRepository;
        this.sneakerRepository = sneakerRepository;
        this.dropService = dropService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println(dropService.getStatus());
        System.out.println(inventoryService.getModule());

        for (Brand brand : brandRepository.findAll()) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Sneaker Library ---");
            System.out.println("1) List all sneakers");
            System.out.println("0) Quit");
            System.out.print("Your Choice: ");

            switch (scanner.nextInt()) {
                case 1 ->listSneakers();
                case 0 -> running = false;
                default -> System.out.println("Wrong Input!");
            }
        }
    }


    private void listSneakers() {
        System.out.println("You have " + sneakerRepository.count() + " sneakers:");
        for (Sneaker sneaker : sneakerRepository.findAll()) {
            System.out.printf("%d - %s ($%.2f %d)%n", sneaker.getId(), sneaker.getModel(), sneaker.getPrice(), sneaker.getReleaseYear());

        }
    }

    private void seedData() {
        if(brandRepository.count() == 0) {
            brandRepository.save(new Brand("Nike"));
            brandRepository.save(new Brand("Jordan"));
            brandRepository.save(new Brand("Adidas"));
            brandRepository.save(new Brand("New Balance"));
            brandRepository.save(new Brand("Puma"));
        }

        if (sneakerRepository.count() == 0) {
            sneakerRepository.save(new Sneaker("Air Force 1'07", 115.00, 1982));
            sneakerRepository.save(new Sneaker("Air Jordan 13", 150.00, 1997));
            sneakerRepository.save(new Sneaker("Samba OG", 100.00, 1950));
            sneakerRepository.save(new Sneaker("574 Core", 100.00, 1988));
            sneakerRepository.save(new Sneaker("Suede Classic", 75.00, 1968));
        }
    }
}
