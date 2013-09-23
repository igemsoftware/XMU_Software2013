package ttec;

public class AlgorithmOne
{
	public static boolean Warn;
    public static String Warning;
    public static String Help;
    public static String[] aTail;
    public static String[] stemloop;
    public static String[] spacerSequence;
    public static String[] tTail;
    public static int[] stemloopPosition;
    public static int[] ttailPosition;
    public static int[] traceBackNumber;
    public static int[][][] structure;
    public static double[] terminatorEfficiency;
    public static double[] hairpinscore;
    public static double[] ttailscore;
    public static double[] dscore;
    public static String[][] structure_string;
    public static String terminatorSequence;
    
    public static void input(String terminatorSequenceInput, int top, int minTTailSequenceLength, boolean tailScoreAlgorithm, boolean autoExtendTail, boolean spacer, double HairpinscoreUpbounded, double ttailscoreUpbounded, boolean forward, boolean sbol, String path, String fileName)
    {
        Warning = null;
        Help = null;
        aTail = null;
        stemloop = null;
        spacerSequence = null;
        tTail = null;
        stemloopPosition = null;
        ttailPosition = null;
        traceBackNumber = null;
        structure = null;
        terminatorEfficiency = null;
        hairpinscore = null;
        ttailscore = null;
        dscore = null;
        OverallManager.OverallManagerConstructor(terminatorSequenceInput, top, minTTailSequenceLength, tailScoreAlgorithm, autoExtendTail, spacer, HairpinscoreUpbounded, ttailscoreUpbounded, forward, sbol, path, fileName);
        terminatorSequence = OverallManager.terminatorSequence;
        ErrorDisplay.errorDisplayConstructor();
        if (OverallManager.errorState != 0)
        {
            Warn = true;
        }
        else
        {
            Warn = false;
        }
        Warning = ErrorDisplay.Warning;
        Help = ErrorDisplay.Help;
        if (Warn == false)
        {
            aTail = new String[top];
            aTail = OverallManager.aTail;
            stemloop = new String[top];
            stemloop = OverallManager.stemloop;
            spacerSequence = new String[top];
            spacerSequence = OverallManager.spacerSequence;
            tTail = new String[top];
            tTail = OverallManager.tTail;
            stemloopPosition = new int[top * 2];
            stemloopPosition = OverallManager.stemloopPosition;
            ttailPosition = new int[top];
            ttailPosition = OverallManager.ttailPosition;
            traceBackNumber = new int[top];
            traceBackNumber = OverallManager.traceBackNumber;
            structure = new int[top][][];
            for (int i = 0; i < top; i++)
            {
                structure[i] = new int[traceBackNumber[i]][terminatorSequence.length()];
            }
            structure = OverallManager.structure;

            ttailscore = new double[top];
            ttailscore = OverallManager.ttailscore;
            hairpinscore = new double[top];
            hairpinscore = OverallManager.hairpinscore;
            dscore = new double[top];
            dscore = OverallManager.dscore;
            terminatorEfficiency = new double[top];
            terminatorEfficiency = OverallManager.terminatorEfficiency;
            structure_string = new String[top][];
            

            for (int i = 0; i < top; i++)
            {
                structure_string[i] = new String[traceBackNumber[i]];
                for (int t = 0; t < traceBackNumber[i]; t++)
                {
                    for (int j = 0; j < terminatorSequence.length(); j++)
                    {
                        structure_string[i][t] += structure[i][t][j];
                    }
                }
            }
        }
    }
}
