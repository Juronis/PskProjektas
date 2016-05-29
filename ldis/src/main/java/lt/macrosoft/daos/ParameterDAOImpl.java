package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Parameter;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 * Created by Main on 5/29/2016.
 */
public class ParameterDAOImpl extends GenericDAOImpl<Parameter, Long> implements ParameterDAO {

    @Inject
    public ParameterDAOImpl(EntityManager em) { super(em, Parameter.class); }

    public Optional<Parameter> findParameterValue(String parameter) {
        try {
            return Optional.fromNullable(getEntityManager().createNamedQuery("Parameters.find", Parameter.class)
                    .setParameter("name", changeParameterNameToDBParameterNAme(parameter)).getSingleResult());
        } catch (NoResultException e) {
            return Optional.absent();
        }
    }

    public Parameter save(Parameter parameter) {
        em.persist(parameter);
        return parameter;
    }

    private String changeParameterNameToDBParameterNAme (String parameter){
        switch (parameter) {
            case "MEMBERSHIP_PRICE" : return "MEMBERSHIP_PRICE";
            default: return "";
        }
    }

}
