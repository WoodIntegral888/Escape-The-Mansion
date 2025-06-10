/* Manoush and Vishwa
 * 9/6/2025
 * Abstract class - Room
 */

/**
 * The abstract class Room represents a generic blueprint for all rooms
 * within an escaperoom. It defines methods that all specific room types 
 * (e.g., Reception, Lounge) must implement.
 */
abstract class Room {
     /**
     * This abstract method must be implemented by all subclasses.
     * It will be used to start up the room (guides the story)
     */
    abstract void enterRoom(); 
    
    /**
     * This abstract method must be implemented by all subclasses.
     * It will be used to inform user what needs to happen in said room
     */
    abstract void showInstructions(); 
} //Vishwa
