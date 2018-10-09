package laser;

/**
 * Simple implementation of {@link Laser} which logs operations to System.out.
 *
 * You should NOT modify this class.
 */
public class LaserImpl implements Laser {

    @Override
    public void beamOn() {
        System.out.println("Beam on");
    }

    @Override
    public void beamOff() {
        System.out.println("Beam off");
    }

    @Override
    public void moveTo(int x, int y) {
        System.out.println("Move to (" + x + ", " + y + ")");
    }
}
