package io.hops.hopsworks.common.dao.hdfsUser;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import io.hops.hopsworks.common.dao.AbstractFacade;
import java.util.List;

@Stateless
public class HdfsUsersFacade extends AbstractFacade<HdfsUsers> {

  @PersistenceContext(unitName = "kthfsPU")
  private EntityManager em;

  public HdfsUsersFacade() {
    super(HdfsUsers.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public HdfsUsers findHdfsUser(byte[] id) {
    return em.find(HdfsUsers.class, id);
  }

  public HdfsUsers findByName(String name) {
    try {
      return em.createNamedQuery("HdfsUsers.findByName", HdfsUsers.class).
              setParameter(
                      "name", name).getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  public List<HdfsUsers> findProjectUsers(String projectName) {
    try {
      return em.createNamedQuery("HdfsUsers.findProjectUsers", HdfsUsers.class).
              setParameter("name", projectName).
              getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }
  
  public void persist(HdfsUsers user) {
    em.persist(user);
  }

  public void merge(HdfsUsers user) {
    em.merge(user);
  }

  public void removeHdfsUser(HdfsUsers user) {
    if (user == null) {
      return;
    }
    HdfsUsers u = em.find(HdfsUsers.class, user.getId());
    if (u != null) {
      em.createNamedQuery("HdfsUsers.delete", HdfsUsers.class).
              setParameter("id", user.getId()).executeUpdate();
    }
  }
}
