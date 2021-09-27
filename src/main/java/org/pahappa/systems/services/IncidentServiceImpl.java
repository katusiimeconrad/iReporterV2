package org.pahappa.systems.services;

<<<<<<< HEAD
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.enums.Status;
import org.pahappa.systems.hibernateUtils.HibernateUtil;
import org.pahappa.systems.models.Incident;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
=======
import org.pahappa.systems.enums.Status;
import org.pahappa.systems.enums.Type;
import org.pahappa.systems.exceptions.SavingFailedException;
import org.pahappa.systems.exceptions.ValidationFailedException;
import org.pahappa.systems.models.Incident;

>>>>>>> 26f47bd5eadaf0b62275883a57450e68f55c2b20
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

<<<<<<< HEAD
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
=======

public class IncidentServiceImpl implements IncidentService {

	private  static ArrayList<Incident> incidents = new ArrayList<Incident>();
	private static int incidentIds=0;

	@Override
	public Incident saveIncident(Incident incident) throws Exception {

		if (incident.getTitle()==null){
			throw new ValidationFailedException("Please enter title");
		}
		if (incident.getTitle().isEmpty()){
			throw new ValidationFailedException("Please enter the title");
		}
		if (incident.getComment()==null){
			throw new ValidationFailedException("Please enter comment");
		}
		if (incident.getComment().isEmpty()){
			throw new ValidationFailedException("please enter comment.");
		}
		incident.setId(++incidentIds);
		incident.setStatus(Status.DRAFT);
		incident.setCreatedOn( new Date());
		incidents.add(incident);

		return incident;

	}

	@Override
	public Incident updateIncident(Incident incident) throws Exception {
		for(Incident item: incidents){
			if(item.getId() == incident.getId()){
				item.setTitle(incident.getTitle());
				item.setType(incident.getType());
				item.setComment(incident.getComment());
				return item;
			}
		}
		throw new SavingFailedException("Record not found");
	}

	@Override
	public List<Incident> getAllIncidents() {
		// TODO Auto-generated method stub
		return incidents;
	}

	@Override
	public List<Incident> getRedflagIncidents() {
		//[redflagIncidents] will contain all incidents in [incidents] where type is REDFLAG
		List<Incident> redflagIncidents = new ArrayList<Incident>();
		for (Incident incident:incidents) {
			if( incident.getType() == Type.RED_FLAG ){
				redflagIncidents.add(incident);
			}
		}
		//The List will be empty if no incidents were marked as REDFLAG
		return redflagIncidents;
	}

	@Override
	//creating a list to store intervention incidents
	public List<Incident> getInterventionIncidents() {
		List<Incident> interventionIncidents =new ArrayList<Incident>();
		//looping through the incidents list to get intervention incidents
		for (Incident incident:incidents) {
			if (incident.getType()== Type.INTERVENTION) {
				interventionIncidents.add(incident);
			}
		}
		// TODO Auto-generated method stub
		return interventionIncidents;
	}

	@Override
	public int countIncidents() {
		return incidents.size();
	}

	@Override
	public boolean incidentExists(Incident incident) {
		String titleOfpassedIncident = incident.getTitle();
		for(Incident item:incidents){
			String titleOfIncident = item.getTitle();
			if(titleOfIncident == titleOfpassedIncident){
				if(item.getComment() == incident.getComment() && item.getType() == incident.getType()){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Incident getIncidentOfId(int id) {
		for(Incident incident:incidents){
			if(incident.getId() == id){
				return incident;
			}
		}
		return null;
	}

	@Override
	public void deleteIncident(Incident incident) {
		if(incident != null){
			if(incidentExists(incident)){
				incidents.remove(incident);
			}else{
				System.out.println("There is no record of this incident");
			}
		}else{
			System.out.println("Please an enter an incident to delete");
		}
	}



>>>>>>> 26f47bd5eadaf0b62275883a57450e68f55c2b20
}
