package br.ufs.dcomp.farms.model.dto;

import java.util.Date;



public class SelectionStepCreateDto {
	
	
	private Integer qtReview;
	private Date dhStartSelectionStep;
	private Date dhEndSelectionStep;
	private Date dhReviewEnd;
	private Date dhConflictsSolvingEnd;
	private String dsratedContent;
	private String dsKey;
	
	
	public SelectionStepCreateDto(Integer qtReview, Date dhStartSelectionStep, Date dhEndSelectionStep,
			Date dhReviewEnd, Date dhConflictsSolvingEnd, String dsratedContent, String dsKey) {
		super();
		this.qtReview = qtReview;
		this.dhStartSelectionStep = dhStartSelectionStep;
		this.dhEndSelectionStep = dhEndSelectionStep;
		this.dhReviewEnd = dhReviewEnd;
		this.dhConflictsSolvingEnd = dhConflictsSolvingEnd;
		this.dsratedContent = dsratedContent;
		this.dsKey = dsKey;
	}
	
	
	public SelectionStepCreateDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getQtReview() {
		return qtReview;
	}
	public void setQtReview(Integer qtReview) {
		this.qtReview = qtReview;
	}
	public Date getDhStartSelectionStep() {
		return dhStartSelectionStep;
	}
	public void setDhStartSelectionStep(Date dhStartSelectionStep) {
		this.dhStartSelectionStep = dhStartSelectionStep;
	}
	public Date getDhEndSelectionStep() {
		return dhEndSelectionStep;
	}
	public void setDhEndSelectionStep(Date dhEndSelectionStep) {
		this.dhEndSelectionStep = dhEndSelectionStep;
	}
	public Date getDhReviewEnd() {
		return dhReviewEnd;
	}
	public void setDhReviewEnd(Date dhReviewEnd) {
		this.dhReviewEnd = dhReviewEnd;
	}
	public Date getDhConflictsSolvingEnd() {
		return dhConflictsSolvingEnd;
	}
	public void setDhConflictsSolvingEnd(Date dhConflictsSolvingEnd) {
		this.dhConflictsSolvingEnd = dhConflictsSolvingEnd;
	}
	public String getDsratedContent() {
		return dsratedContent;
	}
	public void setDsratedContent(String dsratedContent) {
		this.dsratedContent = dsratedContent;
	}
	public String getDsKey() {
		return dsKey;
	}
	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}
	
	
	
	

}
