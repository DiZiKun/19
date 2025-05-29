import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test class for map utility functions
 */
public class MapTest {
    
    /**
     * Test implementation of Map utility class
     */
    private static class TestMap {
        public final static ArrayList<Integer> blankLayout = new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(4);
            add(5);
            add(6);
            add(11);
            add(24);
            add(29);
            add(30);
            add(31);
            add(34);
            add(35);
        }};
        
        public final static int rows = 6;
        public final static HashMap<Integer, int[]> coordinatesMatcher = new HashMap<>();
        public final static HashMap<String, Integer> numberMatcher = new HashMap<>();
        public final static HashMap<Integer, String> adventurerMatcher = new HashMap<>();
        
        public static void setLocation() {
            setUpCoordinateAndNumberMatchers();
            setUpAdventurerMatcher();
        }
        
        private static void setUpCoordinateAndNumberMatchers() {
            int[][] coordinates = {
                {0, 2}, {0, 3},
                {1, 1}, {1, 2}, {1, 3}, {1, 4},
                {2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5},
                {3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5},
                {4, 1}, {4, 2}, {4, 3}, {4, 4},
                {5, 2}, {5, 3}
            };
            
            for (int i = 0; i < coordinates.length; i++) {
                int[] coord = coordinates[i];
                coordinatesMatcher.put(i, coord);
                numberMatcher.put(Arrays.toString(coord), i);
            }
        }
        
        private static void setUpAdventurerMatcher() {
            String[] adventurers = {"Diver", "Engineer", "Explorer", "Messenger", "Navigator", "Pilot"};
            for (int i = 0; i < adventurers.length; i++) {
                adventurerMatcher.put(i, adventurers[i]);
            }
        }
        
        public static Map<String, Object> createMap() {
            return new HashMap<>();
        }
        
        public static void put(Map<String, Object> map, String key, Object value) {
            map.put(key, value);
        }
        
        public static Object get(Map<String, Object> map, String key) {
            return map.get(key);
        }
    }
    
    @Before
    public void setUp() {
        TestMap.setLocation();
    }
    
    @Test
    public void testBlankLayout() {
        // Test blank area definition
        assertEquals("Should have 12 blank areas", 12, TestMap.blankLayout.size());
        assertTrue("Should contain specific blank position", TestMap.blankLayout.contains(0));
        assertTrue("Should contain specific blank position", TestMap.blankLayout.contains(35));
    }
    
    @Test
    public void testCoordinatesMatcher() {
        // Test coordinate mapping
        int[] coords = TestMap.coordinatesMatcher.get(0);
        assertNotNull("Coordinates should not be null", coords);
        assertEquals("X coordinate should be correct", 0, coords[0]);
        assertEquals("Y coordinate should be correct", 2, coords[1]);
    }
    
    @Test
    public void testNumberMatcher() {
        // Test number mapping
        int[] coords = {0, 2};
        int number = TestMap.numberMatcher.get(Arrays.toString(coords));
        assertEquals("Should correctly map back to number", 0, number);
    }
    
    @Test
    public void testAdventurerMatcher() {
        // Test adventurer mapping
        String adventurer = TestMap.adventurerMatcher.get(0);
        assertEquals("Should correctly map adventurer name", "Diver", adventurer);
    }
    
    @Test
    public void testBidirectionalMapping() {
        // Test bidirectional mapping consistency
        for (int i = 0; i < 24; i++) {  // Test all valid positions
            int[] coords = TestMap.coordinatesMatcher.get(i);
            if (coords != null) {
                int mappedNumber = TestMap.numberMatcher.get(Arrays.toString(coords));
                assertEquals("Bidirectional mapping should be consistent", i, mappedNumber);
            }
        }
    }
    
    @Test
    public void testMapDimensions() {
        // Test map dimensions
        assertEquals("Map should have 6 rows", 6, TestMap.rows);
    }
    
    @Test
    public void testAdventurerMatcherCompleteness() {
        // Test adventurer mapping completeness
        String[] expectedAdventurers = {"Diver", "Engineer", "Explorer", "Messenger", "Navigator", "Pilot"};
        for (int i = 0; i < expectedAdventurers.length; i++) {
            assertEquals("Should contain all adventurers", expectedAdventurers[i], TestMap.adventurerMatcher.get(i));
        }
    }
    
    @Test
    public void testCreateMap() {
        Map<String, Object> map = TestMap.createMap();
        assertNotNull("Map should be created successfully", map);
        assertTrue("Map should be empty when created", map.isEmpty());
    }
    
    @Test
    public void testPutAndGet() {
        Map<String, Object> map = TestMap.createMap();
        String key = "test_key";
        String value = "test_value";
        
        TestMap.put(map, key, value);
        assertEquals("Value should be retrieved correctly", value, TestMap.get(map, key));
    }
    
    @Test
    public void testGetNonExistentKey() {
        Map<String, Object> map = TestMap.createMap();
        assertNull("Non-existent key should return null", TestMap.get(map, "non_existent_key"));
    }
    
    @Test
    public void testPutNull() {
        Map<String, Object> map = TestMap.createMap();
        String key = "null_key";
        
        TestMap.put(map, key, null);
        assertNull("Null value should be stored and retrieved correctly", TestMap.get(map, key));
        assertTrue("Map should contain key with null value", map.containsKey(key));
    }
} 