package org.pahappa.systems.views;

import org.pahappa.systems.models.Incident;
import org.pahappa.systems.services.IncidentService;
import org.pahappa.systems.services.IncidentServiceImpl;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean( name = "incidentView")
@SessionScoped
public class IncidentsView {
    private IncidentService incidentService;
    private Incident incident;
    private List<Incident> incidents;

    @PostConstruct
    public void init() {
        this.incidentService = new IncidentServiceImpl();
        this.incident = new Incident();
        this.incidents = incidentService.getAllIncidents();
    }

    /**
     * Method saves an Incident to the DB
     * @param incident
     * @throws Exception
     */
    public void save(Incident incident) throws Exception {
        incidentService.saveIncident(incident);

    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }

    /**
     * Get all incidents from the DB
     * @return
     */
    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
}
