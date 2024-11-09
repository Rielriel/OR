package com.postgresql.database.controller;

import com.postgresql.database.model.Klub;
import com.postgresql.database.repo.KlubRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        switch (searchField) {
            case "naziv_liga":
                return klubRepo.findByNazivLigaContainingIgnoreCase(searchText);
            case "rang":
                return klubRepo.findByRangContainingIgnoreCase(searchText);
            case "broj_klubova":
                return klubRepo.findByBrojKlubovaContainingIgnoreCase(searchText);
            case "krugovi":
                return klubRepo.findByKrugoviContainingIgnoreCase(searchText);
            case "naziv_klub":
                return klubRepo.findByNazivKlubContainingIgnoreCase(searchText);
            case "nadimak":
                return klubRepo.findByNadimakContainingIgnoreCase(searchText);
            case "naziv_stadion":
                return klubRepo.findByNazivStadionContainingIgnoreCase(searchText);
            case "mjesto":
                return klubRepo.findByMjestoContainingIgnoreCase(searchText);
            case "godina_osnutak":
                return klubRepo.findByGodinaOsnutak(Integer.parseInt(searchText));
            case "predsjednik":
                return klubRepo.findByPredsjednikContainingIgnoreCase(searchText);
            case "trener":
                return klubRepo.findByTrenerContainingIgnoreCase(searchText);
            case "navijaci":
                return klubRepo.findByNavijaciContainingIgnoreCase(searchText);
            case "boja":
                return klubRepo.findByBojaContainingIgnoreCase(searchText);
            case "prvak_hrvatska":
                return klubRepo.findByPrvakHrvatska(Integer.parseInt(searchText));
            default:
                return klubRepo.findByAnyField(searchText);
        }
    }


}
