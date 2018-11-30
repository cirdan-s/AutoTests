package tests.suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.*;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ArticleTests.class,
        ChangeAppConditionsTests.class,
        GetStartedTest.class,
        MyListsTest.class,
        SearchTests.class
}
)

public class TestSuite {
}
