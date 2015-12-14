package edu.wctc.srt.pitdcaradminservice.service;

import edu.wctc.srt.pitdcaradminservice.entity.Part;
import edu.wctc.srt.pitdcaradminservice.repository.ManufacturerRepository;
import edu.wctc.srt.pitdcaradminservice.repository.PartRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author srouthu
 */
@Repository("partService")
@Transactional(readOnly = true)
public class PartService {
    private transient final Logger LOG = LoggerFactory.getLogger(PartService.class);
    
    @Autowired
    private PartRepository partRepo;
    
    @Autowired
    private ManufacturerRepository manufacturerRepo;

    public PartService() {
    }
    
    public List<Part> findAll() {
        return partRepo.findAll();
    }
    
    public  Part findById(String id) {
        return partRepo.findOne(new Integer(id));
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Part part) {
        LOG.debug("Deleting part: " + part.getPartName());
        partRepo.delete(part);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public  Part edit(Part part) {
        return partRepo.saveAndFlush(part);
    }
    
}
