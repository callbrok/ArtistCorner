package com.artistcorner.engclasses.others;

import com.artistcorner.engclasses.exceptions.*;

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

    public ExceptionView createView(ExceptionsTypeManager etm) {

        switch(etm.getType()) {
            case 0: case 7:
                return new EmptyFieldExceptionView(etm);

            case 1: case 8:
                return new EmpyPathExceptionView(etm);

            case 2: case 9:
                return new DuplicateArtworkExceptionView(etm);

            case 3: case 10:
                return new ProposalNotFoundExceptionView(etm);

            case 4: case 11:
                return new SellArtworkNotFoundExceptionView(etm);

            case 5: case 12:
                return new ArtworkNotFoundExceptionView(etm);

            case 6: case 13:
                return new SentProposalNotFoundExceptionView(etm);

            default:
                return new EmptyFieldExceptionView(etm);
        }
    }

}
