package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.SecondaryQuestion;

public class SecondaryQuestionCreatedDto {

	private String dsSecondaryQuestion;
	private String dsProjectKey;

	public SecondaryQuestionCreatedDto(SecondaryQuestion secondaryQuestion) {
		this.dsSecondaryQuestion = secondaryQuestion.getDsSecondaryQuestion();
		this.dsProjectKey = secondaryQuestion.getProject().getDsKey();
	}

	public SecondaryQuestionCreatedDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SecondaryQuestionCreatedDto(String dsSecondaryQuestion, String dsProjectKey) {
		super();
		this.dsSecondaryQuestion = dsSecondaryQuestion;
		this.dsProjectKey = dsProjectKey;
	}

	public String getDsSecondaryQuestion() {
		return dsSecondaryQuestion;
	}

	public void setDsSecondaryQuestion(String dsSecondaryQuestion) {
		this.dsSecondaryQuestion = dsSecondaryQuestion;
	}

	public String getDsProjectKey() {
		return dsProjectKey;
	}

	public void setDsProjectKey(String dsProjectKey) {
		this.dsProjectKey = dsProjectKey;
	}
}