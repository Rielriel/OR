package com.postgresql.database.controller;

import com.postgresql.database.model.Klub;
import com.postgresql.database.repo.KlubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KlubController {
    private final KlubRepo klubRepo;

    @Autowired
    public KlubController(KlubRepo klubRepo) {
        this.klubRepo = klubRepo;
    }



    @GetMapping
    public ResponseEntity getKlub() {
        return ResponseEntity.ok(this.klubRepo.findAll());
    }

    @GetMapping("/klub")
    public List<Klub> getKlubovi(
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "searchField", required = false) String searchField) {

        if (searchText == null || searchText.isEmpty()) {
            return klubRepo.findAll();
        }

        searchText = "%" + searchText.toLowerCase() + "%";

        return switch (searchField) {
            case "naziv_liga" -> klubRepo.findByNazivLigaContainingIgnoreCase(searchText);
            case "rang" -> klubRepo.findByRangContainingIgnoreCase(searchText);
            case "broj_klubova" -> klubRepo.findByBrojKlubovaContainingIgnoreCase(searchText);
            case "krugovi" -> klubRepo.findByKrugoviContainingIgnoreCase(searchText);
            case "naziv_klub" -> klubRepo.findByNazivKlubContainingIgnoreCase(searchText);
            case "nadimak" -> klubRepo.findByNadimakContainingIgnoreCase(searchText);
            case "naziv_stadion" -> klubRepo.findByNazivStadionContainingIgnoreCase(searchText);
            case "mjesto" -> klubRepo.findByMjestoContainingIgnoreCase(searchText);
            case "godina_osnutak" -> klubRepo.findByGodinaOsnutak(Integer.parseInt(searchText));
            case "predsjednik" -> klubRepo.findByPredsjednikContainingIgnoreCase(searchText);
            case "trener" -> klubRepo.findByTrenerContainingIgnoreCase(searchText);
            case "navijaci" -> klubRepo.findByNavijaciContainingIgnoreCase(searchText);
            case "boja" -> klubRepo.findByBojaContainingIgnoreCase(searchText);
            case "prvak_hrvatska" -> klubRepo.findByPrvakHrvatska(Integer.parseInt(searchText));
            default -> klubRepo.findByAnyField(searchText);
        };
    }

    @GetMapping("/svi")
    public List<Klub> getAll(
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "searchField", required = false) String searchField) {
        return klubRepo.findByAnyField(searchText);
    }

    @GetMapping("/klub/{naziv}")
    public ResponseEntity<Klub> getKlubByNaziv(@PathVariable String naziv) {
        Klub klub = klubRepo.findById(naziv).orElse(null);
        if (klub == null) {
            return ResponseEntity
                    .status(404)
                    .header("Error-Message", "Klub s nazivom " + naziv + " nije pronađen.")
                    .build();
        }
        return ResponseEntity.ok(klub);
    }



    @GetMapping("/prva")
    public List<Klub> getFirst() {
        return klubRepo.findByRangContainingIgnoreCase(String.valueOf(1));
    }

    @GetMapping("/druga")
    public List<Klub> getSecond() {
        return klubRepo.findByRangContainingIgnoreCase(String.valueOf(2));
    }

    @GetMapping("/treca")
    public List<Klub> getThird() {
        return klubRepo.findByRangContainingIgnoreCase(String.valueOf(3));
    }

    @PostMapping("/dodaj")
    public ResponseEntity<String> addKlub(@RequestBody Klub klub) {
        klubRepo.save(klub);
        return ResponseEntity.ok("Klub uspješno dodan.");
    }


    @PutMapping("/azuriraj/{naziv}")
    public ResponseEntity<String> azurirajKlub(@PathVariable String naziv, @RequestBody Klub klub) {
        if (!klubRepo.existsById(naziv)) {
            return ResponseEntity.notFound().build();
        }
        System.out.println(klub.toString());
        klub.setNaziv_klub(naziv);
        klubRepo.save(klub);

        return ResponseEntity.ok("Klub uspješno ažuriran.");
    }


    @DeleteMapping("/obrisi/{naziv}")
    public ResponseEntity<String> obrisiKlub(@PathVariable String naziv) {
        if (!klubRepo.existsById(naziv)) {
            return ResponseEntity.notFound().build();
        }
        klubRepo.deleteById(naziv);

        return ResponseEntity.ok("Klub uspješno obrisan.");
    }

}