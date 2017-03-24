package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.SearchKeyword;

public class SearchKeywordCreatedDto {

	private Long idSearchKeyword;
	private String dsSearchKeyword;
	private String dsProjectKey;

	public SearchKeywordCreatedDto(SearchKeyword searchKeyword) {
		this.idSearchKeyword = searchKeyword.getIdSearchKeyword();
		this.dsSearchKeyword = searchKeyword.getDsSearchKeyword();
	}

	public SearchKeywordCreatedDto() {
		super();
	}

	public SearchKeywordCreatedDto(Long idSearchKeyword, String dsSearchKeyword, String dsProjectKey) {
		super();
		this.idSearchKeyword = idSearchKeyword;
		this.dsSearchKeyword = dsSearchKeyword;
		this.dsProjectKey = dsProjectKey;
	}

	public SearchKeywordCreatedDto(String dsSearchKeyword, String dsProjectKey) {
		super();
		this.dsSearchKeyword = dsSearchKeyword;
		this.dsProjectKey = dsProjectKey;
	}

	public Long getIdSearchKeyword() {
		return idSearchKeyword;
	}

	public void setIdSearchKeyword(Long idSearchKeyword) {
		this.idSearchKeyword = idSearchKeyword;
	}

	public String getDsSearchKeyword() {
		return dsSearchKeyword;
	}

	public void setDsSearchKeyword(String dsSearchKeyword) {
		this.dsSearchKeyword = dsSearchKeyword;
	}

	public String getDsProjectKey() {
		return dsProjectKey;
	}

	public void setDsProjectKey(String dsProjectKey) {
		this.dsProjectKey = dsProjectKey;
	}

}