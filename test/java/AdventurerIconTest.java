import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;

import com.forbidden.island.view.AdventurerIcon;

/**
 * Test class for AdventurerIcon
 */
public class AdventurerIconTest {
    
    private Icon topIcon;
    private Icon bottomIcon;
    private AdventurerIcon adventurerIcon;
    private AdventurerIcon scaledAdventurerIcon;
    
    @Before
    public void setUp() {
        // Create mock icons
        topIcon = createMockIcon(20, 20);
        bottomIcon = createMockIcon(30, 30);
        
        // Create AdventurerIcon instances
        adventurerIcon = new AdventurerIcon(topIcon, bottomIcon);
        scaledAdventurerIcon = new AdventurerIcon(topIcon, bottomIcon, 2);
    }
    
    @Test
    public void testIconDimensions() {
        // Test icon height (should be max of top and bottom)
        assertEquals("Icon height should be max of top and bottom icons",
                    30, adventurerIcon.getIconHeight());
        
        // Test icon width (should be max of top and bottom)
        assertEquals("Icon width should be max of top and bottom icons",
                    30, adventurerIcon.getIconWidth());
    }
    
    @Test
    public void testScaledIconDimensions() {
        // Dimensions should be the same regardless of scaling
        assertEquals("Scaled icon height should be max of top and bottom icons",
                    30, scaledAdventurerIcon.getIconHeight());
        assertEquals("Scaled icon width should be max of top and bottom icons",
                    30, scaledAdventurerIcon.getIconWidth());
    }
    


    // Helper method to create mock icons
    private Icon createMockIcon(final int width, final int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                // Mock implementation
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }


}
