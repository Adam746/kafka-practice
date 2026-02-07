package joiners;

import org.apache.kafka.streams.kstream.ValueJoiner;
import classes.LeftClass;
import classes.RightClass;

public class JoinerTemplate implements ValueJoiner<LeftClass, RightClass, LeftClass> {
    public LeftClass apply(LeftClass left, RightClass right) {
        // Add properties from rightclass
        return new LeftClass();
    }
}
