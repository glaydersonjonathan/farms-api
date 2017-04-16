package br.ufs.dcomp.farms.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "base_use_criteria")
@XmlRootElement
public class BaseUseCriteria implements Serializable {

	private static final long serialVersionUID = 1L;
	private SearchEngine searchEngine;
	private String dsBaseUseCriteria;
	private Project project;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_project", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_search_engine", nullable = false)
	public SearchEngine getSearchEngine() {
		return searchEngine;
	}

	public void setSearchEngine(SearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}

	@Column(name = "ds_base_use_criteria", nullable = false, unique = true)
	public String getDsBaseUseCriteria() {
		return dsBaseUseCriteria;
	}

	public void setDsBaseUseCriteria(String dsBaseUseCriteria) {
		this.dsBaseUseCriteria = dsBaseUseCriteria;
	}
}
