package junittests;


import com.artistcorner.controller.applicationcontroller.UploadArtWork;
import com.artistcorner.engclasses.bean.ArtWorkBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtWorkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author  Marco Purificato
 */
public class TestUploadArtWork {

    @Test
    public void testUploadArtWorkImageNotSelected(){
        UploadArtWork upju = new UploadArtWork();
        int code = -1;

        ArtWorkBean artWorkToCheck = new ArtWorkBean("Titolo Opera",999,1,1,"impressionista");

        try {
            upju.uploadImage(artWorkToCheck,"");
        } catch (DuplicateArtWorkException | EmptyFieldException e) {
            code = 0;
        } catch (EmptyPathException e1){
            code = 1;
        }

        assertEquals(1, code);
    }
}
