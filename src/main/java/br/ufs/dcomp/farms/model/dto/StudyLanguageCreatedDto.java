package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.Language;

public class StudyLanguageCreatedDto {

	private Language language;
	private String dsProjectKey;

	public StudyLanguageCreatedDto(Language language, String dsProjectKey) {
		super();
		this.language = language;
		this.dsProjectKey = dsProjectKey;
	}

	public StudyLanguageCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getDsProjectKey() {
		return dsProjectKey;
	}

	public void setDsProjectKey(String dsProjectKey) {
		this.dsProjectKey = dsProjectKey;
	}

}
