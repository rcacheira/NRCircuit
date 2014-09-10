package isel.leic.poo.nrcircuit.android;

public class StaticValues {
	public final static String VIEW_STATE_KEY = "NRCircuit_grid";
	public final static String KEY_MODULE_FINISHED = "NRCircuit_Module_Finished";
	public final static String KEY_MODULE = "NRCircuit_Module";
	public final static String KEY_LEVEL = "NRCircuit_Level";

	public static final int MODULE_REQUEST = 1;
	public static final int LEVEL_REQUEST = 1;
	
	public final static String MODULES_FILE = "modules";
	public final static String PROGRESS_FILE_PREFIX = "d_";
	public final static String MODULES_PROGRESS_FILE = PROGRESS_FILE_PREFIX + MODULES_FILE;
	
	public static String getModuleProgressFile(String module){
		return PROGRESS_FILE_PREFIX + module;
	}
}
