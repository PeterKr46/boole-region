package eu.saltyscout.booleregion.region;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.extension.platform.Actor;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.regions.selector.limit.SelectorLimits;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Peter on 18-Nov-16.
 */
public class BooleRegionSelector implements RegionSelector {
    private BooleRegion shape;

    public BooleRegionSelector(BooleRegion region) {
        checkNotNull(region);
        shape = region;
    }

    public boolean isRegionWrapper() {
        return shape.isRegionWrapper();
    }

    @Nullable
    @Override
    public World getWorld() {
        return shape.getWorld();
    }

    @Override
    public void setWorld(@Nullable World world) {
        shape.setWorld(world);
    }

    @Override
    public boolean selectPrimary(BlockVector3 vector, SelectorLimits selectorLimits) {
        return true;
    }

    @Override
    public boolean selectSecondary(BlockVector3 vector, SelectorLimits selectorLimits) {
        return true;
    }

    @Override
    public void explainPrimarySelection(Actor actor, LocalSession localSession, BlockVector3 vector) {
        actor.print(ChatColor.GREEN + "Current selection (arbitrary names): " + shape.explain((char) (((int)'a')-1)));
        actor.print(ChatColor.RED + "//sel cuboid" + ChatColor.GREEN + "to change back to normal selection mode.");
    }

    @Override
    public void explainSecondarySelection(Actor actor, LocalSession localSession, BlockVector3 vector) {
        actor.print(ChatColor.GREEN + "Current selection (arbitrary names): " + shape.explain((char) (((int)'a')-1)));
        actor.print(ChatColor.RED + "//sel cuboid" + ChatColor.GREEN + "to change back to normal selection mode.");
    }

    @Override
    public void explainRegionAdjust(Actor actor, LocalSession localSession) {

    }

    @Override
    public BlockVector3 getPrimaryPosition() throws IncompleteRegionException {
        return shape.getCenter().toBlockPoint();
    }

    @Override
    public Region getRegion() throws IncompleteRegionException {
        return shape.clone();
    }

    @Override
    public Region getIncompleteRegion() {
        return shape.clone();
    }

    @Override
    public boolean isDefined() {
        return shape != null;
    }

    @Override
    public int getArea() {
        return shape.getArea();
    }

    @Override
    public void learnChanges() {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getTypeName() {
        return "boole";
    }

    @Override
    public List<String> getInformationLines() {
        return Collections.singletonList("This is a Boole Region. A composite of multiple standard-type regions.");
    }
}
