package br.ufs.dcomp.farms.model.dto;

import java.util.Date;

import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.entity.Review;
import br.ufs.dcomp.farms.model.entity.Study;
import br.ufs.dcomp.farms.model.enums.SelectionStatusEnum;

public class ReviewCreatedDto {

	private Long idReview;
	private Date dhAssign;
	private Date dhReview;
	private Integer tpStatus;
	private Long idResearcher;
	private StudyCreatedDto study;
	

	public ReviewCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewCreatedDto(Long idReview, Date dhAssign, Date dhReview, Integer tpStatus,
			Long researcher, StudyCreatedDto study) {
		super();
		this.idReview = idReview;
		this.dhAssign = dhAssign;
		this.dhReview = dhReview;
		this.tpStatus = tpStatus;
		this.idResearcher = researcher;
		this.study = study;
	}

	public ReviewCreatedDto(Review review) {
		this.idReview = review.getIdReview();
		this.dhAssign = review.getDhAssign();
		this.dhReview = review.getDhReview();
		this.idResearcher = review.getResearcher().getIdResearcher();
		this.tpStatus = review.getTpStatus().getCode();
		this.study = new StudyCreatedDto (review.getStudy());
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

}
