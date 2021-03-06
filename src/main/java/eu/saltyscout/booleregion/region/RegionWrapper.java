package eu.saltyscout.booleregion.region;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
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
    public BlockVector3 getMinimumPoint() {
        return region.getMinimumPoint();
    }

    @Override
    public BlockVector3 getMaximumPoint() {
        return region.getMaximumPoint();
    }

    @Override
    public Vector3 getCenter() {
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
    public void expand(BlockVector3... vectors) throws RegionOperationException {
        region.expand(vectors);
    }

    @Override
    public void contract(BlockVector3... vectors) throws RegionOperationException {
        region.contract(vectors);
    }

    @Override
    public void shift(BlockVector3 vector) throws RegionOperationException {
        region.shift(vector);
    }

    @Override
    public boolean contains(BlockVector3 vector) {
        return region.contains(vector);
    }

    @Override
    public Set<BlockVector2> getChunks() {
        return region.getChunks();
    }

    @Override
    public Set<BlockVector3> getChunkCubes() {
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
    public RegionWrapper clone() {
        return new RegionWrapper(region);
    }

    @Override
    public List<BlockVector2> polygonize(int i) {
        return region.polygonize(i);
    }

    @Override
    public Iterator<BlockVector3> iterator() {
        return region.iterator();
    }
}
