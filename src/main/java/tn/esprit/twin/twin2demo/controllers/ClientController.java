package tn.esprit.twin.twin2demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.twin2demo.entities.Client;
import tn.esprit.twin.twin2demo.service.IClientService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    // ---------- CRUD ----------
    @GetMapping
    public List<Client> all() {
        return clientService.retrieveAllClients();
    }

    @GetMapping("/{id}")
    public Client one(@PathVariable Long id) {
        return clientService.retrieveClient(id);
    }

    @PostMapping
    public Client create(@RequestBody Client c) {
        if (c.getNom() == null || c.getNom().isBlank()) {
            throw new IllegalArgumentException("Le champ 'nom' est obligatoire.");
        }
        return clientService.addClient(c);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client c) {
        c.setIdClient(id);
        return clientService.updateClient(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.removeClient(id);
    }

    // ---------- Recherches ----------
    @GetMapping("/search/nom")
    public List<Client> searchByNom(@RequestParam String q) {
        return clientService.searchByNom(q);
    }

    @GetMapping("/search/after")
    public List<Client> registeredAfter(
            @RequestParam("d") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date d) {
        return clientService.searchRegisteredAfter(d);
    }

    @GetMapping("/search/email-domain")
    public List<Client> byEmailDomain(@RequestParam String like) {
        return clientService.searchByEmailDomain(like);
    }
}
