package junittests;
import com.artistcorner.controller.applicationcontroller.ViewPendingProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.exceptions.SentProposalNotFoundException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author  Alessio Torroni
 */
public class TestManageArtworkGallery {
    @Test
    public void testSentProposalFailed(){
        ViewPendingProposals vpg = new ViewPendingProposals();
        boolean success = true ;
        ArtGalleryBean galleryToCheck = new ArtGalleryBean();
        galleryToCheck.setNome("Il Mondo dell''Arte");
        galleryToCheck.setGalleria(5);
        galleryToCheck.setDescrizione("descrizione galleria 1");
        galleryToCheck.setIndirizzo("Via dei Castani, 193, 00172 Roma RM");
        try {
            vpg.retrieveProposal(galleryToCheck,1);
        } catch (SentProposalNotFoundException e) {
            success = false ;
        }
        assertEquals(false,success);
    }
}
