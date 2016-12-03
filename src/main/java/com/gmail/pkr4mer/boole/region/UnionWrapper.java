package com.gmail.pkr4mer.boole.region;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.regions.RegionOperationException;
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
    public Vector getMinimumPoint() {
        Vector a = this.a.getMinimumPoint();
        Vector b = this.b.getMinimumPoint();
        return new Vector(Math.min(a.getBlockX(), b.getBlockX()), Math.min(a.getBlockY(), b.getBlockY()), Math.min(a.getBlockZ(), b.getBlockZ()));
    }

    @Override
    public Vector getMaximumPoint() {
        Vector a = this.a.getMaximumPoint();
        Vector b = this.b.getMaximumPoint();
        return new Vector(Math.max(a.getBlockX(), b.getBlockX()), Math.max(a.getBlockY(), b.getBlockY()), Math.max(a.getBlockZ(), b.getBlockZ()));
    }

    @Override
    public Vector getCenter() {
        return a.getCenter().add(b.getCenter()).divide(2);
    }

    @Override
    public int getArea() {
        int i = 0;
        Iterator<BlockVector> iter = iterator();
        while (iter.hasNext()) {
            i++;
        }
        return i;
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
    public void expand(Vector... vectors) throws RegionOperationException {

    }

    @Override
    public void contract(Vector... vectors) throws RegionOperationException {

    }

    @Override
    public void shift(Vector vector) throws RegionOperationException {

    }

    @Override
    public boolean contains(Vector vector) {
        return a.contains(vector) || b.contains(vector);
    }

    @Override
    public Set<Vector2D> getChunks() {
        Set<Vector2D> chunks = a.getChunks();
        b.getChunks().forEach(chunk -> {
            if (!chunks.stream().anyMatch(chunk::equals)) {
                chunks.add(chunk);
            }
        });
        return chunks;
    }

    @Override
    public Set<Vector> getChunkCubes() {
        Set<Vector> chunks = a.getChunkCubes();
        b.getChunkCubes().forEach(chunk -> {
            if (!chunks.stream().anyMatch(chunk::equals)) {
                chunks.add(chunk);
            }
        });
        return chunks;
    }


    @Override
    public void setWorld(@Nullable World world) {

    }

    @Override
    public void setWorld(@Nullable LocalWorld localWorld) {

    }

    @Override
    public UnionWrapper clone() {
        return new UnionWrapper(a.clone(), b.clone());
    }

    @Override
    public List<BlockVector2D> polygonize(int i) {
        List<BlockVector2D> a = this.a.polygonize(i);
        a.addAll(b.polygonize(i));
        return a;
    }

    @Override
    public Iterator<BlockVector> iterator() {
        return new Iterator<BlockVector>() {
            List<BlockVector> a = Lists.newArrayList(UnionWrapper.super.a.iterator());
            Iterator<BlockVector> b = UnionWrapper.super.b.iterator();
            @Override
            public boolean hasNext() {
                while (a.size() > 0 && UnionWrapper.super.b.contains(a.get(0))) {
                    a.remove(0);
                }
                return a.size() > 0 || b.hasNext();
            }

            @Override
            public BlockVector next() {
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
