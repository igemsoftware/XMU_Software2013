package ttec;

import java.io.File;
import java.io.FileReader;

public class OverallManager
{
	public static int errorState;
    public static String[] aTail;
    public static String[] stemloop;
    public static String[] spacerSequence;
    public static String[] tTail;
    public static double[] ttailscore;
    public static double[] hairpinscore;
    public static double[] dscore;
    public static double[] terminatorEfficiency;
    public static int[] traceBackNumber;
    public static int[][] recrusionTrackArray;
    public static int[][][] structure;
    public static int[] stemloopPosition;
    public static int[] ttailPosition;
    public static String terminatorSequence;
    public static void OverallManagerConstructor(String terminatorSequenceInput, int top, int minTTailSequenceLength, boolean tailScoreAlgorithm, boolean autoExtendTail, boolean spacer, double HairpinscoreUpbounded, double ttailscoreUpbounded, boolean forward, boolean sbol, String path, String fileName)
    {
        terminatorSequence = "";
        terminatorSequence = getTerminatorSequence(forward, sbol, terminatorSequenceInput, path, fileName);
       
        errorState = 0;
        recrusionTrackArray = new int[top][];
        structure = new int[top][][];
        traceBackNumber = new int[top];
        terminatorEfficiency = new double[top];
        aTail = new String[top];
        stemloop = new String[top];
        spacerSequence = new String[top];
        tTail = new String[top];
        ttailscore = new double[top];
        hairpinscore = new double[top];
        dscore = new double[top];
        stemloopPosition = new int[top * 2];
        ttailPosition = new int[top];
        for (int i = 0 ; i < terminatorSequence.length() ; i++)
        {
        	char a = terminatorSequence.charAt(i);
            if (a != ' ' && a != 'u' && a != 'c' && a != 'g' && a != 't' && a != 'a' && a != 'U' && a != 'C' && a != 'G' && a != 'T' && a != 'A')
            {System.out.println(a);
                errorState = 6; break;
            }
        }
        if (errorState != 6)
        {
            if (terminatorSequence.length() >= 150)
            {
                errorState = 4;
            }
            else if (terminatorSequence.length() <= 11)
            {
                errorState = 5;
            }
            else
            {
                ProtentialTTail.protentialTtailCalculation(terminatorSequence, minTTailSequenceLength);

                if (ProtentialTTail.getNoTTailWarning() == true)
                {
                    errorState = 1;
                }
                else
                {
                    CalculationCore.terminatorSequence = terminatorSequence;
                    CalculationCore.calculationCoreConstructor();
                    Stemloop.stemloopConstructor(terminatorSequence, spacer, top, HairpinscoreUpbounded);

                    if ((Stemloop.getArraySizeWarning() == false) && (Stemloop.getStemLoopMinSizeWarning() == false))
                    {


                        stemloopPosition = Stemloop.tophairpinScorePosition;
                        ttailPosition = Stemloop.topttailPosition;
                        for (int i = 0; i <= top - 1; i++)
                        {

                            CalculationCore.traceBack(Stemloop.tophairpinScorePosition[2 * i], Stemloop.tophairpinScorePosition[2 * i + 1]);
                            recrusionTrackArray[i] = new int[CalculationCore.recrusionTrackNumber * terminatorSequence.length()];
                            structure[i] = new int[CalculationCore.recrusionTrackNumber][terminatorSequence.length()];
                            for (int j = 0; j <= CalculationCore.recrusionTrackNumber * terminatorSequence.length() - 1; j++)
                            {

                                recrusionTrackArray[i][j] = CalculationCore.recrusionTrack[j];

                            }
                            for (int t = 0; t <= CalculationCore.recrusionTrackNumber; t++)
                            {
                                for (int j = 0; j <= CalculationCore.recrusionTrackNumber * terminatorSequence.length() - 1; j++)
                                {
                                    if ((j <= (t + 1) * terminatorSequence.length() - 1) && (j >= t * terminatorSequence.length()))
                                    {
                                        structure[i][t][ j - t * terminatorSequence.length()] = recrusionTrackArray[i][j];
                                    }
                                }
                            }
                            TTailScoreCal.tTailScoreConstructor(terminatorSequence.substring(ttailPosition[i]), minTTailSequenceLength, tailScoreAlgorithm, autoExtendTail);
                            aTail[i] = terminatorSequence.substring(0, stemloopPosition[2 * i]);
                            stemloop[i] = terminatorSequence.substring(stemloopPosition[2 * i], stemloopPosition[2 * i] + stemloopPosition[2 * i + 1] - stemloopPosition[2 * i] + 1);
                            spacerSequence[i] = terminatorSequence.substring(stemloopPosition[2 * i + 1] + 1, stemloopPosition[2 * i + 1] + 1 + ttailPosition[i] - stemloopPosition[2 * i + 1] - 1);
                            tTail[i] = terminatorSequence.substring(ttailPosition[i]);

                            ttailscore[i] = TTailScoreCal.getTailScore();
                            hairpinscore[i] = Stemloop.tophairpinScore[i];
                            traceBackNumber[i] = CalculationCore.recrusionTrackNumber;
                            terminatorEfficiency[i] = Efficiency.terminatorEfficiencyCal(ttailscore[i], hairpinscore[i], stemloopPosition[2 * i + 1] - stemloopPosition[2 * i] + 1);
                            dscore[i] = Efficiency.dscore;


                        }
                    }
                    else
                    {
                        if (Stemloop.getArraySizeWarning() == true)
                        {
                            errorState = 2;
                        }
                        if (Stemloop.getStemLoopMinSizeWarning() == true)
                        {
                            errorState = 3;
                        }
                    }


                }
            }

        }
    }

    public static String getTerminatorSequence(boolean forward, boolean sbol, String terminatorSequenceInput, String path, String fileName)
    {
        String terminatorSequence = "";
        if (sbol == true)
        {
            terminatorSequence = getSbolTerminatorSequence(path, fileName);

        }
        else
        {
            terminatorSequence = terminatorSequenceInput;
        }
        terminatorSequence= getForwardTerminatorSequence(terminatorSequence, forward);
        if (terminatorSequence == null) terminatorSequence = "a";
        terminatorSequence = terminatorSequence.toUpperCase();
        return terminatorSequence;
    }

    public static String getForwardTerminatorSequence(String terminatorSequenceInput,boolean forward)
    {
        terminatorSequenceInput = terminatorSequenceInput.toUpperCase();
        if (forward == false)
        {
            String forwardSequence = "";
            for (int i = terminatorSequenceInput.length() - 1; i >= 0; i--)
            {
                forwardSequence += Complete(terminatorSequenceInput.charAt(i));
            }
            return forwardSequence;
        }
        else
        {
            String newTerminatorSequence = "";
            
            for (int i = 0 ; i < terminatorSequenceInput.length() ; i++)
            {
            	char a = terminatorSequenceInput.charAt(i);
            	newTerminatorSequence += UtoT(a);
            }
            
//            foreach (char A in terminatorSequenceInput)
//            {
//                newTerminatorSequence += UtoT(A).ToString();
//            }
            return newTerminatorSequence;
        }

    }

    public static String getSbolTerminatorSequence(String path, String fileName)
    {
    	return null;
    }

    public static char Complete(char A)
    {
        char T;
        if (A == 'A')
        {
            T = 'T';
        }
        else if (A == 'C')
        {
            T = 'G';
        }
        else if (A == 'G')
        {
            T = 'C';
        }
        else
        {
            T = 'A';
        }
        return T;
    }

    public static char UtoT(char U)
    {
        char T;
        if (U == 'U')
        {
            T = 'T';
        }
        else
        {
            T = U;
        }
        return T;
    }
}
