package com.yzy.emsp.infrastructure.repository;


import com.yzy.emsp.domain.model.account.Account;
import com.yzy.emsp.domain.model.account.AccountNumber;
import com.yzy.emsp.domain.model.account.AccountRepository;
import com.yzy.emsp.domain.model.account.EMAID;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateAccountRepository extends HibernateSupport<Account> implements AccountRepository {
    HibernateAccountRepository(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public List<Account> findByCreateTimeBetween(Date startTime, Date endTime, int page, int size) {

        Query<Account> query = getSession().createQuery(
                        "from Account where createTime between :startTime and :endTime", Account.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        query.setFirstResult((page-1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }


    @Override
    public long countByCreateTimeBetween(Date startTime, Date endTime) {
        Query<Long> query = getSession().createQuery(
                        "select count(*) from Account where createTime between :startTime and :endTime", Long.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);

        return query.uniqueResult();
    }

    @Override
    public long findByEmail(String email) {


        Query<Long> query = getSession().createQuery(
                "select count(*) from Account where email=:email ", Long.class)
                .setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public Account findByAccountNo(AccountNumber accountNo) {

        if (StringUtils.isEmpty(accountNo)) {
            return null;
        }
        Query<Account> query = getSession().createQuery(
                "from Account where accountNo=:accountNo ", Account.class).setParameter("accountNo", accountNo);
        return query.uniqueResult();
    }

    @Override
    public Long findByContractId(EMAID contractId) {
        Query<Long> query = getSession().createQuery(
                        "select count(*) from Account where contractId=:contractId ", Long.class)
                .setParameter("contractId", contractId);
        return query.uniqueResult();
    }
}
