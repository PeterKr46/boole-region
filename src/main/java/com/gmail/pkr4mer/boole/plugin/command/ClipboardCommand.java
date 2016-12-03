package com.gmail.pkr4mer.boole.plugin.command;

import com.gmail.pkr4mer.boole.exception.IncompleteSelectionException;
import com.gmail.pkr4mer.boole.exception.InvalidSessionException;
import com.gmail.pkr4mer.boole.parser.BooleParser;
import com.gmail.pkr4mer.boole.plugin.BooleRegionPlugin;
import com.gmail.pkr4mer.boole.plugin.session.Clipboard;
import com.gmail.pkr4mer.boole.plugin.session.Session;
import com.gmail.pkr4mer.boole.region.BooleRegion;
import com.gmail.pkr4mer.boole.region.BooleRegionSelector;
import com.gmail.pkr4mer.boole.region.RegionWrapper;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

import static com.gmail.pkr4mer.boole.lang.Lang.*;

/**
 * Created by Peter on 03-Dec-16.
 */
public class ClipboardCommand implements CommandExecutor {
    
    private final BooleRegionPlugin owner;
    
    
    public ClipboardCommand(BooleRegionPlugin owner) {
        this.owner = owner;
    }
    
    private boolean listCommand(Player sender, String[] args) {
        Session session = null;
        if (args.length > 1) {
            // Can supply a target to list clipboard.
            Player targetPlayer = getOnlinePlayer(args[1]);
            if(targetPlayer == null) {
                sender.sendMessage(PLAYER_NOT_FOUND);
            } else {
                session = owner.getSessionFactory().getSession(targetPlayer.getUniqueId());
            }
        } else {
            // Otherwise their own clipboard is used.
            session = owner.getSessionFactory().getSession(sender.getUniqueId());
        }
        if(session == null) {
            // Didn't manage to find a valid session.
            sender.sendMessage(INVALID_SESSION);
        } else {
            sender.sendMessage(listClipboard(session));
        }
        return true;
    }
    
    private boolean storeCommand(Player sender, String[] args) {
        boolean success = false;
        if(args.length > 1) {
            String[] targetEntry = args[1].split(":");
            Clipboard clipboard = getClipboard(sender, targetEntry);
            BooleRegion selection = getWorldEditSelection(sender);
            if(selection != null) {
                clipboard.storeRegion(targetEntry[targetEntry.length-1].replaceAll("[^a-zA-Z0-9 -]", ""), selection);
                sender.sendMessage(SELECTION_STORED);
            } else {
                sender.sendMessage(INCOMPLETE_SELECTION);
            }
            success = true;
        }
        return success;
    }
    
    private boolean loadCommand(Player sender, String[] args) {
        boolean success = false;
        if(args.length > 1) {
            success = true;
            Clipboard clipboard = owner.getSessionFactory().getSession(sender.getUniqueId()).getClipboard();
            String logic = String.join(" ", Arrays.stream(args).skip(1).collect(Collectors.toList()));
            BooleRegion selection = BooleParser.parse(logic, clipboard);
            if(selection != null) {
                setWorldEditSelection(sender, selection);
                sender.sendMessage(SELECTION_LOADED);
            }
        }
        return success;
    }
    
    
    private boolean cloneCommand(Player sender, String[] args) {
        boolean success = false;
        if(args.length > 2) {
            success = true;
            Clipboard clipboardFrom, clipboardTo;
            String[] fromEntry = args[1].split(":");
            String[] toEntry = args[2].split(":");
            clipboardFrom = getClipboard(sender, fromEntry);
            clipboardTo = getClipboard(sender, toEntry);
            if(clipboardFrom == null || clipboardTo == null) {
                sender.sendMessage(PLAYER_NOT_FOUND);
            } else {
                String fromId = fromEntry[fromEntry.length-1], toId = toEntry[toEntry.length-1].replaceAll("[^a-zA-Z0-9 -]", "");
                if(clipboardFrom.isUsed(fromId)) {
                    clipboardTo.storeRegion(toId, clipboardFrom.getRegion(fromId).clone());
                    sender.sendMessage(CLIPBOARD_CLONE_COMPLETE);
                } else {
                    sender.sendMessage(CLIPBOARD_ENTRY_NOT_FOUND);
                }
            }
        }
        return success;
    }
    
    private String listClipboard(Session session) {
        String list;
        if(session.getClipboard().isEmpty()) {
            list = CLIPBOARD_EMPTY;
        } else {
            list = String.format(CLIPBOARD_CONTENT_FORMAT, String.join(", ",session.getClipboard().getKeys()));
        }
        return list;
    }
    
    private BooleRegion getWorldEditSelection(Player player) {
        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        com.sk89q.worldedit.regions.Region selection;
        try {
            selection = wep.getSession(player).getRegionSelector((com.sk89q.worldedit.world.World) new BukkitWorld(player.getWorld())).getRegion();
        } catch (IncompleteRegionException e) {
            throw new IncompleteSelectionException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidSessionException();
        }
        return selection == null ? null : new RegionWrapper(selection);
    }
    
    private void setWorldEditSelection(Player player, BooleRegion booleRegion) {
        WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        Region region = booleRegion;
        if(booleRegion.isRegionWrapper()) {
            region = ((RegionWrapper)booleRegion).getRegion();
        }
        try {
            RegionSelector selector = createSelector(region);
            wep.getSession(player).setRegionSelector(selector.getWorld(), selector);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidSessionException();
        }
    }
    
    private RegionSelector createSelector(Region region) {
        RegionSelector selector = null;
        if(region instanceof CuboidRegion) {
            selector = new com.sk89q.worldedit.regions.selector.CuboidRegionSelector(region.getWorld(), region.getMinimumPoint(), region.getMaximumPoint());
        } else if(region instanceof EllipsoidRegion) {
            selector =  new com.sk89q.worldedit.regions.selector.EllipsoidRegionSelector(region.getWorld(), region.getCenter(), ((EllipsoidRegion) region).getRadius());
        } else if(region instanceof Polygonal2DRegion) {
            selector =  new com.sk89q.worldedit.regions.selector.Polygonal2DRegionSelector(region.getWorld(), ((Polygonal2DRegion) region).getPoints(), ((Polygonal2DRegion) region).getMinimumY(), ((Polygonal2DRegion) region).getMaximumY());
        } else if(region instanceof CylinderRegion) {
            selector =  new com.sk89q.worldedit.regions.selector.CylinderRegionSelector(region.getWorld(), region.getCenter().toVector2D(), ((CylinderRegion) region).getRadius(), ((CylinderRegion) region).getMinimumY(), ((CylinderRegion) region).getMaximumY());
        } else if(region instanceof ConvexPolyhedralRegion) {
            selector = new com.sk89q.worldedit.regions.selector.ConvexPolyhedralRegionSelector(region.getWorld());
            for (Vector vertex : ((ConvexPolyhedralRegion) region).getVertices()) {
                ((ConvexPolyhedralRegion) selector.getIncompleteRegion()).addVertex(vertex);
            }
        } else if(region instanceof BooleRegion) {
            selector = new BooleRegionSelector((BooleRegion) region);
        }
        return selector;
    }
    
    private Clipboard getClipboard(Player sender, String[] request) {
        Clipboard clipboard = null;
        if(request.length > 1) {
            Player targetPlayer = getOnlinePlayer(request[0]);
            if(targetPlayer != null) {
                clipboard = owner.getSessionFactory().getSession(targetPlayer.getUniqueId()).getClipboard();
            }
        } else {
            clipboard = owner.getSessionFactory().getSession(sender.getUniqueId()).getClipboard();
        }
        return clipboard;
    }
    
    private Player getOnlinePlayer(String name) {
        Player result = null;
        Iterator<? extends Player> players = Bukkit.getOnlinePlayers().iterator();
        while(players.hasNext() && result == null) {
            Player player = players.next();
            if(player.getName().equalsIgnoreCase(name)) {
                result = player;
            }
        }
        return result;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean success = false;
        if(sender instanceof Player) {
            if(args.length > 0) {
                Player player = (Player) sender;
                try {
                    switch (args[0].toLowerCase()) {
                        case "list": {
                            success = listCommand(player, args);
                            break;
                        }
                        case "copy":
                        case "clone": {
                            success = cloneCommand(player, args);
                            break;
                        }
                        case "save":
                        case "store": {
                            success = storeCommand(player, args);
                            break;
                        }
                        case "select":
                        case "load": {
                            success = loadCommand(player, args);
                            break;
                        }
                    }
                } catch (Exception e) {
                    player.sendMessage("§4ERROR: §c" + e.getMessage());
                }
            }
        } else {
            sender.sendMessage(PLAYER_ONLY_COMMAND);
        }
        return success;
    }
}
