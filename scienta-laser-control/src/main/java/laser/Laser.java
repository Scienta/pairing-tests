package laser;

/**
 * Defines operations of the laser.
 * <p/>
 * You should NOT modify this interface.
 */
public interface Laser {

    /**
     * Turns the laser beam on.
     */
    void beamOn();

    /**
     * Turns the laser beam off.
     */
    void beamOff();

    /**
     * Moves the laser to the given position. The beam might be on or off
     * while moving.
     *
     * @param x The absolute X coordinate.
     * @param y The absolute Y coordinate.
     */
    void moveTo(int x, int y);

}
