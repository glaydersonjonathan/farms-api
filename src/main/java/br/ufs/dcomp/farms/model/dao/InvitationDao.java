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

    /**
     * get pendents invitations of researcher.
     *
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
        return invitations;
    }

    /**
     * getInvitationsByProjectAndDSSO
     *
     * @param idProject
     * @param idResearcher
     * @return
     */
    public List<Invitation> getInvitationsByProjectAndDSSO(Long idProject, Long idResearcher) {
        StringBuilder sbHql = new StringBuilder();
        sbHql.append("from Invitation i");
        sbHql.append(" where i.researcher.idResearcher = :idResearcher and i.dhConfirmation = null and i.project.idProject = :idProject");

        Query query = getSession().createQuery(sbHql.toString());
        query.setParameter("idResearcher", idResearcher);
        query.setParameter("idProject", idProject);
        List<Invitation> invitations = query.list();
        return invitations;
    }

    /**
     * Decline a invitation
     *
     * @param idInvitation
     */
    public void delete(Long idInvitation) {
        String hql = "delete from Invitation where idInvitation= :idInvitation";
        Query query = getSession().createQuery(hql);
        query.setLong("idInvitation", idInvitation);
        query.executeUpdate();
    }

    /**
     * Accept a invitation.
     *
     * @param idInvitation
     */
    public void accept(Long idInvitation) {
        Query query = getSession().createQuery(
                "update Invitation set dhConfirmation = :dhConfirmation where idInvitation = :idInvitation");
        query.setParameter("dhConfirmation", new Date(System.currentTimeMillis()));
        query.setParameter("idInvitation", idInvitation);
        query.executeUpdate();
    }

}
