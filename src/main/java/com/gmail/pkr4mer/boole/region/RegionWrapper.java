package com.gmail.pkr4mer.boole.region;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.world.World;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Peter on 17-Nov-16.
 */
public class RegionWrapper extends BooleRegion {

    private final Region region;

    @Override
    public boolean isRegionWrapper() {
        return true;
    }
    
    @Override
    public String explain(char lastExplainChar) {
        return String.valueOf(lastExplainChar);
    }
    
    public Region getRegion() {
        return region.clone();
    }

    public RegionWrapper(Region region) {
        this.region = region.clone();
    }

    @Override
    public Vector getMinimumPoint() {
        return region.getMinimumPoint();
    }

    @Override
    public Vector getMaximumPoint() {
        return region.getMaximumPoint();
    }

    @Override
    public Vector getCenter() {
        return region.getCenter();
    }

    @Override
    public int getArea() {
        return region.getArea();
    }

    @Override
    public int getWidth() {
        return region.getWidth();
    }

    @Override
    public int getHeight() {
        return region.getHeight();
    }

    @Override
    public int getLength() {
        return region.getLength();
    }

    @Override
    public void expand(Vector... vectors) throws RegionOperationException {
        region.expand(vectors);
    }

    @Override
    public void contract(Vector... vectors) throws RegionOperationException {
        region.contract(vectors);
    }

    @Override
    public void shift(Vector vector) throws RegionOperationException {
        region.shift(vector);
    }

    @Override
    public boolean contains(Vector vector) {
        return region.contains(vector);
    }

    @Override
    public Set<Vector2D> getChunks() {
        return region.getChunks();
    }

    @Override
    public Set<Vector> getChunkCubes() {
        return region.getChunkCubes();
    }

    @Nullable
    @Override
    public World getWorld() {
        return region.getWorld();
    }

    @Override
    public void setWorld(@Nullable World world) {
        region.setWorld(world);
    }

    @Override
    public void setWorld(@Nullable LocalWorld localWorld) {
        region.setWorld(localWorld);
    }

    @Override
    public RegionWrapper clone() {
        return new RegionWrapper(region);
    }

    @Override
    public List<BlockVector2D> polygonize(int i) {
        return region.polygonize(i);
    }

    @Override
    public Iterator<BlockVector> iterator() {
        return region.iterator();
    }
}
