package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.Country;

public class InstitutionCreateDto {

	private String dsAbbreviation;
	private String nmInstitution;
	private Country country;

	public InstitutionCreateDto() {
		}

	
	public InstitutionCreateDto(String dsAbbreviation, String nmInstitution, Country country) {	
		super();
		this.dsAbbreviation = dsAbbreviation;
		this.nmInstitution = nmInstitution;
		this.country = country;
	}

	public String getDsAbbreviation() {
		return dsAbbreviation;
	}

	public void setDsAbbreviation(String dsAbbreviation) {
		this.dsAbbreviation = dsAbbreviation;
	}

	public String getNmInstitution() {
		return nmInstitution;
	}

	public void setNmInstitution(String nmInstitution) {
		this.nmInstitution = nmInstitution;
	}

	public Country getCountry() {
		return country;
	}

	public void setIdCountry(Country country) {
		this.country = country;
	}
}