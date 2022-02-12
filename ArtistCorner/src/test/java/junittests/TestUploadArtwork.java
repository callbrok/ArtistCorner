package junittests;


import com.artistcorner.controller.applicationcontroller.UploadArtwork;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.exceptions.DuplicateArtworkException;
import com.artistcorner.engclasses.exceptions.EmptyFieldException;
import com.artistcorner.engclasses.exceptions.EmptyPathException;
import com.artistcorner.engclasses.exceptions.ImageTooLargeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author  Marco Purificato
 */
public class TestUploadArtwork {

    @Test
    public void testUploadArtWorkImageNotSelected(){
        UploadArtwork upju = new UploadArtwork();
        int code = -1;

        ArtworkBean artWorkToCheck = new ArtworkBean();

        artWorkToCheck.setTitolo("Titolo Opera");
        artWorkToCheck.setCategoria("impressionista");
        artWorkToCheck.setArtistId(1);
        artWorkToCheck.setPrezzo(999);
        artWorkToCheck.setFlagVendibile(1);

        try {
            upju.uploadImage(artWorkToCheck,"");
        } catch (DuplicateArtworkException | EmptyFieldException | ImageTooLargeException e) {
            code = 0;
        } catch (EmptyPathException e1){
            code = 1;
        }

        assertEquals(1, code);
    }
}
