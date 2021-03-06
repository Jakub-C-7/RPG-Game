package lib;
import java.io.*;
import java.util.*;

/**
 * This class creates the instance for the player's adventure and fills it up with rooms 
 * that the player is free to explore.
 * @author Jakub
 *
 */
public final class Adventure {
	/**
	 * Fields
	 */
    private final Map<Integer, Map<Integer, Room>> map = new HashMap<Integer, Map<Integer, Room>>();
    private Room currentRoom;
    private int currentX = 0;
    private int currentY = 0;
    
    private Adventure() {
    }

    //Functions
    /**
     * Function for creating a new room and giving it x and y coordinates 
     * @param x
     * @param y
     * @param room
     */
    private void putRoom(int x, int y, Room room) {
        if (!map.containsKey(x)) {
            map.put(x, new HashMap<Integer, Room>());	
        }
        map.get(x).put(y, room);
    }

    /**
     * Function for returning current room coordinates
     * @param x
     * @param y
     * @return
     */
    private Room getRoom(int x, int y) {
        return map.get(x).get(y);
    }

    /**
     * Function for checking if a certain room exists via given coordinates
     * @param x
     * @param y
     * @return
     */
    private boolean roomExists(int x, int y) {
        if (!map.containsKey(x)) {
            return false;
        }
        return map.get(x).containsKey(y);
    }
    
    /**
     * Function returns true if boss room is completed 
     * @return
     */
    private boolean isComplete() {
        return currentRoom.isBossRoom() && currentRoom.isComplete();
    }
    /**
     * Function for moving the player north, east, south or west into a new room in the instance
     * @param player
     * @throws IOException
     */
    public void movePlayer(Player player) throws IOException {
    	//booleans check which directions have possible rooms to move to
        boolean northPossible = roomExists(currentX, currentY + 1);
        boolean southPossible = roomExists(currentX, currentY - 1);
        boolean eastPossible = roomExists(currentX + 1, currentY);
        boolean westPossible = roomExists(currentX - 1, currentY);
        System.out.print("Where would you like to go :");
        
        if (northPossible) {
            System.out.print(" North (n)");
        }
        if (eastPossible) {
            System.out.print(" East (e)");
        }
        if (southPossible) {
            System.out.print(" South (s)");
        }
        if (westPossible) {
            System.out.print(" West (w)");
        }
        System.out.print(" ? ");     
       
         //allows for user input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String direction = in.readLine();
        
        //moves the player after reading input
        if (direction.equals("n") && northPossible) {
            currentY++;
        } else if (direction.equals("s") && southPossible) {
            currentY--;
        } else if (direction.equals("e") && eastPossible) {
            currentX++;
        } else if (direction.equals("w") && westPossible) {
            currentX--;
        }
        currentRoom = getRoom(currentX, currentY);
        currentRoom.enter(player);
    }
    
    /**
     * Function for starting and ending the current quest
     * @param player
     * @throws IOException
     */
    public void currentQuest(Player player) throws IOException {
        while (player.isAlive() && !isComplete()) {
            movePlayer(player);
        }
        if (player.isAlive()) {
            System.out.println("CROWN");
        }else {
            System.out.println("YOU HAVE PERISHED");
        }
    }
    /**
     * Creates a new adventure instance and initialises it with rooms as well as the boss room
     * @return
     */
    public static Adventure newInstance() {
        Adventure adventure = new Adventure();
        adventure.putRoom(0, 0, Room.newRegularInstance());
        adventure.putRoom(-1, 1, Room.newRegularInstance());
        adventure.putRoom(0, 1, Room.newRegularInstance());
        adventure.putRoom(1, 1, Room.newRegularInstance());
        adventure.putRoom(-1, 2, Room.newRegularInstance());
        adventure.putRoom(1, 2, Room.newRegularInstance());
        adventure.putRoom(-1, 3, Room.newRegularInstance());
        adventure.putRoom(0, 3, Room.newRegularInstance());
        adventure.putRoom(1, 3, Room.newRegularInstance());
        adventure.putRoom(0, 4, Room.newBossInstance());
        adventure.currentRoom = adventure.getRoom(0, 0);
        return adventure;
    }

}
