package br.ufs.dcomp.farms.model.dto;

import java.util.Date;
import java.util.List;

public class ReviewCreateDto {
	private List<Long> studies;
	private String dsSSO;
	private Date dhAssign;
	private Date dhReview;
	private String dsCommentary;
	private List<Long> criterias;

	public ReviewCreateDto(List<Long> studies, String dsSSO, Date dhAssign) {
		super();
		this.studies = studies;
		this.dsSSO = dsSSO;
		this.dhAssign = dhAssign;
	}

	
	public ReviewCreateDto(List<Long> studies, String dsSSO, Date dhAssign, Date dhReview, String dsCommentary,
			List<Long> criterias) {
		super();
		this.studies = studies;
		this.dsSSO = dsSSO;
		this.dhAssign = dhAssign;
		this.dhReview = dhReview;
		this.dsCommentary = dsCommentary;
		this.criterias = criterias;
	}


	public ReviewCreateDto() {
		super();
	}

	public List<Long> getStudies() {
		return studies;
	}

	public void setStudies(List<Long> studies) {
		this.studies = studies;
	}

	public String getDsSSO() {
		return dsSSO;
	}

	public void setDsSSO(String dsSSO) {
		this.dsSSO = dsSSO;
	}

	public Date getDhAssign() {
		return dhAssign;
	}

	public void setDhAssign(Date dhAssign) {
		this.dhAssign = dhAssign;
	}

	public Date getDhReview() {
		return dhReview;
	}

	public void setDhReview(Date dhReview) {
		this.dhReview = dhReview;
	}

	public String getDsCommentary() {
		return dsCommentary;
	}

	public void setDsCommentary(String dsCommentary) {
		this.dsCommentary = dsCommentary;
	}

	public List<Long> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<Long> criterias) {
		this.criterias = criterias;
	}
	
	
}
