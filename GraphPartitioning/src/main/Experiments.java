package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import heuristics.StartHeuristic1;
import heuristics.StartHeuristic2;

public class Experiments {

	// file and extension names
	public static String TEST_GRAPHS_DIR_NAME = "F:\\Entwicklung\\git-test\\graph-partitioning\\TestGraphs";
	public static String TMP_DIR_NAME = "tmp";
	public static String EXPERIMENT_RESULT_EXTENSION = ".exr";
	public static String TIMESTAMP = "" + System.currentTimeMillis();

	// arg options
	public static Options OPTIONS = new Options();
	public static String TEST_GRAPH_DIR_OPTION = "t";

	private static File TMP_DIR;
	private static File TEST_GRAPHS_DIR;
	private static int[] kArray = new int[] { 2, 4, 8, 16, 32, 64 };

	private static void generateOptions() {
		OPTIONS.addOption(TEST_GRAPH_DIR_OPTION, "test_graphs_dir", true,
				"absolute path the dir containing testgraphs");
		OPTIONS.addOption(Main.HELP_OPTION, "help", false, "print this message");
	}

	private static void testHeuristic(String heuristicClassPath) throws IOException {

		String heuristicName = heuristicClassPath.replace("heuristics.", "");

		File latexFile = new File(TMP_DIR + "/exp_result_" + heuristicName + "_" + TIMESTAMP + ".tex");
		latexFile.createNewFile();
		FileWriter latexWriter = new FileWriter(latexFile);
		List<String> argz = new ArrayList<>();

		Object[] printResults = new String[kArray.length + 1];
		printResults[0] = "graph";
		StringBuilder sbFormat = new StringBuilder();
		sbFormat.append("| %-12s "); // first col
		StringBuilder emptyLine = new StringBuilder();
		emptyLine.append("+--------------");
		String latexheader = "\\bgroup\n\\scriptsize\n\\sffamily\n\\def\\arraystretch{1.5}\n\\begin{tabular}{| l ";
		for (int i = 0; i < kArray.length; i++) {
			printResults[i + 1] = "" + kArray[i];
			sbFormat.append("| %-18s ");
			emptyLine.append("+--------------------");
			latexheader += "| l ";
		}
		latexheader += "|} \\hline\n\tgraph ";
		for (int i = 0; i < kArray.length; i++)
			latexheader += "& " + kArray[i] + " ";
		latexheader += "\\\\\\hline\n";
		emptyLine.append("+\n");
		sbFormat.append("|%n");

		System.out.format(emptyLine.toString());
		System.out.format(sbFormat.toString(), printResults); // currently the
																// header
		System.out.format(emptyLine.toString());

		latexWriter.write(latexheader);
		argz.add("-q");
		for (File inputGraphFile : TEST_GRAPHS_DIR.listFiles(TEST_GRAPH_FILENAME_FILTER)) {

			// DEBUG System.out.println("argz-length="+argz.size());

			// TODO ugly!
			String graphName = inputGraphFile.getName().replace(".graph", "");

			printResults[0] = graphName;
			String latexTableRow = "\t";
			String latexGraphName = graphName.replace("_", "\\_");
			latexTableRow += latexGraphName + "&";
			for (int i = 0; i < kArray.length; i++) {
				String tmpFilename = TIMESTAMP + "-" + graphName + "-" + heuristicName + "-" + kArray[i];

				// TODO ugly...
				argz.add("-i");
				argz.add(inputGraphFile.getAbsolutePath());
				argz.add("-e");
				argz.add(tmpFilename);
				argz.add("-heu");
				argz.add(heuristicClassPath);
				argz.add("-k");
				argz.add("" + kArray[i]);

				Main.main(argz.toArray(new String[argz.size()]));

				argz.remove("-i");
				argz.remove(inputGraphFile.getAbsolutePath());
				argz.remove("-e");
				argz.remove(tmpFilename);
				argz.remove("-heu");
				argz.remove(heuristicClassPath);
				argz.remove("-k");
				argz.remove("" + kArray[i]);

				// double duration = (double) (System.currentTimeMillis() -
				// start) / 1000;

				// BufferedReader reader = new BufferedReader(
				// new FileReader());
				// String checkResult = reader.readLine();
				Properties properties = new Properties();
				InputStream is = new FileInputStream(
						new File(TMP_DIR + "/" + tmpFilename + EXPERIMENT_RESULT_EXTENSION));
				properties.load(is);

				printResults[i + 1] = properties.getProperty("cutweight") + " [" + properties.getProperty("time") + "]";
				latexTableRow += properties.getProperty("cutweight") + " [" + properties.getProperty("time") + "]"
						+ " & ";

				is.close();
			}
			latexTableRow = latexTableRow.substring(0, latexTableRow.length() - 3);
			latexTableRow += "\\\\\n";
			latexWriter.write(latexTableRow);

			System.out.format(sbFormat.toString(), printResults);

		}
		System.out.format(emptyLine.toString());
		latexWriter.write("\t\\hline\n\\end{tabular}\n\\egroup");
		latexWriter.flush();
		latexWriter.close();

		// deleting all check results
		for (File experimentResultFile : TMP_DIR.listFiles(EXPERIMENT_RESULT_FILENAME_FILTER)) {
			experimentResultFile.delete();
		}
	}

	public static void main(String[] args) {

		generateOptions();
		try {

			TMP_DIR = new File(TMP_DIR_NAME);
			TMP_DIR.mkdirs();

			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(OPTIONS, args);

			// help
			if (cmd.hasOption(Main.HELP_OPTION)) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("graph-partitioner (experiments)", OPTIONS);
				return;
			}

			// optional another test_graphs_dir
			if (cmd.hasOption(TEST_GRAPH_DIR_OPTION))
				TEST_GRAPHS_DIR_NAME = cmd.getOptionValue(TEST_GRAPH_DIR_OPTION);
			TEST_GRAPHS_DIR = new File(TEST_GRAPHS_DIR_NAME);
			if (!TEST_GRAPHS_DIR.isDirectory()) {
				System.err.println("provided test_graphs_dir is no directory");
				return;
			}
			System.out.println("using test_graphs_dir=" + TEST_GRAPHS_DIR_NAME + " ("
					+ TEST_GRAPHS_DIR.listFiles(TEST_GRAPH_FILENAME_FILTER).length + " Files)");

			// testing heuristics
			long overallStart = System.currentTimeMillis();
			testHeuristic(StartHeuristic1.class.getName());
			testHeuristic(StartHeuristic2.class.getName());
			System.out.println(
					"experiments finished after: " + (double) (System.currentTimeMillis() - overallStart) / 1000 + "s");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static FilenameFilter TEST_GRAPH_FILENAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".graph");
		}
	};
	private static FilenameFilter EXPERIMENT_RESULT_FILENAME_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(EXPERIMENT_RESULT_EXTENSION) && name.contains(TIMESTAMP);
		}
	};

}
