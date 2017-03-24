package br.ufs.dcomp.farms.model.dto;

public class ProjectMemberInviteDto {
	private String dsEmail;
	private String dsKey;

	public ProjectMemberInviteDto() {
		super();
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsKey() {
		return dsKey;
	}

	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}

}
