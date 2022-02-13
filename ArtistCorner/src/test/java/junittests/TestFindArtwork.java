package junittests;
import com.artistcorner.controller.applicationcontroller.FindArtwork;
import com.artistcorner.controller.applicationcontroller.ViewFavourites;
import com.artistcorner.engclasses.bean.ArtistBean;
import com.artistcorner.engclasses.bean.ArtworkBean;
import com.artistcorner.engclasses.bean.BuyerBean;
import com.artistcorner.engclasses.exceptions.ArtworkNotFoundException;
import com.artistcorner.engclasses.exceptions.BuyArtworkManagementProblemException;
import com.artistcorner.engclasses.exceptions.FavouritesManagementProblemException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author  Alessio Torroni
 */
public class TestFindArtwork {

    @Test
    public void testSearchArtwork(){
        FindArtwork faw = new FindArtwork();
        ArtworkBean aw = new ArtworkBean();
        aw.setTitolo(".,;");
        aw.setCategoria("stilizzato");
        boolean code = true;
        try {
            faw.retrieveSearchArtWorkByName(aw);
        } catch (ArtworkNotFoundException e) {
            code = false;
        }
        assertEquals(false,code);
    }

}
