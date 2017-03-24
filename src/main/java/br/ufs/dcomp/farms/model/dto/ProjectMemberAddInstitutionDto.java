package br.ufs.dcomp.farms.model.dto;

public class ProjectMemberAddInstitutionDto {

	private String dsUserName;
	private Long idInstitution;
	private String dsKey;

	public ProjectMemberAddInstitutionDto() {
		super();
	}

	public String getDsUserName() {
		return dsUserName;
	}

	public void setDsUserName(String dsUserName) {
		this.dsUserName = dsUserName;
	}

	public Long getIdInstitution() {
		return idInstitution;
	}

	public void setIdInstitution(Long idInstitution) {
		this.idInstitution = idInstitution;
	}

	public String getDsKey() {
		return dsKey;
	}

	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}

}
