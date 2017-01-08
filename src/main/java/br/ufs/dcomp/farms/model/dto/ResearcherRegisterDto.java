package br.ufs.dcomp.farms.model.dto;

import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.enums.StateEnum;
import br.ufs.dcomp.farms.model.enums.YesNoEnum;

public class ResearcherRegisterDto {

	private String nmResearcher;
	private String dsSSO;
	private String dsEmail;
	private String dsPassword;
	private String dsConfirmPassword;
	private StateEnum tpState;
	
	private Long idResearcher;
	private String cdUuid;
	private YesNoEnum tpConfirmed;
	
	public ResearcherRegisterDto() {
		super();
	}

	public ResearcherRegisterDto(String nmResearcher, String dsSSO, String dsEmail, String dsPassword, String dsConfirmPassword, StateEnum tpState) {
		super();
		this.nmResearcher = nmResearcher;
		this.dsSSO = dsSSO;
		this.dsEmail = dsEmail;
		this.dsPassword = dsPassword;
		this.dsConfirmPassword = dsConfirmPassword;
		this.setTpState(tpState);
	}
	
	public ResearcherRegisterDto(Researcher researcher) {
		this.nmResearcher = researcher.getNmResearcher();
		this.dsSSO = researcher.getDsSSO();
		this.dsEmail = researcher.getDsEmail();
		this.dsPassword = researcher.getDsPassword();
		this.tpState = researcher.getTpState();
		this.cdUuid = researcher.getCdUuid();
		this.idResearcher = researcher.getIdResearcher();
		this.tpConfirmed = researcher.getTpConfirmed();
			}
	
	
	public Long getIdResearcher() {
		return idResearcher;
	}

	public void setIdResearcher(Long idResearcher) {
		this.idResearcher = idResearcher;
	}

	public String getCdUuid() {
		return cdUuid;
	}

	public void setCdUuid(String cdUuid) {
		this.cdUuid = cdUuid;
	}

	public YesNoEnum getTpConfirmed() {
		return tpConfirmed;
	}

	public void setTpConfirmed(YesNoEnum tpConfirmed) {
		this.tpConfirmed = tpConfirmed;
	}

	public String getNmResearcher() {
		return nmResearcher;
	}
	
	public void setNmResearcher(String nmResearcher) {
		this.nmResearcher = nmResearcher;
	}
	
	public String getDsSSO() {
		return dsSSO;
	}
	
	public void setDsSSO(String dsSSO) {
		this.dsSSO = dsSSO;
	}
	
	public String getDsEmail() {
		return dsEmail;
	}
	
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}
	
	public String getDsPassword() {
		return dsPassword;
	}
	
	public void setDsPassword(String dsPassword) {
		this.dsPassword = dsPassword;
	}
	
	public String getDsConfirmPassword() {
		return dsConfirmPassword;
	}
	
	public void setDsConfirmPassword(String dsConfirmPassword) {
		this.dsConfirmPassword = dsConfirmPassword;
	}

	public StateEnum getTpState() {
		return tpState;
	}

	public void setTpState(StateEnum tpState) {
		this.tpState = tpState;
	}
}