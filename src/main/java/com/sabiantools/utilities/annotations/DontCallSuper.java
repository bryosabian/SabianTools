package com.sabiantools.utilities.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Denotes that any overriding methods should not invoke this method.
 * <p>
 * Example:
 * <pre><code>
 *  &#64;DontCallSuper
 *  public abstract void onFocusLost();
 * </code></pre>
 */
@Documented
@Retention(CLASS)
@Target({METHOD})
public @interface DontCallSuper {
}
