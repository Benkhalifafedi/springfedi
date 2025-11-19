package tn.esprit.twin.twin2demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.twin2demo.entities.ChefCuisinier;
import tn.esprit.twin.twin2demo.repository.ChefCuisinierRepository;

import java.util.List;

@RestController
@RequestMapping("/api/chefs")
public class ChefCuisinierController {

    @Autowired
    private ChefCuisinierRepository chefRepo;

    @PostMapping("/add")
    public ChefCuisinier addChef(@RequestBody ChefCuisinier chef) {
        return chefRepo.save(chef);
    }

    @GetMapping("/all")
    public List<ChefCuisinier> getAll() {
        return chefRepo.findAll();
    }
}
