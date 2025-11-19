// src/main/java/tn/esprit/twin/twin2demo/controllers/ChaineRestaurationController.java
package tn.esprit.twin.twin2demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.twin2demo.entities.ChaineRestauration;
import tn.esprit.twin.twin2demo.repository.ChaineRestaurationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/chains")
@Tag(name = "Chains", description = "CRUD des chaînes de restauration")
public class ChaineRestaurationController {

    @Autowired
    private ChaineRestaurationRepository repo;

    @Operation(summary = "Créer une chaîne")
    @PostMapping
    public ChaineRestauration create(@RequestBody ChaineRestauration c) {
        if (c.getLibelle() == null || c.getLibelle().isBlank())
            throw new IllegalArgumentException("libelle est obligatoire");
        return repo.save(c);
    }

    @Operation(summary = "Lister les chaînes")
    @GetMapping
    public List<ChaineRestauration> all() {
        return repo.findAll();
    }
}
