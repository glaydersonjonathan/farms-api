package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.StudyLanguage;

public class LanguageCreatedDto {

	private Long idLanguage;
	private String nmLanguage;
	private String dsProjectKey;

	public LanguageCreatedDto(StudyLanguage language) {
		this.idLanguage = language.getLanguage().getIdLanguage();
		this.nmLanguage = language.getLanguage().getNmLanguage();
		this.dsProjectKey = language.getProject().getDsKey();
	}

	public LanguageCreatedDto() {
		super();
	}

	public Long getIdLanguage() {
		return idLanguage;
	}

	public void setIdLanguage(Long idLanguage) {
		this.idLanguage = idLanguage;
	}

	public String getNmLanguage() {
		return nmLanguage;
	}

	public void setNmLanguage(String nmLanguage) {
		this.nmLanguage = nmLanguage;
	}

	public String getDsProjectKey() {
		return dsProjectKey;
	}

	public void setDsProjectKey(String dsProjectKey) {
		this.dsProjectKey = dsProjectKey;
	}

}