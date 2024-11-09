package com.postgresql.database.repo;
import com.postgresql.database.model.Klub;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface KlubRepo extends JpaRepository<Klub, String> {

    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.naziv_liga) LIKE :searchText OR " +
            "LOWER(k.rang) LIKE :searchText OR " +
            "LOWER(k.broj_klubova) LIKE :searchText OR " +
            "LOWER(k.krugovi) LIKE :searchText OR " +
            "LOWER(k.naziv_klub) LIKE :searchText OR " +
            "LOWER(k.nadimak) LIKE :searchText OR " +
            "LOWER(k.naziv_stadion) LIKE :searchText OR " +
            "LOWER(k.mjesto) LIKE :searchText OR " +
            "CAST(k.godina_osnutak AS string) LIKE :searchText OR " +
            "LOWER(k.predsjednik) LIKE :searchText OR " +
            "LOWER(k.trener) LIKE :searchText OR " +
            "LOWER(k.navijači) LIKE :searchText OR " +
            "LOWER(k.boja) LIKE :searchText OR " +
            "CAST(k.prvak_hrvatska AS string) LIKE :searchText")
    List<Klub> findByAnyField(String searchText);

    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.naziv_liga) LIKE :naziv_liga")
    List<Klub> findByNazivLigaContainingIgnoreCase(String naziv_liga);
    @Query("SELECT k FROM Klub k WHERE " +
            "k.rang LIKE :rang")
    List<Klub> findByRangContainingIgnoreCase(String rang);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.broj_klubova) LIKE :broj_klubova")
    List<Klub> findByBrojKlubovaContainingIgnoreCase(String broj_klubova);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.krugovi) LIKE :krugovi")
    List<Klub> findByKrugoviContainingIgnoreCase(String krugovi);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.naziv_klub) LIKE :naziv_klub")
    List<Klub> findByNazivKlubContainingIgnoreCase(String naziv_klub);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.nadimak) LIKE :nadimak")
    List<Klub> findByNadimakContainingIgnoreCase(String nadimak);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.naziv_stadion) LIKE :naziv_stadion")
    List<Klub> findByNazivStadionContainingIgnoreCase(String naziv_stadion);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.mjesto) LIKE :mjesto")
    List<Klub> findByMjestoContainingIgnoreCase(String mjesto);
    @Query("SELECT k FROM Klub k WHERE " +
            "CAST(k.godina_osnutak AS string) LIKE :godina_osnutak")
    List<Klub> findByGodinaOsnutak(int godina_osnutak);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.predsjednik) LIKE :predsjednik")
    List<Klub> findByPredsjednikContainingIgnoreCase(String predsjednik);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.trener) LIKE :trener")
    List<Klub> findByTrenerContainingIgnoreCase(String trener);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.navijači) LIKE :navijači")
    List<Klub> findByNavijaciContainingIgnoreCase(String navijači);
    @Query("SELECT k FROM Klub k WHERE " +
            "LOWER(k.boja) LIKE :boja")
    List<Klub> findByBojaContainingIgnoreCase(String boja);
    @Query("SELECT k FROM Klub k WHERE " +
            "CAST(k.prvak_hrvatska AS string) LIKE :prvak_hrvatska")
    List<Klub> findByPrvakHrvatska(int prvak_hrvatska);
}
