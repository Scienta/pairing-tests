package laser;

/**
 * Simple implementation of {@link Laser} which logs operations to System.out.
 *
 * You should NOT modify this class.
 */
public class LaserImpl implements Laser {

    /**
     * {@inheritDoc}
     */
    public void beamOn() {
        System.out.println("Beam on");
    }

    /**
     * {@inheritDoc}
     */
    public void beamOff() {
        System.out.println("Beam off");
    }

    /**
     * {@inheritDoc}
     */
    public void moveTo(int x, int y) {
        System.out.println("Move to (" + x + ", " + y + ")");
    }
}
