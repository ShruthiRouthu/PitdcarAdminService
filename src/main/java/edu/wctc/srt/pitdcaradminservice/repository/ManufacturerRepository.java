package edu.wctc.srt.pitdcaradminservice.repository;

import edu.wctc.srt.pitdcaradminservice.entity.Manufacturer;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



/**
 *
 * @author srouthu
 */
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer>, Serializable {
  
    @Query("SELECT m FROM Manufacturer m Join FETCH m.partSet WHERE m.manufacturerId = (:id)")
   
    public Manufacturer findByIdAndFetchBooksEagerly(@Param("id") Integer id);
    
    
}
