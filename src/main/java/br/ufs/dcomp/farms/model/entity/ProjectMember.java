package br.ufs.dcomp.farms.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.ufs.dcomp.farms.model.enums.RoleEnum;
import br.ufs.dcomp.farms.model.enums.StateEnum;

@Entity
@Table(name = "project_member")
@XmlRootElement
@SequenceGenerator(name = "ProjectMemberSequenceGenerator", sequenceName = "sq_project_member")
public class ProjectMember implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long idProjectMember;
	private Researcher researcher;
	private Project project;
	private RoleEnum tpRole;
	
	private StateEnum tpState;
	
	public ProjectMember() {}

	public ProjectMember(Researcher researcher, Project project,RoleEnum tpRole) {
		this.researcher = researcher;
		this.project = project;
		this.tpRole = tpRole;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ProjectMemberSequenceGenerator")
	@Column(name = "id_project_member", nullable = false, unique = true)
	public Long getIdProjectMember() {
		return idProjectMember;
	}
	
	public void setIdProjectMember(Long idProjectMember) {
		this.idProjectMember = idProjectMember;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_researcher", nullable = false)
	public Researcher getResearcher() {
		return researcher;
	}
	
	public void setResearcher(Researcher researcher) {
		this.researcher = researcher;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_project", nullable = false)
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	
	@Column(name = "tp_role", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	public RoleEnum getTpRole() {
		return tpRole;
	}

	public void setTpRole(RoleEnum tpRole) {
		this.tpRole = tpRole;
	}
	
	@Column(name = "tp_state", nullable = false)
	@Enumerated(EnumType.STRING)
	public StateEnum getTpState() {
		return tpState;
	}

	public void setTpState(StateEnum tpState) {
		this.tpState = tpState;
	}
}
