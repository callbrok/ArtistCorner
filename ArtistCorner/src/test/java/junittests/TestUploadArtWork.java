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

        ArtWorkBean artWorkToCheck = new ArtWorkBean();

        artWorkToCheck.setTitolo("Titolo Opera");
        artWorkToCheck.setCategoria("impressionista");
        artWorkToCheck.setArtistId(1);
        artWorkToCheck.setPrezzo(999);
        artWorkToCheck.setFlagVendibile(1);

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
