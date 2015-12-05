/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.srt.pitdcaradminservice.service;

import edu.wctc.srt.pitdcaradminservice.entity.Part;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Shruthi Routhu
 */
@Stateless
public class PartFacade extends AbstractFacade<Part> {
    @PersistenceContext(unitName = "edu.wctc.srt_PitdcarAdminService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartFacade() {
        super(Part.class);
    }
    
}
