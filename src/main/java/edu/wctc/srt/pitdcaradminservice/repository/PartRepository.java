package edu.wctc.srt.pitdcaradminservice.repository;

import edu.wctc.srt.pitdcaradminservice.entity.Part;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author srouthu
 */
public interface PartRepository extends JpaRepository<Part, Integer>, Serializable {
    
}
