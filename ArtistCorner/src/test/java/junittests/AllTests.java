package junittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLogin.class,
        TestSignUp.class,
        TestUploadArtWork.class,
        TestSentArtGalleryProposal.class,
        TestViewFavouritesBuyer.class,
        TestViewProfileGallery.class
})

public class AllTests {
}

