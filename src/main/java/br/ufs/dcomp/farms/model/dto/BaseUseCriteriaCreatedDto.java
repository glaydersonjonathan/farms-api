package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.SearchEngine;

public class BaseUseCriteriaCreatedDto {

	private SearchEngine engine;
	private String dsProjectKey;
	private String dsBaseUseCriteria;

	public BaseUseCriteriaCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BaseUseCriteriaCreatedDto(SearchEngine engine, String dsProjectKey, String dsdsBaseUseCriteria) {
		super();
		this.engine = engine;
		this.dsProjectKey = dsProjectKey;
		this.dsBaseUseCriteria = dsdsBaseUseCriteria;
	}

	public SearchEngine getEngine() {
		return engine;
	}

	public void setEngine(SearchEngine engine) {
		this.engine = engine;
	}

	public String getDsProjectKey() {
		return dsProjectKey;
	}

	public void setDsProjectKey(String dsProjectKey) {
		this.dsProjectKey = dsProjectKey;
	}

	public String getDsBaseUseCriteria() {
		return dsBaseUseCriteria;
	}

	public void setDsBaseUseCriteria(String dsBaseUseCriteria) {
		this.dsBaseUseCriteria = dsBaseUseCriteria;
	}

}
