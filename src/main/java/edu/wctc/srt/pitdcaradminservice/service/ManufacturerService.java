package edu.wctc.srt.pitdcaradminservice.service;

import edu.wctc.srt.pitdcaradminservice.entity.Manufacturer;
import edu.wctc.srt.pitdcaradminservice.repository.ManufacturerRepository;
import edu.wctc.srt.pitdcaradminservice.repository.PartRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("manufacturerService")
@Transactional(readOnly = true)
public class ManufacturerService {

    private transient final Logger LOG = LoggerFactory.getLogger(ManufacturerService.class);

    @Autowired
    private ManufacturerRepository ManufacturerRepo;

    @Autowired
    private PartRepository partRepo;

    public ManufacturerService() {
    }

    public List<Manufacturer> findAll() {
        return ManufacturerRepo.findAll();
    }

    public Manufacturer findById(String id) {
        return ManufacturerRepo.findOne(new Integer(id));
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Manufacturer manufacturer) {
        LOG.debug("Deleting manufacturer: " + manufacturer.getManufacturerName());
        ManufacturerRepo.delete(manufacturer);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Manufacturer edit(Manufacturer manufacturer) {
        return ManufacturerRepo.saveAndFlush(manufacturer);
    }
}
