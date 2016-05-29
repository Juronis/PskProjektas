package lt.macrosoft.daos;

import com.google.common.base.Optional;
import lt.macrosoft.entities.Parameter;


/**
 * Created by Main on 5/29/2016.
 */
public interface ParameterDAO extends GenericDAO<Parameter, Long> {

        public Optional<Parameter> findParameterValue(String parameter);

        public Parameter save(Parameter parameter);
        
        public Parameter saveOrUpdate(Parameter parameter);

}
