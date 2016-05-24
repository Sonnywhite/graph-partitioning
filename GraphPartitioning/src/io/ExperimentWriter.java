package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import main.Checker.CheckResult;
import main.Experiments;

public class ExperimentWriter {
	
	public void write(String id, CheckResult checkResult, String time) {
		
		File tmpDir = new File(Experiments.TMP_DIR_NAME);
		File newFilename = new File(tmpDir.getAbsolutePath()+"/"+id+Experiments.EXPERIMENT_RESULT_EXTENSION);
		
		try {
			newFilename.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		try (FileWriter writer = new FileWriter(newFilename)) {
			writer.write("time="+time+"\n");
			writer.write("heuristic="+checkResult.getUsedHeuristic()+"\n");
			writer.write("cutweight="+checkResult.getCutWeight()+"\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return;
	}

}
