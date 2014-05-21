package com.sinosoft.one.monitor.job;
import org.hibernate.HibernateException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
/**
 * Created with IntelliJ IDEA.
 * User: daojian
 * Date: 13-8-23
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractJobDetail extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(AbstractJobDetail.class);
    // spring injected reference
    private EntityManagerFactory entityManagerFactory;



    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Most of this method is copied from the HibernateInterceptor.
     */
    protected final void executeInternal(JobExecutionContext ctx)
            throws JobExecutionException {
        EntityManager entityManager = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        EntityManager newEntityManager = null;
        if (entityManager != null) {
            log.info("Found thread-bound entityManager for TransactionalQuartzTask");
        } else {
            newEntityManager = getEntityManagerFactory().createEntityManager();
            TransactionSynchronizationManager.bindResource(getEntityManagerFactory(),
                    new EntityManagerHolder(newEntityManager));
        }

        try {
            executeTransactional(ctx);
        } catch (HibernateException ex) {
            log.error("Execute arbor schedule task exception.", ex);
            throw ex;
        } finally {
            if (entityManager != null) {
                log.debug("Not closing pre-bound Hibernate Session after TransactionalQuartzTask");
            } else {
                TransactionSynchronizationManager
                        .unbindResource(getEntityManagerFactory());
                EntityManagerFactoryUtils.closeEntityManager(newEntityManager);
            }
        }
    }

    /**
     * Implementing classes, implement this method.
     */
    protected abstract void executeTransactional(JobExecutionContext ctx)
            throws JobExecutionException;
}

