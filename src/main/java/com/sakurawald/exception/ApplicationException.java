package com.sakurawald.exception;

import javafx.application.Application;

import java.io.PrintWriter;
import java.io.StringWriter;

/** marker interface */
public class ApplicationException extends Throwable {

    public static String getExceptionDetails(Throwable throwable) {
        PrintWriter pw = new PrintWriter(new StringWriter());
        throwable.printStackTrace(pw);
        return pw.toString();
    }

    public static void throwRuntimeException(RuntimeException e){
        throw e;
    }

}

