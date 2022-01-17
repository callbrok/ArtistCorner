package com.artistcorner.engclasses.others;

import com.artistcorner.engclasses.exceptions.*;

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
            case 0: case 6:
                return new EmptyFieldExceptionView(etm);

            case 1: case 7:
                return new EmpyPathExceptionView(etm);

            case 2: case 8:
                return new DuplicateArtWorkExceptionView(etm);

            case 3: case 9:
                return new ProposalNotFoundExceptionView(etm);

            case 4: case 10:
                return new SellArtWorkNotFoundExceptionView(etm);

            case 5: case 11:
                return new ArtWorkNotFoundExceptionView(etm);

            default:
                return new EmptyFieldExceptionView(etm);
        }
    }

}
