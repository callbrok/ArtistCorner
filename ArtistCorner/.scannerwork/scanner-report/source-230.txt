package junittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLogin.class,
        TestSignUp.class,
        TestUploadArtwork.class,
        TestViewPendingProposal.class,
        TestFindArtwork.class,
        TestManageArtworkGallery.class
})

public class AllTests {
}

