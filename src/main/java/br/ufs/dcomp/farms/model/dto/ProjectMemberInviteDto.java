package br.ufs.dcomp.farms.model.dto;

public class ProjectMemberInviteDto {
	private String dsEmail;
	private Long idInstitution;
	private String dsKey;
	
	public ProjectMemberInviteDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
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
