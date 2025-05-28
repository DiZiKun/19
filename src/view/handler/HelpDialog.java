package view.handler;

import javax.swing.*;
import java.awt.*;

/**
 * Help dialog class that displays game instructions and rules.
 * Provides a comprehensive HTML-formatted guide including:
 * - Game objectives and rules
 * - Interface overview
 * - Turn structure
 * - Button functions
 * - Player roles and abilities
 * - Winning and losing conditions
 */
public class HelpDialog extends JDialog {
    /**
     * Constructor initializes and configures the help dialog window.
     * Sets up a scrollable text area with HTML-formatted game instructions.
     * Dialog is modal and centered on screen.
     */
    public HelpDialog() {
        setTitle("Game Help");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JEditorPane helpPane = new JEditorPane();
        helpPane.setContentType("text/html");
        helpPane.setEditable(false);
        helpPane.setText(getHtmlHelpContent()); // Use HTML documentation

        JScrollPane scrollPane = new JScrollPane(helpPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        setVisible(true);
    }

    /**
     * Returns the HTML-formatted help content as a string.
     * Contains detailed game instructions including:
     * - Game objective and overview
     * - Interface components
     * - Turn structure and actions
     * - Button functions
     * - Role descriptions and abilities
     * - Treasure collection mechanics
     * - Win/loss conditions
     * - Tips for new players
     * 
     * @return HTML-formatted string containing complete game documentation
     */
    private String getHtmlHelpContent() {
        return """
                <html>
                <head>
                                                <meta charset="UTF-8" />
                                                <style>
                                                  body { font-family: 'Segoe UI', sans-serif; padding: 20px; line-height: 1.6; background: #fdfdfd; color: #2c3e50; }
                                                  h1, h2, h3 { color: #2c3e50; }
                                                  ul, ol { margin-left: 20px; }
                                                  li { margin-bottom: 8px; }
                                                  code { background: #f4f4f4; padding: 2px 4px; border-radius: 4px; }
                                                  .role { font-weight: bold; color: #2980b9; }
                                                  .important { background: #fff9c4; padding: 2px 6px; border-radius: 4px; }
                                                </style>
                                                <title>Forbidden Island Help (EN)</title>
                                              </head>
                                              <body>
                                              <h1>🌊 Forbidden Island Game Help</h1>
                
                                              <p>Welcome to <strong>Forbidden Island</strong>, a strategic cooperative game where you and your team must collect the four sacred treasures and escape before the island sinks!</p>
                
                                              <h2>📌 Objective</h2>
                                              <ul>
                                                <li>Collect the 4 treasures: Ocean’s Chalice, Crystal of Fire, Statue of the Wind, Earth Stone</li>
                                                <li>Gather all players at <strong>Fools’ Landing</strong> and use a <em>Helicopter Lift</em> card to escape</li>
                                                <li>Avoid losing key tiles or team members — or the game ends in failure!</li>
                                              </ul>
                
                                              <h2>🧩 Game Setup</h2>
                                              <ol>
                                                <li>Choose number of players (2–4) and difficulty (Novice, Normal, Elite, Legendary)</li>
                                                <li>System shuffles and reveals 6 initial flood cards — flip matching tiles to flooded</li>
                                                <li>Each player is dealt a role card and placed on their starting tile</li>
                                                <li>Each player draws 2 treasure cards (replace Water Rise! if drawn initially)</li>
                                                <li>Set the water meter to chosen difficulty</li>
                                              </ol>
                
                                              <h2>🕹️ Player Turn Structure</h2>
                                              <ol>
                                                <li><strong>Actions</strong>: Take up to 3 actions (Move, Shore, Pass, Capture, Special)</li>
                                                <li><strong>Treasure Cards</strong>: Draw 2 from treasure deck</li>
                                                <li><strong>Flood Cards</strong>: Draw based on current water level</li>
                                              </ol>
                
                                              <h2>🎮 Action Buttons</h2>
                                              <ul>
                                                <li><code>Move</code>: Move to adjacent tile (Explorer can move diagonally, Pilot can fly)</li>
                                                <li><code>Shore</code>: Flip a flooded tile back (Engineer shores 2; Explorer can shore diagonally)</li>
                                                <li><code>Pass</code>: Give cards to a player on the same tile (Messenger can pass to anyone)</li>
                                                <li><code>Capture</code>: Exchange 4 matching cards at a treasure tile to capture treasure</li>
                                                <li><code>Special</code>: Use special cards (Helicopter Lift, Sandbags)</li>
                                                <li><code>Lift Off</code>: Escape from Fools’ Landing if all players and treasures are ready</li>
                                                <li><code>Next</code>: End turn and switch to next player</li>
                                                <li><code>Discard</code>: Drop cards if over the 5-card hand limit</li>
                                                <li><code>Reset</code>: Cancel current selections and reset the turn</li>
                                              </ul>
                
                                              <h2>🧙‍♂️ Role Abilities</h2>
                                              <ul>
                                                <li><span class="role">Engineer (Red)</span>: Shore 2 adjacent flooded tiles in one action</li>
                                                <li><span class="role">Messenger (White)</span>: Pass any card to any player from any location</li>
                                                <li><span class="role">Pilot (Blue)</span>: Once per turn, fly to any tile</li>
                                                <li><span class="role">Diver (Black)</span>: Move through adjacent flooded or sunken tiles</li>
                                                <li><span class="role">Explorer (Green)</span>: Move and shore diagonally</li>
                                                <li><span class="role">Navigator (Yellow)</span>: Move another player up to 2 tiles on your turn</li>
                                              </ul>
                
                                              <h2>🎴 Special Cards</h2>
                                              <ul>
                                                <li><strong>Helicopter Lift</strong>: Move players on the same tile to any tile or escape from Fools’ Landing</li>
                                                <li><strong>Sandbags</strong>: Shore any tile at any time (cannot recover sunken tiles)</li>
                                                <li><strong>Water Rise!</strong>: Raise water level, reshuffle flood discard pile to top</li>
                                              </ul>
                
                                              <h2>📦 Treasure Collection</h2>
                                              <p>Collect a treasure by:</p>
                                              <ul>
                                                <li>Moving to one of the two corresponding tiles</li>
                                                <li>Having 4 matching treasure cards</li>
                                                <li>Clicking <code>Capture</code> to collect</li>
                                              </ul>
                
                                              <h2>🚨 Game Over Conditions</h2>
                                              <ul>
                                                <li>Both tiles of a treasure sink before it's collected</li>
                                                <li>Fools’ Landing sinks</li>
                                                <li>A player is on a tile that sinks with no adjacent tile to swim to</li>
                                                <li>Water level reaches the skull icon</li>
                                              </ul>
                
                                              <h2>🧠 Gameplay Tips</h2>
                                              <ul>
                                                <li>Keep <strong>Fools’ Landing</strong> and treasure tiles shored!</li>
                                                <li>Use <strong>Helicopter</strong> and <strong>Sandbags</strong> cards at the right moments</li>
                                                <li>Watch your hand size — you can only hold 5 cards</li>
                                                <li>Coordinate with teammates — this is a cooperative game!</li>
                                              </ul>
                
                                              <h2>🏁 Winning Conditions</h2>
                                              <ul>
                                                <li>All 4 treasures are captured</li>
                                                <li>All players are on Fools’ Landing</li>
                                                <li>Helicopter Lift card is used to escape</li>
                                              </ul>
                
                                              <p><strong>🌟 Victory awaits — teamwork is your key to survival!</strong></p>
                                              </body>
                       </html>
""";
    }
}
