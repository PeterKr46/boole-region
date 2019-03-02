package eu.saltyscout.booleregion.region;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Peter on 17-Nov-16.
 */
public class IntersectionWrapper extends PairWrapper {

    @Override
    public String getOperation() {
        return "intersection";
    }

    public IntersectionWrapper(BooleRegion a, BooleRegion b) {
        super(a, b);

    }

    @Override
    public Vector3 getCenter() {
        return a.getCenter().add(b.getCenter()).divide(2);
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
    public boolean contains(BlockVector3 vector) {
        return a.contains(vector) && b.contains(vector);
    }

    @Override
    public Set<BlockVector2> getChunks() {
        Set<BlockVector2> chunks = b.getChunks();
        chunks.removeIf(chunk -> !a.getChunks().contains(chunk));
        return chunks;
    }

    @Override
    public Set<BlockVector3> getChunkCubes() {
        Set<BlockVector3> chunks = b.getChunkCubes();
        chunks.removeIf(chunk -> !a.getChunkCubes().contains(chunk));
        return chunks;
    }

    @Override
    public void setWorld(@Nullable World world) {

    }
    @Override
    public IntersectionWrapper clone() {
        return new IntersectionWrapper(a.clone(), b.clone());
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
            List<BlockVector3> a = Lists.newArrayList(IntersectionWrapper.super.a.iterator());
            @Override
            public boolean hasNext() {
                while (a.size() > 0 && !IntersectionWrapper.super.b.contains(a.get(0))) {
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
            /*List<BlockVector> a = Lists.newArrayList(IntersectionWrapper.super.a.iterator());
            List<BlockVector> b = Lists.newArrayList(IntersectionWrapper.super.b.iterator());

            @Override
            public boolean hasNext() {
                return b.size() > 0 && a.contains(b.get(0));
            }

            @Override
            public BlockVector next() {
                if (!hasNext()) return null;
                BlockVector v = b.remove(0);
                if (a.contains(v)) {
                    return v;
                }
                return next();
            }*/
        };
    }
}
