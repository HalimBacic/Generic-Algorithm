package genericalgorithm;

/**
 * Interface which provide methods for simulation.
 * Function which convert double value of point in binary represetation and vice versa
 */
public interface BinaryRepresentation {
    String toBinary(double d, int precision);

    String wholeToBinary(long l);

    String fractionalToBinary(double num, int precision);

    Double binaryToDouble(String str);
}
