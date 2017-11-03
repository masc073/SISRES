package converter;

import dominio.EntidadeNegocio;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (EntidadeNegocio) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        if (entity != null && entity instanceof EntidadeNegocio) {
            component.getAttributes().put(((EntidadeNegocio) entity).getId().toString(), entity);
            return ((EntidadeNegocio) entity).getId().toString();
        }

        return null;
    }

}