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
    public ParameterDAOImpl(EntityManager em) {
        super(em, Parameter.class);
    }

    public Optional<Parameter> findParameterValue(String parameterName) {
        try {
            return Optional.fromNullable(getEntityManager().createNamedQuery("Parameters.find", Parameter.class)
                    .setParameter("name", changeParameterNameToDBParameterName(parameterName).toUpperCase()).getSingleResult());
        } catch (NoResultException e) {
            return Optional.absent();
        }
    }

    @Override
    public Parameter save(Parameter parameter) {
        parameter.setName(changeParameterNameToDBParameterName(parameter.getName().toUpperCase()).toUpperCase());
        return super.save(parameter);
    }

    public Parameter saveOrUpdate(Parameter parameter) {
        Optional<Parameter> updateParameter = findParameterValue(parameter.getName());
        Parameter paramete;
        if (updateParameter.isPresent()) {
            paramete = updateParameter.get();
            paramete.setPvalue(parameter.getPvalue());
            save(paramete);
        } else {
            save(parameter);
        }
        return parameter;
    }

    private String changeParameterNameToDBParameterName(String parameter) {
        switch (parameter.toUpperCase()) {
            case "MEMBERSHIP_PRICE":
                return "MEMBERSHIP_PRICE";
            case "MAX_MEMBERS":
                return "MAX_MEMBERS";
            case "MAX_REGISTRATION_DAYS":
                return "MAX_REGISTRATION_DAYS";
            case "MINIMUM_RECOMMENDATIONS":
                return "MINIMUM_RECOMMENDATIONS";
            case "BIRTHDAY_REQUIRED":
                return "BIRTHDAY_REQUIRED";
            default:
                return "";
        }
    }

}
