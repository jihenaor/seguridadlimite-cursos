package com.seguridadlimite.shared.domain.propiedad;

import com.seguridadlimite.shared.domain.exceptions.ExcepcionInformacionInvalida;
import lombok.Getter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

/**
 * Value object padre de todos los value objects tipo date
 *
 * @author carlos.guzman
 * @create 21/12/2020
 **/
public abstract class ValueDateObjectDomain implements Serializable {

    private static final String FORMATO_FECHA = "yyyy/MM/dd";

    @Getter
    protected final Date value;

    protected String valueTexto;

    public ValueDateObjectDomain(Date value) {
        this.value = value;
        obtenerFechaCadena(value);
    }

    public ValueDateObjectDomain() {
        this.value = null;
    }

    public ValueDateObjectDomain(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_FECHA);

        try {
            this.value = simpleDateFormat.parse(date);
        } catch (ParseException ex){
            throw new ExcepcionInformacionInvalida(getMensajeFormatoFecha(date));
        }
    }

    public ValueDateObjectDomain(String date, String formato){
        formato = formato.replace("/","-");
        date = date.replace("/","-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
        try {
            Date dateMinima = simpleDateFormat.parse("1800-01-01");
            this.value = simpleDateFormat.parse(date);
            if (this.value.before(dateMinima)){
                throw new ExcepcionInformacionInvalida(getMensajeFormatoFecha(date));
            }
        } catch (ParseException ex){
            throw new ExcepcionInformacionInvalida(getMensajeFormatoFecha(date));
        }
    }

    public String getValueTexto() {
        obtenerFechaCadena(this.value);
        return this.valueTexto;
    }

    private void obtenerFechaCadena(Date fecha){
        Optional<Date> values = Optional.ofNullable(fecha);
        values.ifPresent(x -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATO_FECHA);
            this.valueTexto = simpleDateFormat.format(fecha);
        });


    }

    public String obtenerMesComoTexto() {
        SimpleDateFormat formato = new SimpleDateFormat("MMMM",  new Locale ( "es" , "ES" ));
        return formato.format(value);
    }

    private String getMensajeFormatoFecha(String date) {
        return String.format("Se presento un error al obtener una fecha valida. fecha recibida: %s", date);
    }
}
