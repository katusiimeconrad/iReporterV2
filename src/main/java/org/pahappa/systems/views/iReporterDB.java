/**
 * In this class, I test the Hibernate ORM by saving and retrieving from the DB
 * @author Blaki
 */

package org.pahappa.systems.views;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.enums.Type;
import org.pahappa.systems.hibernateUtils.HibernateUtil;
import org.pahappa.systems.models.Incident;
import org.pahappa.systems.services.IncidentServiceImpl;
import org.pahappa.systems.services.IncidentServiceImplConsole;

public class iReporterDB {
    //For creating transactions to the DB
    private static Transaction transaction;
    //Reads Config file and opens connection to DB
    private static Session session = HibernateUtil.getSessionFactory().openSession();


    public static void main(String[] args) throws Exception {
        IncidentServiceImplConsole incidentService = new IncidentServiceImplConsole();

        Incident incident = new Incident();

        incident.setTitle("Theft of Funds");
        incident.setType(Type.RED_FLAG);
        incident.setComment("Money for building a school was embezzled");

        //incidentService.saveIncident(incident);

        try {
            transaction = session.beginTransaction();

            //Attempt to Save to DB
            session.save(incidentService.saveIncident(incident));

        } catch (HibernateException exception){
            exception.printStackTrace();
        } finally {

            //If no errors, send the transaction to the Server
            transaction.commit();

            System.out.println("Incident Saved!");
        }


        IncidentServiceImpl x = new IncidentServiceImpl();

        x.saveIncident(incident);

        System.out.println(x.getAllIncidents());


//        try {
//            transObj = sessionObj.beginTransaction();
//            sessionObj.save(employee);
//        } catch (HibernateException exceptionObj) {
//            exceptionObj.printStackTrace();
//        } finally {
//            transObj.commit();
//        }
    }
}
