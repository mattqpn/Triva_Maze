package Model;

/**
 * Rooms contain Doors in every compass direction that the player has to open to move through
 */
public class Room {
    private final Door[] myRoomDoors;
    private final RoomBlocker myBlockedDoors;
    private boolean myVisited = false;
    private static final int DOOR1_INDEX = 0;
    private static final int DOOR2_INDEX = 1;
    private static final int DOOR3_INDEX = 2;
    private static final int DOOR4_INDEX = 3;
    private static final int TOTAL_DOORS = 4;

    /**
     * Creates a new Room
     *
     * @param theBlocker   RoomBlocker of un-openable Doors
     * @param theNorthDoor North Shared or New Door
     * @param theSouthDoor South Shared or New Door
     * @param theEastDoor  East Shared or New Door
     * @param theWestDoor  West Shared or New Door
     */
    public Room(final RoomBlocker theBlocker, Door theNorthDoor, Door theSouthDoor, Door theEastDoor, Door theWestDoor) {
        this.myBlockedDoors = theBlocker;
        this.myRoomDoors = new Door[TOTAL_DOORS];
        if (theBlocker.getNorth()) {
            this.myRoomDoors[DOOR1_INDEX] = theNorthDoor;
        }
        if (theBlocker.getSouth()) {
            this.myRoomDoors[DOOR2_INDEX] = theSouthDoor;
        }
        if (theBlocker.getEast()) {
            this.myRoomDoors[DOOR3_INDEX] = theEastDoor;
        }
        if (theBlocker.getWest()) {
            this.myRoomDoors[DOOR4_INDEX] = theWestDoor;
        }
    }

    /**
     * Setter to change the visited status of room
     *
     * @param theVisited true if visited, false if else
     */
    public void setVisited(boolean theVisited) {
        this.myVisited = theVisited;
    }

    /**
     * Getter to get the visited status of room
     *
     * @return myVisited, the field that holds whether room has been visited
     */
    public boolean getVisited() {
        return myVisited;
    }

    /**
     * Returns which doors are permanently blocked
     *
     * @return Returns a RoomBlocker
     */
    public RoomBlocker getBlockedDoors() {
        return myBlockedDoors;
    }

    /**
     * @param theDoorIndex The index of the door
     * @return Returns a door based on the index
     */
    public Door getDoor(final int theDoorIndex) {
        return myRoomDoors[theDoorIndex];
    }

    public Door getDoor(final Direction doorDirection) {
        Door localDoor = null;
        switch (doorDirection) {
            case NORTH -> localDoor = getDoor(DOOR1_INDEX);
            case SOUTH -> localDoor = getDoor(DOOR2_INDEX);
            case EAST -> localDoor = getDoor(DOOR3_INDEX);
            case WEST -> localDoor = getDoor(DOOR4_INDEX);
        }
        return localDoor;
    }

    /**
     * toString method for the room, includes whether each direction is blocked
     * and shows the player in the center of each room
     *
     * @return string representing the room
     */
    @Override
    public String toString() {
        //StringBuilder to build our output
        StringBuilder roomString = new StringBuilder();
        //Center the North movement message
        roomString.append("\n\t\t\t");
        //Check movement North
        if (myRoomDoors[0] == null) {
            roomString.append("BLOCKED");
        } else if (myRoomDoors[0].isDead()) {
            roomString.append("DEAD DOOR");
        } else {
            roomString.append("MOVE NORTH");
        }
        //Go to next line for West,Player, and East
        roomString.append("\n\n");
        //Check movement West
        if (myRoomDoors[3] == null) {
            roomString.append("BLOCKED");
        } else if (myRoomDoors[3].isDead()) {
            roomString.append("DEAD DOOR");
        } else {
            roomString.append("MOVE WEST");
        }
        //Center the placement of player
        roomString.append("\t\tPlayer\t\t");
        //Check movement East
        if (myRoomDoors[2] == null) {
            roomString.append("BLOCKED");
        } else if (myRoomDoors[2].isDead()) {
            roomString.append("DEAD DOOR");
        } else {
            roomString.append("MOVE EAST");
        }
        //Go to next line
        roomString.append("\n\n\t\t\t");
        //Check movement South
        if (myRoomDoors[1] == null) {
            roomString.append("BLOCKED");
        } else if (myRoomDoors[1].isDead()) {
            roomString.append("DEAD DOOR");
        } else {
            roomString.append("MOVE SOUTH");
        }
        return roomString.toString();
    }

}
