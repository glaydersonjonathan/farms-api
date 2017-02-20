package br.ufs.dcomp.farms.model.dto;

import java.util.Date;

import br.ufs.dcomp.farms.model.entity.SelectionStep;

public class SelectionStepCreatedDto {

	private Long idSelectionStep;
	private Integer nrSerial;
	private Integer qtReview;
	private Date dhStartSelectionStep;
	private Date dhEndSelectionStep;
	private Date dhReviewEnd;
	private Date dhConflictsSolvingEnd;
	private Long idRatedContent;
	private Long idProject;
	private Integer tpStatus;
	private Integer vlLowerScore;

	public SelectionStepCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SelectionStepCreatedDto(Long idSelectionStep, Integer nrSerial, Integer qtReview, Date dhStartSelectionStep,
			Date dhEndSelectionStep, Date dhReviewEnd, Date dhConflictsSolvingEnd, Long idRatedContent, Long idProject,
			Integer tpStatus, Integer vlLowerScore) {
		super();
		this.idSelectionStep = idSelectionStep;
		this.nrSerial = nrSerial;
		this.qtReview = qtReview;
		this.dhStartSelectionStep = dhStartSelectionStep;
		this.dhEndSelectionStep = dhEndSelectionStep;
		this.dhReviewEnd = dhReviewEnd;
		this.dhConflictsSolvingEnd = dhConflictsSolvingEnd;
		this.idRatedContent = idRatedContent;
		this.idProject = idProject;
		this.tpStatus = tpStatus;
		this.vlLowerScore = vlLowerScore;
	}

	public SelectionStepCreatedDto(SelectionStep selectionStep) {
		this.idSelectionStep = selectionStep.getIdSelectionStep();
		this.nrSerial = selectionStep.getNrSerial();
		this.qtReview = selectionStep.getQtReview();
		this.dhStartSelectionStep = selectionStep.getDhStartSelectionStep();
		this.dhEndSelectionStep = selectionStep.getDhEndSelectionStep();
		this.dhReviewEnd = selectionStep.getDhReviewEnd();
		this.dhConflictsSolvingEnd = selectionStep.getDhConflictsSolvingEnd();
		this.idRatedContent = selectionStep.getRatedContent().getIdRatedContent();
		this.idProject = selectionStep.getProject().getIdProject();
		this.tpStatus = selectionStep.getTpStatus().getCode();
		this.vlLowerScore = selectionStep.getVlLowerScore();
	}

	public Long getIdSelectionStep() {
		return idSelectionStep;
	}

	public void setIdSelectionStep(Long idSelectionStep) {
		this.idSelectionStep = idSelectionStep;
	}

	public Integer getNrSerial() {
		return nrSerial;
	}

	public void setNrSerial(Integer nrSerial) {
		this.nrSerial = nrSerial;
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

	public Long getIdRatedContent() {
		return idRatedContent;
	}

	public void setIdRatedContent(Long idRatedContent) {
		this.idRatedContent = idRatedContent;
	}

	public Long getIdProject() {
		return idProject;
	}

	public void setIdProject(Long idProject) {
		this.idProject = idProject;
	}

	public Integer getTpStatus() {
		return tpStatus;
	}

	public void setTpStatus(Integer tpStatus) {
		this.tpStatus = tpStatus;
	}

	public Integer getVlLowerScore() {
		return vlLowerScore;
	}

	public void setVlLowerScore(Integer vlLowerScore) {
		this.vlLowerScore = vlLowerScore;
	}

}
