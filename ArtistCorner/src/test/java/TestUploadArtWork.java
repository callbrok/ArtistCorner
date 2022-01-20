import com.artistcorner.controller.applicationcontroller.SignUp;
import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.UserBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.DuplicateUserException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUploadArtWork {


    @Test
    public void testUploadArtWorkImageNotSelected(){
        UploadArtWork upju = new UploadArtWork();
        int code = -1;

        ArtWorkBean artWorkToCheck = new ArtWorkBean("Titolo Opera",999,1);

        // Artist with user artista1 already exist.
        try {
            upju.uploadImage(artWorkToCheck, 5,"");
        } catch (DuplicateArtWorkException | EmptyFieldException e) {
            code = 0;
        } catch (EmptyPathException e1){
            code = 1;
        }

        assertEquals(1, code);
    }
}
