package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.BaseUseCriteria;
import br.ufs.dcomp.farms.model.entity.SearchEngine;

public class SearchEngineCreatedDto {

	private Long idSearchEngine;
	private String nmSearchEngine;

	private String dsProjectKey;
	private String dsBaseUseCriteria;

	public SearchEngineCreatedDto() {
		super();
	}

	public SearchEngineCreatedDto(Long idSearchEngine, String nmSearchEngine, String dsProjectKey,
			String dsBaseUseCriteria) {
		super();
		this.idSearchEngine = idSearchEngine;
		this.nmSearchEngine = nmSearchEngine;
		this.dsProjectKey = dsProjectKey;
		this.dsBaseUseCriteria = dsBaseUseCriteria;
	}

	public SearchEngineCreatedDto(SearchEngine searchEngine) {
		this.idSearchEngine = searchEngine.getIdSearchEngine();
		this.nmSearchEngine = searchEngine.getNmSearchEngine();
	}

	public SearchEngineCreatedDto(BaseUseCriteria searchEngine) {
		this.idSearchEngine = searchEngine.getSearchEngine().getIdSearchEngine();
		this.nmSearchEngine = searchEngine.getSearchEngine().getNmSearchEngine();
		this.dsProjectKey = searchEngine.getProject().getDsKey();
		this.dsBaseUseCriteria = searchEngine.getDsBaseUseCriteria();
	}

	public Long getIdSearchEngine() {
		return idSearchEngine;
	}

	public void setIdSearchEngine(Long idSearchEngine) {
		this.idSearchEngine = idSearchEngine;
	}

	public String getNmSearchEngine() {
		return nmSearchEngine;
	}

	public void setNmSearchEngine(String nmSearchEngine) {
		this.nmSearchEngine = nmSearchEngine;
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