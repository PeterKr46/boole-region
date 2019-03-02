package eu.saltyscout.booleregion.region;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Peter on 17-Nov-16.
 */
public class DifferenceWrapper extends PairWrapper {

    @Override
    public String getOperation() {
        return "difference";
    }

    public DifferenceWrapper(BooleRegion a, BooleRegion b) {
        super(a, b);
    }

    @Override
    public int getArea() {
        /*int i = 0;
        Iterator<BlockVector> iter = iterator();
        while (iter.hasNext()) {
            i++;
        }
        return i;*/
        // For processing purposes:
        return getA().getArea() + getB().getArea();
    }

    @Override
    public boolean contains(BlockVector3 vector) {
        return a.contains(vector) && !b.contains(vector);
    }

    @Override
    public Set<BlockVector2> getChunks() {
        Set<BlockVector2> chunks = a.getChunks();
        b.getChunks().forEach(chunk -> chunks.removeIf(chunk::equals));
        return chunks;
    }

    @Override
    public Set<BlockVector3> getChunkCubes() {
        Set<BlockVector3> chunks = a.getChunkCubes();
        b.getChunkCubes().forEach(chunk -> chunks.removeIf(chunk::equals));
        return chunks;
    }

    @Override
    public void setWorld(@Nullable World world) {

    }

    @Override
    public DifferenceWrapper clone() {
        return new DifferenceWrapper(a.clone(), b.clone());
    }

    @Override
    public List<BlockVector2> polygonize(int i) {
        List<BlockVector2> a = this.a.polygonize(i);
        a.addAll(b.polygonize(i));
        return a;
    }

    @Override
    public Iterator<BlockVector3> iterator() {
        return new Iterator<BlockVector3>() {
            List<BlockVector3> a = Lists.newArrayList(DifferenceWrapper.super.a.iterator());
            @Override
            public boolean hasNext() {
                while (a.size() > 0 && DifferenceWrapper.super.b.contains(a.get(0))) {
                    a.remove(0);
                }
                return a.size() > 0;
            }

            @Override
            public BlockVector3 next() {
                if(!hasNext()) return null;
                return a.remove(0);
            }

            // OLD IMPLEMENTATION
            /* List<BlockVector> a = Lists.newArrayList(DifferenceWrapper.super.a.iterator());
            List<BlockVector> b = Lists.newArrayList(DifferenceWrapper.super.b.iterator());

            @Override
            public boolean hasNext() {
                while (a.size() > 0 && b.contains(a.get(0))) {
                    a.remove(0);
                }
                return a.size() > 0;
            }

            @Override
            public BlockVector next() {
                if (!hasNext()) return null;
                BlockVector v;
                v = a.remove(0);
                if (!b.contains(v)) {
                    return v;
                }
                return next();
            }*/
        };
    }
}
