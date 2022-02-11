package junittests;

import com.artistcorner.controller.applicationcontroller.ViewPendingProposals;
import com.artistcorner.engclasses.bean.ArtGalleryBean;
import com.artistcorner.engclasses.exceptions.ProposalNotFoundException;
import org.junit.Test;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

/**
 * @author  Alessio Torroni
 */

public class TestViewPendingProposal {
    @Test
    public void testRemoveProposalFailed(){
        ViewPendingProposals vsawb = new ViewPendingProposals();
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