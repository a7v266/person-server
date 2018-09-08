package org.sandbox.service.persistence;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.sandbox.domain.BaseEntity;
import org.sandbox.domain.BaseEntityCustomId;
import org.sandbox.hibernate.EntityScroll;
import org.sandbox.hibernate.Scroll;
import org.sandbox.service.search.Filter;
import org.sandbox.service.search.Search;
import org.sandbox.service.search.Sort;
import org.sandbox.utils.CollectionUtils;
import org.sandbox.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class BasePersistenceImpl<T extends BaseEntity, K extends Serializable> implements BasePersistence<T, K> {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    private AtomicLong sequence;

    private final Class<T> clazz;

    protected BasePersistenceImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    protected SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @PostConstruct
    public void initSequence() {
        if (clazz.getSuperclass() == BaseEntityCustomId.class) {
            try (Session session = getSessionFactory().openSession()) {
                Criteria criteria = session.createCriteria(clazz);
                criteria.setProjection(Projections.max(BaseEntity.ID));
                sequence = new AtomicLong(NumberUtils.toLong(criteria.uniqueResult()));
            }
        }
    }

    @Override
    public T get(K id) {
        if (id == null) {
            return null;
        }
        return getCurrentSession().get(clazz, id);
    }

    @Override
    public T get(K id, Consumer<K> consumer) {
        if (id == null) {
            return null;
        }
        T entity = getCurrentSession().get(clazz, id);
        if (entity == null) {
            consumer.accept(id);
        }
        return entity;
    }

    @Override
    public T load(K id) {
        if (id == null) {
            return null;
        }
        return getCurrentSession().load(clazz, id);
    }

    @Override
    public K save(T object) {
        generatePrimaryKey(object);
        return (K) getCurrentSession().save(object);
    }

    @Override
    public void update(T object) {
        getCurrentSession().update(object);
    }

    @Override
    public void saveOrUpdate(T object) {
        generatePrimaryKey(object);
        getCurrentSession().saveOrUpdate(object);
    }

    @Override
    public T merge(T object) {
        generatePrimaryKey(object);
        return (T) getCurrentSession().merge(object);
    }

    @Override
    public void delete(T object) {
        if (object == null) {
            return;
        }
        getCurrentSession().delete(object);
    }

    @Override
    public List<T> list(Search search) {
        Criteria criteria = createCriteria();
        criteria.setFirstResult(search.getOffset());
        criteria.setMaxResults(search.getLimit());
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        setLazyList(criteria, search);
        return criteria.list();
    }

    @Override
    public List<Map<String, Object>> list(Projection projection, Search search) {
        Criteria criteria = createCriteria();
        criteria.setFirstResult(search.getOffset());
        criteria.setMaxResults(search.getLimit());
        criteria.setProjection(projection);
        criteria.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        return criteria.list();
    }

    @Override
    public Long count() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }


    @Override
    public Long count(Search search) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());
        setAliases(criteria, search);
        setFilters(criteria, search);
        return (Long) criteria.uniqueResult();
    }

    @Override
    public Scroll<T> scroll(Search search, boolean readOnly) {
        Criteria criteria = createCriteria();
        criteria.setReadOnly(readOnly);
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        return new EntityScroll<>(criteria.scroll(ScrollMode.FORWARD_ONLY));
    }

    @Override
    public Scroll<T> scroll(Search search) {
        return scroll(search, false);
    }

    @Override
    public void scroll(Search search, Consumer<T> consumer) {
        scroll(search).forEach(consumer);
    }

    @Override
    public void scroll(Search search, boolean readOnly, Consumer<T> consumer) {
        scroll(search, readOnly).forEach(consumer);
    }

    @Override
    public void evict(T object) {
        getCurrentSession().evict(object);
    }

    @Override
    public T uniqueResult(Search search) {
        Criteria criteria = createCriteria();
        setAliases(criteria, search);
        setFilters(criteria, search);
        return (T) criteria.uniqueResult();
    }

    @Override
    public T uniqueResult(Search search, FlushMode flushMode) {
        Criteria criteria = createCriteria();
        criteria.setFlushMode(flushMode);
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        return (T) criteria.uniqueResult();
    }

    @Override
    public List<K> getIds(Search search) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.property(BaseEntity.ID));
        criteria.setMaxResults(Short.MAX_VALUE);
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        return criteria.list();
    }

    @Override
    public T first(Search search) {
        Criteria criteria = createCriteria();
        criteria.setMaxResults(1);
        setAliases(criteria, search);
        setFilters(criteria, search);
        setSorts(criteria, search);
        setLazyList(criteria, search);
        return (T) CollectionUtils.getFirst(criteria.list());
    }

    @Override
    public boolean isEmpty() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.property(BaseEntity.ID));
        criteria.setMaxResults(1);
        return criteria.list().size() == 0;
    }

    @Override
    public boolean isNotEmpty(Search search) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.property(BaseEntity.ID));
        criteria.setMaxResults(1);
        setAliases(criteria, search);
        setFilters(criteria, search);
        return criteria.list().size() > 0;
    }

    @Override
    public Long createId() {
        return sequence.incrementAndGet();
    }

    protected Query getNamedQuery(String queryName) {
        return getCurrentSession().getNamedQuery(queryName);
    }

    private Criterion createCriterion(Filter filter) {
        switch (filter.getOperator()) {
            case EQUALS:
                return Restrictions.eq(filter.getProperty(), filter.getValue());
            case NOT_EQUALS:
                return Restrictions.ne(filter.getProperty(), filter.getValue());
            case GREATER:
                return Restrictions.gt(filter.getProperty(), filter.getValue());
            case GREATER_EQUAL:
                return Restrictions.ge(filter.getProperty(), filter.getValue());
            case LESS:
                return Restrictions.lt(filter.getProperty(), filter.getValue());
            case LESS_EQUAL:
                return Restrictions.le(filter.getProperty(), filter.getValue());
            case IS_NULL:
                return Restrictions.isNull(filter.getProperty());
            case IS_NOT_NULL:
                return Restrictions.isNotNull(filter.getProperty());
            case IN:
                return Restrictions.in(filter.getProperty(), filter.getValues());
            case BETWEEN:
                return Restrictions.between(filter.getProperty(), filter.getLowBound(), filter.getHighBound());
            case LIKE_ANYWHERE:
                return Restrictions.ilike(filter.getProperty(), (String) filter.getValue(), MatchMode.ANYWHERE);
            case LIKE_START:
                return Restrictions.ilike(filter.getProperty(), (String) filter.getValue(), MatchMode.START);
            case LIKE_EXACT:
                return Restrictions.ilike(filter.getProperty(), (String) filter.getValue(), MatchMode.EXACT);
            case AND:
                return Restrictions.and(createCriterionArray(filter.getFilters()));
            case OR:
                return Restrictions.or(createCriterionArray(filter.getFilters()));
            case NOT:
                return Restrictions.not(createCriterion(filter.getFilter()));
        }
        throw new UnsupportedOperationException(filter.toString());
    }

    private Criterion[] createCriterionArray(Collection<Filter> filters) {
        Criterion[] criterionArray = new Criterion[filters.size()];
        int index = 0;
        for (Filter filter : filters) {
            criterionArray[index++] = createCriterion(filter);
        }
        return criterionArray;
    }

    protected Criteria createCriteria() {
        return getCurrentSession().createCriteria(clazz);
    }

    private void setAliases(Criteria criteria, Search search) {
        search.getAliases().forEach((aliasName, aliasPath) -> criteria.createAlias(aliasPath, aliasName, JoinType.LEFT_OUTER_JOIN));
    }

    private void setFilters(Criteria criteria, Search search) {
        search.getFilters().forEach(filter -> criteria.add(createCriterion(filter)));
    }

    private void setSorts(Criteria criteria, Search search) {
        for (Sort sort : search.getSorts()) {
            if (sort.isDesc()) {
                criteria.addOrder(Order.desc(sort.getProperty()).nulls(NullPrecedence.LAST));
            } else {
                criteria.addOrder(Order.asc(sort.getProperty()).nulls(NullPrecedence.LAST));
            }
        }
        criteria.addOrder(Order.asc(BaseEntity.ID));
    }

    private void setLazyList(Criteria criteria, Search search) {
        search.getLazyList().forEach(lazyPath -> criteria.setFetchMode(lazyPath, FetchMode.SELECT));
    }

    private void generatePrimaryKey(T entity) {
        if (entity instanceof BaseEntityCustomId) {
            if (entity.getId() == null) {
                entity.setId(sequence.incrementAndGet());
            } else {
                sequence.getAndUpdate(value -> Math.max(value, entity.getId()));
            }
        }
    }
}