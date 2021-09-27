package org.pahappa.systems.services;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.enums.Status;
import org.pahappa.systems.hibernateUtils.HibernateUtil;
import org.pahappa.systems.models.Incident;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements CRUD functionality for transacting with the DB on behalf of the View
 *
 */

@Service
@Transactional
public class IncidentServiceImpl implements IncidentService {
    //For creating transactions to the DB
    private static Transaction transaction;
    //Reads Config file and opens connection to DB
    private static Session session = HibernateUtil.getSessionFactory().openSession();


    @Override
    public Incident saveIncident(Incident incident) throws Exception {
        try {
            //Set unset properties of the Incident
            incident.setStatus(Status.DRAFT);
            incident.setCreatedOn(new Date());

            transaction = session.beginTransaction();
            //Attempt to Save to DB
            session.save(incident);

        } catch (HibernateException exception){
            exception.printStackTrace();
        } finally {

            //If no errors, send the transaction to the Server
            transaction.commit();

            return incident;
        }
    }

    @Override
    public Incident updateIncident(Incident incident) throws Exception {
        return null;
    }

    @Override
    public List<Incident> getAllIncidents() {

        List<Incident> allIncidents = new ArrayList<>();
        try {
            transaction = session.beginTransaction();

            //Get All Incidents
            allIncidents = session.createQuery("from Incident ").list();

        } catch (HibernateException exception){
            exception.printStackTrace();
        } finally {

            //If no errors, send the transaction to the Server
            transaction.commit();

            return allIncidents;
        }

    }

    @Override
    public List<Incident> getRedflagIncidents() {
        return null;
    }

    @Override
    public List<Incident> getInterventionIncidents() {
        return null;
    }

    @Override
    public int countIncidents() {
        return 0;
    }

    @Override
    public boolean incidentExists(Incident incident) {
        return false;
    }

    @Override
    public Incident getIncidentOfId(int id) {
        return null;
    }

    @Override
    public void deleteIncident(Incident incident) {

    }
}
