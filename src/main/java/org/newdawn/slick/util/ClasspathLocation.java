package org.newdawn.slick.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;

/**
 * A resource location that searches the classpath
 * 
 * @author kevin
 */
public class ClasspathLocation implements ResourceLocation {
    /**
     * @see org.newdawn.slick.util.ResourceLocation#getResource(java.lang.String)
     */
    @Nullable
    public URL getResource(@Nonnull String ref) {
        String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResource(cpRef);
    }

    /**
     * @see org.newdawn.slick.util.ResourceLocation#getResourceAsStream(java.lang.String)
     */
    public InputStream getResourceAsStream(@Nonnull String ref) {
        String cpRef = ref.replace('\\', '/');
        return ResourceLoader.class.getClassLoader().getResourceAsStream(cpRef);
    }

}
