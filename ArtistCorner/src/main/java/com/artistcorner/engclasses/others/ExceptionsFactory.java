package com.artistcorner.engclasses.others;

import com.artistcorner.engclasses.exceptions.EmptyFieldExceptionView;
import com.artistcorner.engclasses.exceptions.EmpyPathExceptionView;
import com.artistcorner.engclasses.exceptions.ExceptionView;

import java.io.IOException;

public class ExceptionsFactory {

    //***Singleton***/
    private static ExceptionsFactory instance = null;
    private ExceptionsFactory() {}

    public static synchronized ExceptionsFactory getInstance() {
        if(instance == null) {
            instance = new ExceptionsFactory();
        }
        return instance;
    }

    public ExceptionView createView(ExceptionsTypeMenager etm) throws IOException {

        switch(etm.getType()) {
            case 0:
                return new EmptyFieldExceptionView(etm);

            case 1:
                return new EmpyPathExceptionView(etm);

            default:
                return new EmptyFieldExceptionView(etm);
        }
    }

}
