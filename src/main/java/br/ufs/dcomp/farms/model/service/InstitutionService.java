package br.ufs.dcomp.farms.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.dao.InstitutionDao;
import br.ufs.dcomp.farms.model.dao.ProjectDao;
import br.ufs.dcomp.farms.model.dto.CountryCreatedDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreateDto;
import br.ufs.dcomp.farms.model.dto.InstitutionCreatedDto;
import br.ufs.dcomp.farms.model.entity.Country;
import br.ufs.dcomp.farms.model.entity.Institution;
import br.ufs.dcomp.farms.model.entity.Project;

@Component
public class InstitutionService {

	@Autowired
	private InstitutionDao institutionDao;
	@Autowired
	private ProjectDao projectDao;
	

	public List<InstitutionCreatedDto> getByDsKeyProject(String dsKey) {
		List<InstitutionCreatedDto> institutionCreatedDto = new ArrayList<InstitutionCreatedDto>();
		List<Institution> institutions = institutionDao.getByDsKeyProject(dsKey);
		if (institutions != null) {
			for(Institution institution : institutions) {
				institutionCreatedDto.add(new InstitutionCreatedDto(institution));
			}
		}
		return institutionCreatedDto;
	}
	
	
	public List<InstitutionCreatedDto> getAll() {
		List<InstitutionCreatedDto> institutionCreatedDto = new ArrayList<InstitutionCreatedDto>();
		List<Institution> institutions = institutionDao.getAllInstitutions();
		if (institutions != null) {
			for(Institution institution : institutions) {
				institutionCreatedDto.add(new InstitutionCreatedDto(institution));
			}
		}
		return institutionCreatedDto;
	}


	public List<CountryCreatedDto> getAllCountries() {
		List<CountryCreatedDto> countryCreatedDto = new ArrayList<CountryCreatedDto>();
		List<Country> countries = institutionDao.getAllCountries();
		if (countries != null) {
			for(Country country : countries) {
				countryCreatedDto.add(new CountryCreatedDto(country));
			}
		}
		return countryCreatedDto;
	}


	public boolean save(InstitutionCreateDto institutionCreateDto) {
		Institution institution = new Institution();
		institution.setDsAbbreviation(institutionCreateDto.getDsAbbreviation());
		institution.setNmInstitution(institutionCreateDto.getNmInstitution());
		institution.setCountry(institutionCreateDto.getCountry());
		
		Project project = projectDao.getByDsKey(institutionCreateDto.getDsKey());
		
		institution.setProject(project);
		
		institutionDao.save(institution);
		
		return true;
	}
	
	
}