package kursadmin.service;

import java.util.List;

import kursadmin.domain.Organisation;

public interface OrganisationManagerInterface
{
	public List<Organisation> getOrgList();
    public Organisation getOrganisation(int oid);
    public Organisation getOrganisation(String orgnr);
    public void updateOrganisation(Organisation org);
    public void insertOrganisation(Organisation org);    
    public void deleteOrganisation(int oid);
}
