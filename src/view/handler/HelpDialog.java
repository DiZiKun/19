package view.handler;

import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {
    public HelpDialog() {
        setTitle("Game Help");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JEditorPane helpPane = new JEditorPane();
        helpPane.setContentType("text/html");
        helpPane.setEditable(false);
        helpPane.setText(getHtmlHelpContent()); // 使用 HTML 说明文档

        JScrollPane scrollPane = new JScrollPane(helpPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
        setVisible(true);
    }

    private String getHtmlHelpContent() {
        return """
                <html>
                       <head>
                         <style>
                           body { font-family: 'Segoe UI', sans-serif; padding: 20px; line-height: 1.6; }
                           h1, h2 { color: #2c3e50; }
                           h2 { margin-top: 24px; }
                           ul, ol { margin-left: 20px; }
                           li { margin-bottom: 6px; }
                           .role { font-weight: bold; color: #2980b9; }
                           .color { font-weight: bold; }
                           code { background-color: #f4f4f4; padding: 2px 4px; border-radius: 3px; }
                         </style>
                       </head>
                       <body>
                       <h1>🧭 Forbidden Island Game Manual (English Version)</h1>
                       <p>Welcome to the world of <b>Forbidden Island</b>! In this cooperative adventure game, you and your teammates must work together to collect four mystical treasures and escape the island before it sinks!</p>
                       <p>This guide will walk you through the gameplay, button functions, turn order, and role abilities — everything you need to get started.</p>
                
                       <h2>🧱 Objective</h2>
                       <ul>
                         <li>Collect the 4 treasures: 🌊 Ocean’s Chalice, 🔥 Crystal of Fire, 🌪️ Statue of the Wind, 🪨 Earth Stone</li>
                         <li>Gather all players on <b>Fools’ Landing</b> and use a Helicopter Lift card to escape</li>
                         <li>Avoid sinking key tiles or losing team members — or you’ll lose the game!</li>
                       </ul>
                
                       <h2>🎮 Game Interface Overview</h2>
                       <p><b>Main Components:</b></p>
                       <ul>
                         <li><b>Center Area:</b> The game board (24 tiles with player pawns and flooding state)</li>
                         <li><b>Left Panel:</b> Treasure progress display</li>
                         <li><b>Right Panel:</b> Flood deck and discard pile</li>
                         <li><b>Top/Bottom Panels:</b> Players’ hands and pawn icons</li>
                         <li><b>Bottom Bar:</b> Operation buttons (up to 3 actions per turn)</li>
                       </ul>
                
                       <h2>🎮 Turn Overview – What You Do on Your Turn</h2>
                       <ol>
                         <li>✅ Perform up to <b>3 actions</b></li>
                         <li>✅ Automatically draw <b>2 Treasure Cards</b></li>
                         <li>✅ Automatically draw <b>Flood Cards</b></li>
                       </ol>
                       <p><b>💡 Tip:</b> The player whose portrait is highlighted is the one whose turn it is.</p>
                
                       <h2>🔘 Operation Button Functions</h2>
                       <ul>
                         <li><b>Move:</b> Move to an adjacent tile (up/down/left/right; some roles allow diagonal or flying)</li>
                         <li><b>Shore:</b> Shore up your tile or adjacent flooded tile</li>
                         <li><b>Pass:</b> Give a card to a teammate (Messenger can pass anywhere)</li>
                         <li><b>Capture:</b> Collect a treasure when on its tile with 4 matching cards</li>
                         <li><b>Lift Off:</b> Escape the island from Fools’ Landing with all players & 4 treasures</li>
                         <li><b>Special Actions:</b> Use special cards (🛩️ Helicopter Lift, 🧱 Sandbags)</li>
                         <li><b>Next:</b> End your turn and proceed to the next player</li>
                         <li><b>Discard:</b> Discard cards when you exceed 5 in hand</li>
                         <li><b>Reset:</b> Reset current selections or actions</li>
                       </ul>
                
                       <h2>🕹️ Standard Turn Walkthrough</h2>
                       <ol>
                         <li>Identify your role and abilities (e.g., Navigator can move teammates; Engineer can double shore)</li>
                         <li>Use up to 3 actions:</li>
                         <ul>
                           <li>Click <code>Move</code>, then select destination tile</li>
                           <li>Click <code>Shore</code>, then select tile to shore</li>
                           <li>Click <code>Pass</code>, then click card and teammate</li>
                           <li>Click <code>Capture</code> when at a treasure tile with 4 matching cards</li>
                         </ul>
                         <li>Click <code>Next</code> to end your turn</li>
                         <li>System will:
                           <ul>
                             <li>Draw 2 Treasure Cards</li>
                             <li>Draw Flood Cards</li>
                             <li>Update map status</li>
                           </ul>
                         </li>
                         <li>Next player begins</li>
                       </ol>
                
                       <h2>👥 Player Roles (Abilities + Pawn Colors)</h2>
                       <p>Each player is randomly assigned a role, each with a unique ability and distinct pawn color:</p>
                       <ul>
                         <li>⚫ (Black)<b>Diver:</b> Can cross any number of adjacent sunken or missing tiles</li>
                         <li>🔴 (Red)<b>Engineer:</b> Can shore up <b>2</b> tiles in one action</li>
                         <li>🟢 (Green)<b>Explorer:</b> Can move/shore diagonally</li>
                         <li>⚪ (White)<b>Messenger:</b> Can pass cards to <b>any player</b>, not just on same tile</li>
                         <li>🟡 (Yellow)<b>Navigator:</b> Can move teammates up to 2 spaces on their turn</li>
                         <li>🔵 (Blue)<b>Pilot:</b> Once per turn, can fly to <b>any tile</b> on the board</li>
                       </ul>
                       <p><b>🎯 Tip:</b> Mastering role cooperation is the key to victory!</p>
                
                       <h2>📦 Treasure Collection</h2>
                       <p>Each treasure has 2 specific collection tiles. A player must:</p>
                       <ul>
                         <li>Be on one of those tiles</li>
                         <li>Hold 4 matching treasure cards</li>
                         <li>Click <b>Capture</b> to collect</li>
                       </ul>
                
                       <h2>☠️ Game Over Conditions (Any of the following)</h2>
                       <ul>
                         <li>Both tiles for a single treasure sink before it’s collected</li>
                         <li><b>Fools’ Landing</b> tile sinks</li>
                         <li>A player is trapped on a tile with no escape</li>
                         <li>Water level reaches the skull mark</li>
                       </ul>
                
                       <h2>🧠 Tips for New Players</h2>
                       <ul>
                         <li>Frequently shore up <b>Fools’ Landing</b> and treasure tiles!</li>
                         <li>Don’t hold too many cards — hand limit is 5</li>
                         <li>Use 🛩️ Helicopter and 🧱 Sandbags wisely</li>
                         <li>Cooperate effectively — pass cards, shore together, delegate</li>
                         <li><b>🔔 Whoever’s avatar is glowing — it’s their turn!</b></li>
                       </ul>
                
                       <h2>🧾 Game Setup</h2>
                       <ol>
                         <li>Click <b>Start Game</b> on the Welcome screen</li>
                         <li>Choose number of players (2–4) and difficulty</li>
                         <li>System deals cards, assigns roles, and places tiles</li>
                         <li>First player begins with 3 actions</li>
                       </ol>
                
                       <h2>🏁 Winning Conditions</h2>
                       <ul>
                         <li>All players reach <b>Fools’ Landing</b></li>
                         <li>All 4 treasures are collected</li>
                         <li>At least one player uses a Helicopter Lift to escape</li>
                       </ul>
                       <p>🎉 Congratulations, you win!</p>
                       </body>
                       </html>
""";
    }
}
