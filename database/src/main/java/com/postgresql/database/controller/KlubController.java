    package com.postgresql.database.controller;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.postgresql.database.model.Klub;
    import com.postgresql.database.repo.KlubRepo;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.io.FileWriter;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.LinkedHashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    @RestController
    public class KlubController {
        private final KlubRepo klubRepo;

        @Autowired
        public KlubController(KlubRepo klubRepo) {
            this.klubRepo = klubRepo;
        }


        private Map<String, Object> generateJsonLdResponse(Klub klub) {
            Map<String, Object> jsonLd = new LinkedHashMap<>();

            jsonLd.put("@context", "https://schema.org");
            jsonLd.put("@type", "SportsTeam");

            jsonLd.put("naziv_klub", klub.getNaziv_klub());
            jsonLd.put("nadimak", klub.getNadimak());
            jsonLd.put("sport", "Nogomet");

            jsonLd.put("godina_osnutak", klub.getGodina_osnutak());
            jsonLd.put("trener", createCoachObject(klub.getTrener()));
            jsonLd.put("predsjednik", createPersonObject(klub.getPredsjednik()));
            jsonLd.put("opis", klub.getNaziv_klub() + " je nogometni klub smješten u " + klub.getMjesto() + ".");

            jsonLd.put("mjesto", createPlaceObject(klub.getMjesto()));
            jsonLd.put("stadion", createStadiumObject(klub.getNaziv_stadion(), klub.getMjesto()));

            jsonLd.put("naziv_liga", klub.getNaziv_liga());
            jsonLd.put("rang", klub.getRang());
            jsonLd.put("broj_klubova", klub.getBroj_klubova());
            jsonLd.put("krugovi", klub.getKrugovi());
            jsonLd.put("navijači", klub.getNavijači());
            jsonLd.put("boja", klub.getBoja());
            jsonLd.put("prvak_hrvatska", klub.getPrvak_hrvatska());

            return jsonLd;
        }

        private Map<String, String> createCoachObject(String name) {
            Map<String, String> coach = new LinkedHashMap<>();
            coach.put("@type", "Coach");
            coach.put("name", name);
            return coach;
        }

        private Map<String, String> createPersonObject(String name) {
            Map<String, String> person = new LinkedHashMap<>();
            person.put("@type", "Person");
            person.put("name", name);
            return person;
        }

        private Map<String, String> createPlaceObject(String mjesto) {
            Map<String, String> place = new LinkedHashMap<>();
            place.put("@type", "Place");
            place.put("name", mjesto);
            return place;
        }

        private Map<String, String> createStadiumObject(String nazivStadion, String mjesto) {
            Map<String, String> stadium = new LinkedHashMap<>();
            stadium.put("@type", "StadiumOrArena");
            stadium.put("name", nazivStadion);
            stadium.put("location", mjesto);
            return stadium;
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

        @GetMapping("/jsonld/klub/{naziv}")
        public ResponseEntity<Map<String, Object>> getLdKlubByNaziv(@PathVariable String naziv) {
            Klub klub = klubRepo.findById(naziv).orElse(null);
            if (klub == null) {
                return ResponseEntity
                        .status(404)
                        .header("Error-Message", "Klub s nazivom " + naziv + " nije pronađen.")
                        .build();
            }
            Map<String, Object> jsonLdResponse = generateJsonLdResponse(klub);
            return ResponseEntity.ok(jsonLdResponse);
        }

        @GetMapping("/jsonld/prva")
        public ResponseEntity<List<Map<String, Object>>> getLdFirst() {
            List<Klub> klubovi = klubRepo.findByRangContainingIgnoreCase(String.valueOf(1));
            List<Map<String, Object>> jsonLdResponses = klubovi.stream()
                    .map(this::generateJsonLdResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(jsonLdResponses);
        }

        @GetMapping("/jsonld/druga")
        public ResponseEntity<List<Map<String, Object>>> getLdSecond() {
            List<Klub> klubovi = klubRepo.findByRangContainingIgnoreCase(String.valueOf(2));
            List<Map<String, Object>> jsonLdResponses = klubovi.stream()
                    .map(this::generateJsonLdResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(jsonLdResponses);
        }

        @GetMapping("/jsonld/treca")
        public ResponseEntity<List<Map<String, Object>>> getLdThird() {
            List<Klub> klubovi = klubRepo.findByRangContainingIgnoreCase(String.valueOf(3));
            List<Map<String, Object>> jsonLdResponses = klubovi.stream()
                    .map(this::generateJsonLdResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(jsonLdResponses);
        }

        @GetMapping("/jsonld/svi")
        public ResponseEntity<List<Map<String, Object>>> getAll() {
            List<Klub> klubovi = klubRepo.findAll();
            List<Map<String, Object>> jsonLdResponses = klubovi.stream()
                    .map(this::generateJsonLdResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(jsonLdResponses);
        }

        @PostMapping("/jsonld/svi/spremi")
        public ResponseEntity<String> saveAllJsonLdToFile() {
            try {

                List<Klub> klubovi = klubRepo.findAll();
                List<Map<String, Object>> jsonLdResponses = klubovi.stream()
                        .map(this::generateJsonLdResponse)
                        .collect(Collectors.toList());

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonContent = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonLdResponses);


                Path filePath = Paths.get("/Users/gabrielleko/Desktop/Faks/5sem/OTVRAC/OR", "klubovi.json");


                Files.write(filePath, jsonContent.getBytes());

                return ResponseEntity.ok("Podaci su uspješno spremljeni u 'klubovi.json'.");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Dogodila se greška prilikom spremanja datoteke.");
            }
        }

        @PostMapping("/jsonld/svi/spremi-csv")
        public ResponseEntity<String> saveAllToCsv() {
            List<Klub> klubovi = klubRepo.findAll();
            Path filePath = Paths.get("/Users/gabrielleko/Desktop/Faks/5sem/OTVRAC/OR/", "klubovi.csv");
            try (FileWriter writer = new FileWriter(filePath.toFile())) {

                writer.append("Naziv kluba,Nadimak,Godina osnutka,Mjesto,Stadion,Trener,Predsjednik,Liga,Rang,Broj klubova,Krugovi,Navijači,Boja,Prvak Hrvatska\n");


                for (Klub klub : klubovi) {
                    writer.append(escapeCsvField(klub.getNaziv_klub()))
                            .append(',')
                            .append(escapeCsvField(klub.getNadimak()))
                            .append(',')
                            .append(String.valueOf(klub.getGodina_osnutak()))
                            .append(',')
                            .append(escapeCsvField(klub.getMjesto()))
                            .append(',')
                            .append(escapeCsvField(klub.getNaziv_stadion()))
                            .append(',')
                            .append(escapeCsvField(klub.getTrener()))
                            .append(',')
                            .append(escapeCsvField(klub.getPredsjednik()))
                            .append(',')
                            .append(escapeCsvField(klub.getNaziv_liga()))
                            .append(',')
                            .append(klub.getRang() != null ? klub.getRang() : "")
                            .append(',')
                            .append(klub.getBroj_klubova() != null ? klub.getBroj_klubova() : "")
                            .append(',')
                            .append(klub.getKrugovi() != null ? klub.getKrugovi() : "")
                            .append(',')
                            .append(escapeCsvField(klub.getNavijači()))
                            .append(',')
                            .append(escapeCsvField(klub.getBoja()))
                            .append(',')
                            .append(String.valueOf(klub.getPrvak_hrvatska()))
                            .append('\n');
                }

                return ResponseEntity.ok("Podaci su uspješno spremljeni u 'klubovi.csv'.");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Dogodila se greška prilikom spremanja datoteke.");
            }
        }


        private String escapeCsvField(String value) {
            if (value == null) {
                return "";
            }

            if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                value = value.replace("\"", "\"\"");
                return "\"" + value + "\"";
            }
            return value;
        }
    }




