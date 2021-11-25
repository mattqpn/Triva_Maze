package Model;

/**
 * Maze is the map for the game, it is what the player traverses to reach the goal.
 */
public class Maze {
    /**
     * Field containing the 2D array of Room Objects that represents the overall maze
     */
    private final Room[][] myMaze;
    /**
     * Field that holds the players X position
     */
    private int myX;
    /**
     * Field that holds the players Y position
     */
    private int myY;

    private final int mazeSize = 8;

    /**
     * Constructor that builds the 2D-array representation of the maze.
     * Additionally, initial coordinates of the player are set. Finally,
     * the roomSetup helper method is called which will set up the rooms with
     * doors.
     */
    public Maze() {
        myX = 0;
        myY = 0;
        myMaze = new Room[mazeSize][mazeSize];
        roomSetup();
    }

    /**
     * Helper method that assigns doors to the rooms depending on the rooms location
     */
    private void roomSetup() {

        for (int xMazeCoord = 0; xMazeCoord < myMaze.length; xMazeCoord++) {
            for (int yMazeCoord = 0; yMazeCoord < myMaze[xMazeCoord].length; yMazeCoord++) {
                //We put our directions in the innermost loop, so they reset for each room
                boolean west = false;
                boolean south = false;
                boolean east = false;
                boolean north = false;
                Door northDoor;
                Door southDoor = new Door();
                Door eastDoor = new Door();
                Door westDoor;
                //If column is greater than 0 we know there is a north facing door
                if (yMazeCoord > 0) {
                    north = true;
                    northDoor = myMaze[xMazeCoord][yMazeCoord - 1].getDoor(1);
                } else {
                    northDoor = new Door();
                }
                /* If column value is 1 less than the inner maze's length - 1 there is a
                   south facing door */
                if (yMazeCoord < myMaze[xMazeCoord].length - 1) {
                    south = true;
                    // Sets the south door of this room to the north door of the previous room.
                }
                //If room is not in a row on the southern edge, there is a east door
                if (xMazeCoord < myMaze.length - 1) {
                    east = true;
                }
                //If row value above is greater or equal to 0 we know exists west door
                if (xMazeCoord - 1 >= 0) {
                    west = true;
                    // Sets the west door of this room to the east door of the previous room.
                    westDoor = myMaze[xMazeCoord - 1][yMazeCoord].getDoor(2);
                } else {
                    westDoor = new Door();
                }
                myMaze[xMazeCoord][yMazeCoord] = new Room(new RoomBlocker(north, south, east, west), northDoor, southDoor, eastDoor, westDoor);
            }
        }
    }

    /**
     * Calculates the new player coordinates given a direction that the player moves
     *
     * @param theDirection The direction the player wants to move
     */
    public void movePlayer(Direction theDirection) {
        if (canMovePlayer(theDirection)) {
            switch (theDirection) {
                case NORTH -> myY--;
                case SOUTH -> myY++;
                case EAST -> myX++;
                case WEST -> myX--;
            }
        }
    }

    /**
     * Boolean value if the player can move in a direction
     *
     * @param theDirection The direction the player wants to move
     * @return Returns true if the player can move in that direction and false otherwise
     */
    public boolean canMovePlayer(Direction theDirection) {
        Door localDoor = getCurrentRoom().getDoor(theDirection);
        return localDoor != null && !localDoor.isDead();
    }

    /**
     * Getter for the player's X position
     *
     * @return myX the current X position
     */
    public int playerX() {
        return myX;
    }

    /**
     * Getter for the player's Y position
     *
     * @return myY the current Y position
     */
    public int playerY() {
        return myY;
    }

    /**
     * Getter for the maze size
     *
     * @return the maze width/height size
     */
    public int getMazeSize() {
        return mazeSize;
    }

    /**
     * Getter that returns the current room the player is in
     *
     * @return the room the player is currently located in
     */
    public Room getCurrentRoom() {
        return myMaze[myX][myY];
    }

    /**
     * Method to calculate if the end of the maze can be reached from
     * the current position
     *
     * @return true if possible, else false
     */
    public boolean isPossible() {
        //Clear visited field for all rooms
        for (int i = 0; i < myMaze.length; i++) {
            for (int j = 0; j < myMaze[i].length; j++) {
                myMaze[i][j].setVisited(false);
            }
        }
        //Utilize helper method
        return isPossibleHelper(myX, myY);
    }

    /**
     * Helper method to isPossible that utilizes 4 recursive calls to check for
     * valid adjacent moves. This method will detect if a path exits by returning
     * true when the destination is reached, else it will return false to signal game
     * has been lost.
     *
     * @param theX x coordinate of room
     * @param theY y coordinate of room
     * @return true if destination found, false otherwise
     */
    private boolean isPossibleHelper(int theX, int theY) {
        //Check to see if the current room is visited, if so return false
        if (!myMaze[theX][theY].getVisited()) {
            //The room corresponding to theX and theY is the destination room
            if (theX == mazeSize - 1 && theY == mazeSize - 1) {
                return true;
            }
            //Say that this room has now been visited
            myMaze[theX][theY].setVisited(true);
            //Check adjacent rooms recursively

            //Check North (assuming door exists and is not dead)
            if (myMaze[theX][theY].getDoor(0) != null &&
                    !myMaze[theX][theY].getDoor(0).isDead()) {
                boolean northCheck = isPossibleHelper(theX, theY - 1);
                if (northCheck) {
                    return true;
                }
            }
            //Check South (assuming door exists and is not dead)
            if (myMaze[theX][theY].getDoor(1) != null &&
                    !myMaze[theX][theY].getDoor(1).isDead()) {
                boolean southCheck = isPossibleHelper(theX, theY + 1);
                if (southCheck) {
                    return true;
                }
            }
            //Check East (assuming door exists and is not dead)
            if (myMaze[theX][theY].getDoor(2) != null &&
                    !myMaze[theX][theY].getDoor(2).isDead()) {
                boolean eastCheck = isPossibleHelper(theX + 1, theY);
                if (eastCheck) {
                    return true;
                }
            }
            //Check West (assuming door exists and is not dead)
            if (myMaze[theX][theY].getDoor(3) != null &&
                    !myMaze[theX][theY].getDoor(3).isDead()) {
                boolean westCheck = isPossibleHelper(theX - 1, theY);
                return westCheck;
            }
        }
        return false;
    }

    /**
     * Returns true when the player reaches the end of the maze
     *
     * @return Returns a boolean of if the player has reached the end of the maze.
     */
    public boolean goalReached() {
        return myX == mazeSize - 1 && myY == mazeSize - 1;
    }

    /**
     * toString displays the overview of the maze, including the player, the rooms,
     * the finish, and the start when the player is not in the start.
     *
     * @return string representing maze
     */
    @Override
    public String toString() {
        StringBuilder mazeString = new StringBuilder();
        for (int i = 0; i < myMaze.length; i++) {
            mazeString.append("\n");
            // Loop through all elements of current row
            for (int j = 0; j < myMaze[i].length; j++) {
                if (i == myY && j == myX) {
                    mazeString.append("[PLYR]");
                } else if (i == mazeSize - 1 && j == mazeSize - 1) {
                    mazeString.append("[FNSH]");
                } else if (i == 0 && j == 0) {
                    mazeString.append("[STRT]");
                } else {
                    mazeString.append("[ROOM]");

                }
            }
        }
        return mazeString.toString();
    }
}
