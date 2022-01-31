package junittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLogin.class,
        TestSignUp.class,
        TestUploadArtWork.class,
       // TestSearchArtWorkBuyer.class,
        TestViewFavouritesBuyer.class,
        TestViewProfileGallery.class
})

public class AllTests {
}

