package br.ufs.dcomp.farms.model.dto;

import java.util.Date;
import java.util.List;

import br.ufs.dcomp.farms.model.entity.Review;




public class ReviewCreateDto {
	private List<Long> studies;
	private String dsSSO;
	private Date dhAssign;
	private Date dhReview;
	private String dsCommentary;
	private List<SelectionCriteriaCreatedDto> criterias;
	private Long idReview;
	
	private String tpStatus;
	private Long idResearcher;
	private StudyCreatedDto study;

	public ReviewCreateDto(List<Long> studies, String dsSSO, Date dhAssign) {
		super();
		this.studies = studies;
		this.dsSSO = dsSSO;
		this.dhAssign = dhAssign;
	}
	
	
	public ReviewCreateDto(Review review, String dsCommentary, List<SelectionCriteriaCreatedDto> criterias) {
		this.idReview = review.getIdReview();
		this.dhAssign = review.getDhAssign();
		this.dhReview = review.getDhReview();
		this.idResearcher = review.getResearcher().getIdResearcher();
		this.tpStatus = review.getTpStatus().getCode().toString();
		this.study = new StudyCreatedDto (review.getStudy());
		this.dsCommentary = dsCommentary;
		this.criterias = criterias;
	}

	public ReviewCreateDto(List<Long> studies, String dsSSO, Date dhAssign, Date dhReview, String dsCommentary,
			List<SelectionCriteriaCreatedDto> criterias, Long idReview, String tpStatus, Long idResearcher, StudyCreatedDto study) {
		super();
		this.studies = studies;
		this.dsSSO = dsSSO;
		this.dhAssign = dhAssign;
		this.dhReview = dhReview;
		this.dsCommentary = dsCommentary;
		this.criterias = criterias;
		this.idReview = idReview;
	    this.tpStatus = tpStatus;
	    this.idResearcher = idResearcher;
	    this.study = study;
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

	public List<SelectionCriteriaCreatedDto> getCriterias() {
		return criterias;
	}

	public void setCriterias(List<SelectionCriteriaCreatedDto> criterias) {
		this.criterias = criterias;
	}

	public Long getIdReview() {
		return idReview;
	}

	public void setIdReview(Long idReview) {
		this.idReview = idReview;
	}

	public String getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(String tpStatus) {
		this.tpStatus = tpStatus;
	}

	public Long getIdResearcher() {
		return idResearcher;
	}

	public void setIdResearcher(Long idResearcher) {
		this.idResearcher = idResearcher;
	}

	public StudyCreatedDto getStudy() {
		return study;
	}

	public void setStudy(StudyCreatedDto study) {
		this.study = study;
	}
	
	
	

}
