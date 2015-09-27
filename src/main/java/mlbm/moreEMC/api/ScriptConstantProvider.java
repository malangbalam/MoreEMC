package mlbm.moreEMC.api;

/**
 * @author hasunwoo this annotation is searched by moreEMC API annotation
 *         handling mechanism. any constants that available in annotated class
 *         will added to script runtime. constant value must be static and
 *         public.
 */
public @interface ScriptConstantProvider {
	/**
	 * propertyName is used to access your constant in java script.
	 * 
	 * @return
	 */
	public String propertyName();
}
