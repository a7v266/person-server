package org.sandbox.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.SimpleProjection;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.IntegerType;
import org.hibernate.type.Type;


/**
 * ConstantProjection - generally this would only be useful in a case ... when but you
 * may have other situations where you want to stick a constant value into a column somewhere.
 * <p>
 * Also this could be used to create other kinds of calculated projections, such as arithmetic
 * projections.
 * <p>
 * Note that this projection has to be add as a projection AND a criterion, and it should be
 * added to the criterion early in the list so that the parameters line up correctly (projections
 * are emitted into the SQL before conditionals).
 */
public class ConstantProjection extends SimpleProjection implements Projection {
    private static final long serialVersionUID = 1L;

    public static final ConstantProjection ZERO = new ConstantProjection(0);
    public static final ConstantProjection ONE = new ConstantProjection(1);

    private TypedValue value;

    public ConstantProjection(TypedValue value) {
        super();
        this.value = value;
    }

    public ConstantProjection(Integer value) {
        super();
        this.value = new TypedValue(IntegerType.INSTANCE, value);
    }

    @Override
    public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
            throws HibernateException {
        return new Type[]{value.getType()};
    }

    @Override
    public String toSqlString(Criteria criteria, int position,
                              CriteriaQuery criteriaQuery) throws HibernateException {
        return "?";
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public Criterion criterion() {
        return new Criterion() {
            private static final long serialVersionUID = 1L;

            @Override
            public TypedValue[] getTypedValues(Criteria criteria,
                                               CriteriaQuery criteriaQuery) throws HibernateException {
                return new TypedValue[]{value};
            }

            // Always true in this case ...
            @Override
            public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
                    throws HibernateException {
                return "1=1";
            }
        };
    }

}
