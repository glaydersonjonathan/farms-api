package br.ufs.dcomp.farms.model.dto;


public class ProjectMemberCreateDto {

	private String dsUserName;
	private Long idInstitution;
	private String dsKey;

	public ProjectMemberCreateDto() {
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
