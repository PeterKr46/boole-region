package com.gmail.pkr4mer.boole.region;

import com.gmail.pkr4mer.boole.exception.BoolePairException;
import com.gmail.pkr4mer.boole.exception.RegionNotInClipboardException;
import com.sk89q.worldedit.world.World;

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
