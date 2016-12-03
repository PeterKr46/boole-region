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
public class IntersectionWrapper extends PairWrapper {

    @Override
    public String getOperation() {
        return "intersection";
    }

    public IntersectionWrapper(BooleRegion a, BooleRegion b) {
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
        return a.contains(vector) && b.contains(vector);
    }

    @Override
    public Set<Vector2D> getChunks() {
        Set<Vector2D> chunks = b.getChunks();
        chunks.removeIf(chunk -> !a.getChunks().contains(chunk));
        return chunks;
    }

    @Override
    public Set<Vector> getChunkCubes() {
        Set<Vector> chunks = b.getChunkCubes();
        chunks.removeIf(chunk -> !a.getChunkCubes().contains(chunk));
        return chunks;
    }

    @Override
    public void setWorld(@Nullable World world) {

    }

    @Override
    @Deprecated
    public void setWorld(@Nullable LocalWorld localWorld) {

    }

    @Override
    public IntersectionWrapper clone() {
        return new IntersectionWrapper(a.clone(), b.clone());
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
            List<BlockVector> a = Lists.newArrayList(IntersectionWrapper.super.a.iterator());
            @Override
            public boolean hasNext() {
                while (a.size() > 0 && !IntersectionWrapper.super.b.contains(a.get(0))) {
                    a.remove(0);
                }
                return a.size() > 0;
            }

            @Override
            public BlockVector next() {
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
