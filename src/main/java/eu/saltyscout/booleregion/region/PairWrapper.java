package eu.saltyscout.booleregion.region;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.world.World;
import eu.saltyscout.booleregion.exception.BoolePairException;

import javax.annotation.Nullable;

/**
 * Created by Peter on 17-Nov-16.
 */
public abstract class PairWrapper extends BooleRegion {

    final BooleRegion a, b;

    public abstract String getOperation();

    PairWrapper(BooleRegion a, BooleRegion b) {
        if (a == null || b == null) {
            throw new BoolePairException("You can't just give me a non-existent region.. please..");
        }
        this.a = a.clone();
        this.b = b.clone();
        if (!a.getWorld().equals(b.getWorld())) {
            throw new BoolePairException("The given regions are not in the same world.. please..");
        }
    }


    @Override
    public BlockVector3 getMinimumPoint() {
        // Note: Ideally subtypes should override this behaviour if optimization is required.
        BlockVector3 a = this.a.getMinimumPoint();
        BlockVector3 b = this.b.getMinimumPoint();
        return BlockVector3.at(Math.min(a.getBlockX(), b.getBlockX()), Math.min(a.getBlockY(), b.getBlockY()), Math.min(a.getBlockZ(), b.getBlockZ()));
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        // Note: Ideally subtypes should override this behaviour if optimization is required.
        BlockVector3 a = this.a.getMaximumPoint();
        BlockVector3 b = this.b.getMaximumPoint();
        return BlockVector3.at(Math.max(a.getBlockX(), b.getBlockX()), Math.max(a.getBlockY(), b.getBlockY()), Math.max(a.getBlockZ(), b.getBlockZ()));
    }

    @Override
    public int getWidth() {
        return getMaximumPoint().getBlockX() - getMinimumPoint().getBlockX();
    }

    @Override
    public int getHeight() {
        return getMaximumPoint().getBlockY() - getMinimumPoint().getBlockY();
    }

    @Override
    public int getLength() {
        return getMaximumPoint().getBlockZ() - getMinimumPoint().getBlockZ();
    }

    @Override
    public void expand(BlockVector3... vectors) throws RegionOperationException {

    }

    @Override
    public void contract(BlockVector3... vectors) throws RegionOperationException {

    }

    @Override
    public void shift(BlockVector3 vector) throws RegionOperationException {

    }

    @Override
    public Vector3 getCenter() {
        return a.getCenter().add(b.getCenter()).divide(2);
    }
    
    @Override
    public String explain(char lastExplainChar) {
        char op;
        switch (getOperation()) {
            case "union": {
                op = '+';
                break;
            }
            case "difference": {
                op = '\\';
                break;
            }
            case "intersection": {
                op = '*';
                break;
            }
            default: {
                op = '?';
            }
        }
        return String.format("(%s%s%s)", a.explain(++lastExplainChar), op, b.explain(++lastExplainChar));
    }

    public BooleRegion getA() {
        return a;
    }

    public BooleRegion getB() {
        return b;
    }

    @Nullable
    @Override
    public final World getWorld() {
        return a.getWorld();
    }
}
