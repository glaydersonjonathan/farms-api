package br.ufs.dcomp.farms.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import br.ufs.dcomp.farms.model.entity.Invitation;

@Component
@SuppressWarnings("unchecked")
public class InvitationDao extends HibernateDao<Invitation> {

	public InvitationDao() {
		super(Invitation.class);
	}

	/**get pendents invitations of researcher.
	 * @param dsSSO
	 * @return
	 */
	public List<Invitation> getInvitations(String dsSSO) {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("from Invitation i");
		sbHql.append(" join fetch i.project p");
		sbHql.append(" where i.researcher.dsSSO = :dsSSO and i.dhConfirmation = null");

		Query query = getSession().createQuery(sbHql.toString());
		query.setParameter("dsSSO", dsSSO);
		List<Invitation> invitations = query.list();
		return invitations ;
	}
	
	/**
	 * Decline a invitation
	 * @param id
	 */
	public void delete (Long id){
		String hql = "delete from Invitation where idInvitation= :id";
		Query query = getSession().createQuery(hql);
		query.setLong("id", id);
		System.out.println(query.executeUpdate());
	}
	
	/**
	 * Accept a invitation.
	 * @param id
	 */
	public void accept (Long id){
		Query query = getSession().createQuery(
				"update Invitation set dhConfirmation = :dhConfirmation where idInvitation = :idInvitation");
		query.setParameter("dhConfirmation", new Date(System.currentTimeMillis()));
		query.setParameter("idInvitation", id);
		System.out.println(query.executeUpdate());
	}

}
