package junittests;

import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author  Marco Purificato
 */
public class TestSignUp {

    @Test
    public void testRegistrationDuplicateArtistUsername(){
        SignUp sgup = new SignUp();
        boolean duplicateUsername = false;

        ArtistBean artistToCheck = new ArtistBean();

        artistToCheck.setNome("Alba");
        artistToCheck.setCognome("DeRose");

        UserBean userToCheck = new UserBean();

        userToCheck.setUsername("artista1");
        userToCheck.setPassword("ispw21");
        userToCheck.setRole("artista");

        // Artist with user artista1 already exist.
        try {
            sgup.registerArtist(userToCheck, artistToCheck);
        } catch (DuplicateUserException e) {
            duplicateUsername=true;
        }
        assertEquals(true, duplicateUsername);
    }
}