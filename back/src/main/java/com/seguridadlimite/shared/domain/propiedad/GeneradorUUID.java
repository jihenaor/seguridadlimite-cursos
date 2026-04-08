package com.seguridadlimite.shared.domain.propiedad;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

public final class GeneradorUUID {

    private GeneradorUUID(){
        //clase final
    }
    public static long generarLongitudNumericaQuince(){

        LocalDateTime date = LocalDateTime.now();

        String total = String.format("%s%s%s", date.getLong(ChronoField.MILLI_OF_DAY), date.getDayOfYear(), date.getYear());
        return Long.parseLong(total);
    }
}
