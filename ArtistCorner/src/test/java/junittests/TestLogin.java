package junittests;

import com.artistcorner.controller.applicationcontroller.Login;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.UserNotFoundException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author  Marco Purificato
 */
public class TestLogin {

    @Test
    public void testDesktopLoginInvalidArtistUser(){
//        Login lgju = new Login();
//        int code = -1;
//
//        UserBean userToCheck = new UserBean("artista45", "ispw21", "artista");
//
//        // Artist with user "artista45" doesn't exist.
//        try {
//            lgju.credentialLogin(userToCheck, null,"D");
//        } catch (UserNotFoundException e) {
//            code = 1;
//        } catch (IOException e) {
//            code = 0;
//        }
//
//        assertEquals(1, code);
    }

}
