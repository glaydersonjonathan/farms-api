package br.ufs.dcomp.farms.model.dao;

import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.SelectionStep;

@Component
@SuppressWarnings("unchecked")
public class SelectionStepDao extends HibernateDao<SelectionStep>{

	public SelectionStepDao() {
		super(SelectionStep.class);
	}
}
