# Laser Control

ACME Laser is a company delivering industrial laser systems used for engraving all kinds of products, from
engine blocks to sun glasses.

You have been hired by ACME Laser to optimize the software which controls the laser beam. The class LaserController
has methods for moving the beam to a given position, moveTo(x, y), and for drawing a line between the current position
and a given position, lineTo(x, y).

The problem with the current software is that consecutive lineTo() operations causes the beam to turn off and on.
This is unnecessary, and reduces the lifetime of the laser. The output from running the Main class illustrates this problem:

Move to (10, 10)
Beam on
Move to (20, 10)
Beam off
Beam on
Move to (20, 20)
Beam off
Beam on
Move to (10, 20)
Beam off
Beam on
Move to (10, 10)
Beam off
Move to (15, 15)
Beam on
Move to (20, 20)
Beam off
Move to (0, 0)

Your job is to modify the LaserController class in such a way that consecutive lineTo() operations don't cause the laser to be
turned off and on. The output from a correctly optimized version of the program is given below:

Move to (10, 10)
Beam on
Move to (20, 10)
Move to (20, 20)
Move to (10, 20)
Move to (10, 10)
Beam off
Move to (15, 15)
Beam on
Move to (20, 20)
Beam off
Move to (0, 0)

Finally, note that you are only allowed to modify the LaserController class.
