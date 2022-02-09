package junittests;
import com.artistcorner.controller.applicationcontroller.ViewSentArtGalleryProposal;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

/**
 * @author  Alessio Torroni
 */
public class TestSentArtGalleryProposal {
    @Test
    public void testRemoveProposalFailed(){
        ViewSentArtGalleryProposal vsawb = new ViewSentArtGalleryProposal();
        boolean code = true;
        ArtGalleryBean galleryToCheck = new ArtGalleryBean();
        try {
            vsawb.removeProposta(galleryToCheck,17);
        } catch (SQLException | ProposalNotFoundException e) {
            code = false;
        }
        assertEquals(false,code);
    }
}