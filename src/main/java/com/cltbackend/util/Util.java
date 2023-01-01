package com.cltbackend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {

    public static boolean validarCorreo(String correo){

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        return mather.find();
    }
}
