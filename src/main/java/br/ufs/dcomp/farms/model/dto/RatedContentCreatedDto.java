package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.RatedContent;

public class RatedContentCreatedDto {

	private Long idRatedContent;
	private String dsRatedContent;

	public RatedContentCreatedDto(RatedContent ratedContent) {
		this.idRatedContent = ratedContent.getIdRatedContent();
		this.dsRatedContent = ratedContent.getDsRatedContent();
	}

	public RatedContentCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RatedContentCreatedDto(Long idRatedContent, String dsRatedContent) {
		super();
		this.idRatedContent = idRatedContent;
		this.dsRatedContent = dsRatedContent;
	}

	public Long getIdRatedContent() {
		return idRatedContent;
	}

	public void setIdRatedContent(Long idRatedContent) {
		this.idRatedContent = idRatedContent;
	}

	public String getDsRatedContent() {
		return dsRatedContent;
	}

	public void setDsRatedContent(String dsRatedContent) {
		this.dsRatedContent = dsRatedContent;
	}

}
