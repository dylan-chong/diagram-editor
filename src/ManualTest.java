/**
 * Created by Dylan on 17/05/16.
 */
public class ManualTest {

    public static void runTests() {
        testBounds();
    }

    public static void testBounds() {
        DEBounds[] boundses = {
            new DEBounds(new DEPoint(1, 2), new DEPoint(2, 3)),
            new DEBounds(new DEPoint(2, 1), new DEPoint(2, 3)),
            new DEBounds(new DEPoint(2, 2), new DEPoint(3, 3)),
            new DEBounds(new DEPoint(2, 30), new DEPoint(2, 30)),
        };

    }
}
