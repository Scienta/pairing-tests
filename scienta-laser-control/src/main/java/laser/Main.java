package laser;

public class Main {

    public static void main(String[] args) {

        LaserController controller = new LaserController(new LaserImpl());

        controller.moveTo(10, 10);
        controller.lineTo(20, 10);
        controller.lineTo(20, 20);
        controller.lineTo(10, 20);
        controller.lineTo(10, 10);

        controller.moveTo(15, 15);
        controller.lineTo(20, 20);

        controller.reset();

    }
}
