package mlbm.moreEMC.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author hasunwoo Annotated class must extends @ScriptAPIProvider. this
 *         annotation is searched by moreEMC API annotation handling mechanism.
 *         any class that extends @ScriptAPIProvider will added to script
 *         runtime automatically.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScriptAPI {
}
