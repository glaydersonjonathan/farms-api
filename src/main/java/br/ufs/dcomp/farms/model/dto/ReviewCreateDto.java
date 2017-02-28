package br.ufs.dcomp.farms.model.dto;

import java.util.Date;
import java.util.List;

import br.ufs.dcomp.farms.model.entity.Researcher;
import br.ufs.dcomp.farms.model.entity.Study;

public class ReviewCreateDto {
	private List<Long> studies;
	private String dsSSO;
	private Date dhAssign;

	public ReviewCreateDto(List<Long> studies, String dsSSO, Date dhAssign) {
		super();
		this.studies = studies;
		this.dsSSO = dsSSO;
		this.dhAssign = dhAssign;
	}

	public ReviewCreateDto() {
		super();
	}

	public List<Long> getStudies() {
		return studies;
	}

	public void setStudies(List<Long> studies) {
		this.studies = studies;
	}

	public String getDsSSO() {
		return dsSSO;
	}

	public void setDsSSO(String dsSSO) {
		this.dsSSO = dsSSO;
	}

	public Date getDhAssign() {
		return dhAssign;
	}

	public void setDhAssign(Date dhAssign) {
		this.dhAssign = dhAssign;
	}
}
