package main;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import heuristics.StartHeuristic1;
import heuristics.StartHeuristic2;
import interfaces.Heuristic;
import io.ConsoleLogger;
import io.ExperimentWriter;
import io.GraphFormatReader;
import io.PartitionWriter;
import main.Checker.CheckResult;
import structs.HashMapGraph;

@SuppressWarnings("unused")
public class Main {

	public static String INPUT_FILE = "/test.graph";
	// public static String INPUT_FILE = "/tinytest.graph";
	public static String OUTPUT_FILE = "output.ptn";
	public static Heuristic heuristic;
	public static int K = 2;
	public static boolean VERBOSE = false;
	public static boolean QUIET = false;

	public static Options OPTIONS = new Options();
	public static String HELP_OPTION = "h";
	public static String K_OPTION = "k";
	public static String INPUT_FILE_OPTION = "i";
	public static String OUTPUT_FILE_OPTION = "o";
	public static String VERBOSE_OPTION = "v";
	public static String QUIET_OPTION = "q";
	public static String EXPERIMENT_ID_OPTION = "e";
	public static String HEURISTIC_OPTION = "heu";

	private static void generateOptions() {
		OPTIONS.addOption(HELP_OPTION, "help", false, "print this message");
		OPTIONS.addOption(K_OPTION, true, "number of partitions the graph will parted (default is " + K + ")");
		OPTIONS.addOption(INPUT_FILE_OPTION, "input_file", true,
				"the absolute path to a file used as input (the extension has to be .graph)");
		OPTIONS.addOption(OUTPUT_FILE_OPTION, "output_file", true, "the absolute path to a file used as output");
		OPTIONS.addOption(VERBOSE_OPTION, "verbose", false, "be extra verbose");
		OPTIONS.addOption(QUIET_OPTION, "quiet", false, "be extra quiet");
		OPTIONS.addOption(EXPERIMENT_ID_OPTION, true, "has to be a unique id; saves experiment results with that id");
		OPTIONS.addOption(HEURISTIC_OPTION, "heuristic", true, "classname of the heuristic that should be used");
	}

	public static void main(String[] args) {

		long overallStartTime = System.currentTimeMillis();
		generateOptions();

		try {

			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(OPTIONS, args);

			if (cmd.hasOption(HELP_OPTION)) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("graph-partitioner", OPTIONS);
				return;
			}

			// args evaluation
			
			// verbose & quiet
			VERBOSE = cmd.hasOption(VERBOSE_OPTION);
			QUIET = cmd.hasOption(QUIET_OPTION);

			// set up the logger for Main
			ConsoleLogger logger = new ConsoleLogger(Main.class.getSimpleName());
			logger.log("starting graph-partitioning");

			// heuristic
			heuristic = new StartHeuristic1();

			// input_file
			InputStream inputStream = null;
			if (cmd.hasOption(INPUT_FILE_OPTION)) {
				INPUT_FILE = cmd.getOptionValue(INPUT_FILE_OPTION);
				logger.log("using input_file=" + INPUT_FILE);
				inputStream = new FileInputStream(INPUT_FILE);
			} else {
				logger.log("no input_file specified, using: " + INPUT_FILE);
				inputStream = Main.class.getResourceAsStream(INPUT_FILE);
			}

			// output_file
			if (cmd.hasOption(OUTPUT_FILE_OPTION)) {
				OUTPUT_FILE = cmd.getOptionValue(OUTPUT_FILE_OPTION);
				logger.log("using output_file=" + OUTPUT_FILE);
			} else
				logger.log("no output_file specified, using: " + OUTPUT_FILE);

			// k
			if (cmd.hasOption(K_OPTION)) {
				K = Integer.parseInt(cmd.getOptionValue(K_OPTION));
				logger.log("using k=" + K);
			} else
				logger.log("no K specified, using K=" + K);

			// heuristic
			if (cmd.hasOption(HEURISTIC_OPTION)) {
				heuristic = (Heuristic) Class.forName(cmd.getOptionValue(HEURISTIC_OPTION)).newInstance();
			}

			// TEST: input_ and output_file specified?
			if (INPUT_FILE == null || OUTPUT_FILE == null) {
				logger.logError("input_file=" + INPUT_FILE + "; output_file=" + OUTPUT_FILE);
				return;
			}

			long tmpStartTime = System.currentTimeMillis();
			HashMapGraph graph = new HashMapGraph(logger);
			GraphFormatReader reader = new GraphFormatReader();
			graph = (HashMapGraph) reader.read(inputStream, graph);
			double readingTime = (double) (System.currentTimeMillis() - tmpStartTime) / 1000;

			// TEST:is k greater then vertice count?
			if (K > graph.getVerticesCount()) {
				logger.logError("the choosen K (" + K
						+ ") is bigger then the vertice count, in that case graph partitioning won't work");
				logger.logError(
						"terminated after " + (double) (System.currentTimeMillis() - overallStartTime) / 1000 + "s");
				return;
			}

			// partitioning
			tmpStartTime = System.currentTimeMillis();
			Partitioner partitioner = new Partitioner();
			partitioner.partGraph(heuristic, graph, K);
			double partitioningTime = (double) (System.currentTimeMillis() - tmpStartTime) / 1000;

			// checking
			tmpStartTime = System.currentTimeMillis();
			Checker checker = new Checker();
			CheckResult checkResult = checker.check(graph, K);
			double checkingTime = (double) (System.currentTimeMillis() - tmpStartTime) / 1000;

			// write result
			tmpStartTime = System.currentTimeMillis();
			PartitionWriter writer = new PartitionWriter();
			writer.write(graph, OUTPUT_FILE);
			double writingTime = (double) (System.currentTimeMillis() - tmpStartTime) / 1000;

			double overallTime = (double) (System.currentTimeMillis() - overallStartTime) / 1000;

			// logging time summary
			logger.logFormat("graph-partitioning finished\ntime stopping summary:\n");
			String leftAlignFormat = "| %-12s | %-8s |%n";
			logger.logFormat("+--------------+----------+%n");
			logger.logFormat(leftAlignFormat, "reading", readingTime + "s");
			logger.logFormat(leftAlignFormat, "partitioning", partitioningTime + "s");
			logger.logFormat(leftAlignFormat, "checking", checkingTime + "s");
			logger.logFormat(leftAlignFormat, "writing", writingTime + "s");
			logger.logFormat(leftAlignFormat, "overall", overallTime + "s");
			logger.logFormat("+--------------+----------+%n");

			// doc experiments
			if (cmd.hasOption(EXPERIMENT_ID_OPTION) && checkResult != null) {
				ExperimentWriter expWriter = new ExperimentWriter();
				expWriter.write(cmd.getOptionValue(EXPERIMENT_ID_OPTION), checkResult, overallTime + "s");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}