package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.Language;

public class LanguageCreatedDto {

	private Long idLanguage;
	private String nmLanguage;
	private String dsProjectKey;

	public LanguageCreatedDto(Language language) {
		this.idLanguage = language.getIdLanguage();
		this.nmLanguage = language.getNmLanguage();
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