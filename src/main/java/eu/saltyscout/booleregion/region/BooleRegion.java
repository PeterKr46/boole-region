package eu.saltyscout.booleregion.region;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;

/**
 * Created by Peter on 17-Nov-16.
 */
public abstract class BooleRegion implements Region {
    @Override
    public abstract boolean contains(BlockVector3 vector);

    @Override
    public abstract BooleRegion clone();

    public boolean isRegionWrapper() {
        return false;
    }
    
    public abstract String explain(char lastExplainChar);

    public final BooleRegion union(BooleRegion b) {
        return new UnionWrapper(this, b);
    }

    public final BooleRegion intersection(BooleRegion b) {
        return new IntersectionWrapper(this, b);
    }

    public final BooleRegion difference(BooleRegion b) {
        return new DifferenceWrapper(this, b);
    }
}
