package br.ufs.dcomp.farms.model.dto;

import java.util.Date;

import br.ufs.dcomp.farms.model.entity.Review;

public class ReviewCreatedDto {

	private Long idReview;
	private Date dhAssign;
	private Date dhReview;
	private Integer tpStatus;
	private Long idResearcher;
	private StudyCreatedDto study;
	private String dsCommentary;
	

	public ReviewCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewCreatedDto(Long idReview, Date dhAssign, Date dhReview, Integer tpStatus,
			Long researcher, StudyCreatedDto study, String dsCommentary) {
		super();
		this.idReview = idReview;
		this.dhAssign = dhAssign;
		this.dhReview = dhReview;
		this.tpStatus = tpStatus;
		this.idResearcher = researcher;
		this.study = study;
		this.dsCommentary = dsCommentary;
	}

	public ReviewCreatedDto(Review review) {
		this.idReview = review.getIdReview();
		this.dhAssign = review.getDhAssign();
		this.dhReview = review.getDhReview();
		this.idResearcher = review.getResearcher().getIdResearcher();
		this.tpStatus = review.getTpStatus().getCode();
		this.study = new StudyCreatedDto (review.getStudy());
		this.dsCommentary = review.getDsCommentary();
	}

	public Long getIdReview() {
		return idReview;
	}

	public void setIdReview(Long idReview) {
		this.idReview = idReview;
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

	public Integer getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(Integer tpStatus) {
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

	public String getDsCommentary() {
		return dsCommentary;
	}

	public void setDsCommentary(String dsCommentary) {
		this.dsCommentary = dsCommentary;
	}

	
}
