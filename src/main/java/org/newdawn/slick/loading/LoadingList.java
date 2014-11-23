package org.newdawn.slick.loading;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A central list where all deferred loading resoures end up when deferred loading is in use. Each
 * texture and sound loaded will be put in this list and can be loaded in one by one 
 *
 * @author kevin
 */
public class LoadingList {
    /** The single instance of this list */
    @Nonnull
    private static LoadingList single = new LoadingList();

    /**
     * Get the single global loading list
     *
     * @return The single global loading list
     */
    @Nonnull
    public static LoadingList get() {
        return single;
    }

    /**
     * Indicate if we're going to use deferred loading. (Also clears the current list)
     *
     * @param loading True if we should use deferred loading
     */
    public static void setDeferredLoading(boolean loading) {
        single = new LoadingList();

        InternalTextureLoader.get().setDeferredLoading(loading);
        /** pc 2014-11-10 START commenting out as I am not currently using org.newdawn.slick.openal.SoundStore
         SoundStore.get().setDeferredLoading(loading);
         pc 2014-11-10 END
         */
    }

    /**
     * Check if we're using deferred loading
     *
     * @return True if we're using deferred loading
     */
    public static boolean isDeferredLoading() {
        return InternalTextureLoader.get().isDeferredLoading();
    }

    /** The list of deferred resources to load */
    @Nonnull
    private List<DeferredResource> deferred = new ArrayList<>();
    /** The total number of elements that have been added - does not go down as elements are removed */
    private int total;

    /**
     * Create a new list
     */
    private LoadingList() {
    }

    /**
     * Add a resource to be loaded at some later date
     *
     * @param resource The resource to be added
     */
    public void add(DeferredResource resource) {
        total++;
        deferred.add(resource);
    }

    /**
     * Remove a resource from the list that has been loaded for
     * other reasons.
     *
     * @param resource The resource to remove
     */
    public void remove(@Nonnull DeferredResource resource) {
        Log.info("Early loading of deferred resource due to req: "+resource.getDescription());
        total--;
        deferred.remove(resource);
    }

    /**
     * Get the total number of resources that were in the list originally
     *
     * @return The total number of resources that were in the list originally
     */
    public int getTotalResources() {
        return total;
    }

    /**
     * Get the number of remaining resources
     *
     * @return The number of resources that still need to be loaded
     */
    public int getRemainingResources() {
        return deferred.size();
    }

    /**
     * Get the next resource that requries loading
     *
     * @return The next resource to load or null if there are no more remaining
     */
    @Nullable
    public DeferredResource getNext() {
        if (deferred.size() == 0) {
            return null;
        }

        return deferred.remove(0);
    }
}
