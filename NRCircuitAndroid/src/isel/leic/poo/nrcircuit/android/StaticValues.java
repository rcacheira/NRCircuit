package isel.leic.poo.nrcircuit.android;

public class StaticValues {
	public final static String KEY_LEVEL_FINISHED = "NRCircuit_Level_Finished";
	public final static String KEY_MODULE_FINISHED = "NRCircuit_Module_Finished";
	public final static String KEY_MODULE = "NRCircuit_Module";
	public final static String KEY_LEVEL = "NRCircuit_Level";
	
	public final static String MODULES_FILE = "modules";
	public final static String PROGRESS_FILE_PREFIX = "d_";
	public final static String MODULES_PROGRESS_FILE = PROGRESS_FILE_PREFIX + MODULES_FILE;
	
	public static String getModuleProgressFile(String module){
		return PROGRESS_FILE_PREFIX + module;
	}
}
