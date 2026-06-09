package com.pluralsight.sneakerdrops;

import com.pluralsight.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrops.service.DropService;
import com.pluralsight.sneakerdrops.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final DropService dropService;
    private final InventoryService inventoryService;

    @Autowired
    public StartupRunner(BrandRepository brandRepository, DropService dropService, InventoryService inventoryService) {
        this.brandRepository = brandRepository;
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
    }

    private void seedData() {
        if(brandRepository.count() == 0) {
            brandRepository.save(new Brand("Nike"));
            brandRepository.save(new Brand("Jordan"));
            brandRepository.save(new Brand("Adidas"));
            brandRepository.save(new Brand("New Balance"));
            brandRepository.save(new Brand("Puma"));
        }
    }
}
