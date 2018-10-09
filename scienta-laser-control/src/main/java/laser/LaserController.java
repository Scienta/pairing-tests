package laser;

/**
 * This class is responsible for controlling a {@link Laser}.
 * <p/>
 * You should modify this class to accommodate for the required changes.
 */
public class LaserController {

    private final Laser laser;

    /**
     * Creates a new controller for the given laser.
     *
     * @param laser The laser to control.
     */
    public LaserController(Laser laser) {
        this.laser = laser;
    }

    /**
     * Moves the laser to the given position with the beam off.
     *
     * @param x The absolute X coordinate.
     * @param y The absolute Y coordinate.
     */
    public void moveTo(int x, int y) {
        laser.moveTo(x, y);
    }

    /**
     * Draws a line by moving the laser with the beam on from the current position
     * to the given position.
     *
     * @param x The absolute X coordinate.
     * @param y The absolute Y coordinate.
     */
    public void lineTo(int x, int y) {
        laser.beamOn();
        laser.moveTo(x, y);
        laser.beamOff();
    }

    /**
     * Resets the laser by turning the beam off and moving it to position (0, 0).
     */
    public void reset() {
        laser.moveTo(0, 0);
    }
}
