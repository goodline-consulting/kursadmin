package kursadmin.service;

import java.util.List;

import kursadmin.domain.Organisation;
import kursadmin.repository.LokalDao;
import kursadmin.repository.OrganisationDao;

public class OrganisationManager implements OrganisationManagerInterface 
{
	OrganisationDao orgDao;
	@Override
	public void deleteOrganisation(int oid) 
	{
		orgDao.deleteOrganisation(oid);
	}

	@Override
	public List<Organisation> getOrgList() 
	{
		return orgDao.getOrgList();
	}

	@Override
	public Organisation getOrganisation(int oid) 
	{
		return orgDao.getOrganisation(oid);
	}

	@Override
	public Organisation getOrganisation(String orgnr) 
	{
		return orgDao.getOrganisation(orgnr);
	}

	@Override
	public void insertOrganisation(Organisation org) 
	{
		orgDao.insertOrganisation(org);
	}

	@Override
	public void updateOrganisation(Organisation org) 
	{
		orgDao.updateOrganisation(org);
	}
	public void setOrganisationDao(OrganisationDao orgDao)
	{
		this.orgDao = orgDao;
	}
}
