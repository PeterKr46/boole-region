package eu.saltyscout.booleregion.region;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Peter on 17-Nov-16.
 */
public class UnionWrapper extends PairWrapper {

    @Override
    public String getOperation() {
        return "union";
    }

    public UnionWrapper(BooleRegion a, BooleRegion b) {
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
    public boolean contains(BlockVector3 vector) {
        return a.contains(vector) || b.contains(vector);
    }

    @Override
    public Set<BlockVector2> getChunks() {
        Set<BlockVector2> chunks = a.getChunks();
        b.getChunks().forEach(chunk -> {
            if (!chunks.stream().anyMatch(chunk::equals)) {
                chunks.add(chunk);
            }
        });
        return chunks;
    }

    @Override
    public Set<BlockVector3> getChunkCubes() {
        Set<BlockVector3> chunks = a.getChunkCubes();
        b.getChunkCubes().forEach(chunk -> {
            if (chunks.stream().noneMatch(chunk::equals)) {
                chunks.add(chunk);
            }
        });
        return chunks;
    }


    @Override
    public void setWorld(@Nullable World world) {

    }

    @Override
    public UnionWrapper clone() {
        return new UnionWrapper(a.clone(), b.clone());
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
            List<BlockVector3> a = Lists.newArrayList(UnionWrapper.super.a.iterator());
            Iterator<BlockVector3> b = UnionWrapper.super.b.iterator();
            @Override
            public boolean hasNext() {
                while (a.size() > 0 && UnionWrapper.super.b.contains(a.get(0))) {
                    a.remove(0);
                }
                return a.size() > 0 || b.hasNext();
            }

            @Override
            public BlockVector3 next() {
                if(!hasNext()) return null;
                if(a.size() > 0) return a.remove(0);
                return b.next();
            }

            // OLD IMPLEMENTATION
            /*List<BlockVector> given = new ArrayList<>();
            Iterator<BlockVector> a = UnionWrapper.super.a.iterator();
            Iterator<BlockVector> b = UnionWrapper.super.b.iterator();

            @Override
            public boolean hasNext() {
                return a.hasNext() || b.hasNext();
            }

            @Override
            public BlockVector next() {
                BlockVector v = null;
                if (a.hasNext()) {
                    v = a.next();
                } else if (b.hasNext()) {
                    v = b.next();
                }
                if (v != null) {
                    if (given.contains(v)) {
                        return next();
                    }
                    given.add(v);
                }
                return v;
            }*/
        };
    }
}
