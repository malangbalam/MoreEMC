package mlbm.moreEMC.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hasunwoo this annotation is searched by moreEMC API annotation
 *         handling mechanism. any constants that available in annotated class
 *         will added to script runtime. constant value must be static and
 *         public. loading of constant can be controlled
 *         using @IScriptConstantController
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScriptConstantProvider {
	/**
	 * propertyName is used to access your constant in java script. must not
	 * conflict with other API's classname.
	 * 
	 * @return
	 */
	public String propertyName();
}
