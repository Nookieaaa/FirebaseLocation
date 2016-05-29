package com.nookdev.firebaselocation.dummy;

import com.nookdev.firebaselocation.model.AppUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<AppUser> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, AppUser> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(AppUser item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static AppUser createDummyItem(int position) {
        return new AppUser(null,"Item " + position);
    }

}
