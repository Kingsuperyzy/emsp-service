package com.yzy.emsp.infrastructure.repository;



import com.yzy.emsp.domain.model.card.Card;
import com.yzy.emsp.domain.model.card.CardNumber;
import com.yzy.emsp.domain.model.card.CardRepository;
import com.yzy.emsp.domain.model.card.CardStatus;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateCardRepository extends HibernateSupport<Card> implements CardRepository {
    HibernateCardRepository(EntityManager entityManager) {
        super(entityManager);
    }


    @Override
    public List<Card> findByCreateTimeBetween(Date startTime, Date endTime, int page, int size) {


        Query<Card> query = getSession().createQuery(
                        " FROM Card where createTime between :startTime and :endTime", Card.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);
        query.setFirstResult((page-1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }


    @Override
    public long countByCreateTimeBetween(Date startTime, Date endTime) {


        Query<Long> query = getSession().createQuery(
                        " select count(*) FROM Card where createTime between :startTime and :endTime", Long.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);

        return query.uniqueResult();
    }


    @Override
    public Card findByCardNo(CardNumber cardNo) {

        if (StringUtils.isEmpty(cardNo)) {
            return null;
        }
        Query<Card> query = getSession().createQuery(
                "from Card where cardNo=:cardNo ", Card.class).setParameter("cardNo", cardNo);
        return query.uniqueResult();
    }

    @Override
    public Integer syncCardStatusWithAccount(String accountNo, CardStatus status) {
        String hql = "UPDATE Card  SET status =:status WHERE userNo=:userNo";
        int updatedCount = getSession().createQuery(hql)
                .setParameter("status", status)
                .setParameter("userNo", accountNo)
                .executeUpdate();
        return updatedCount;
    }



}
