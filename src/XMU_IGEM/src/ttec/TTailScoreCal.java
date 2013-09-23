package ttec;

public class TTailScoreCal
{
	public static boolean shortLengthWarning;
    public static double tailScore;

    public static int min(int a, int b)
    {
        if (a > b)
        {
            a = b;
        }
        return a;
    }
    public static void tTailScoreConstructor(String tTailSequence, int minTTailSequenceLength, boolean tailScoreAlgorithm, boolean autoExtendTail)
    {
        tailScore = 0;
        if (tTailSequence.length() < minTTailSequenceLength)
        {
            shortLengthWarning = true;
        }
        else
        {
            shortLengthWarning = false;
            double[] xn = new double[15];
            if (tailScoreAlgorithm == true)
            {
                xn[0] = 1;
            }
            else
            {
                xn[0] = 0.9;
            }
            tailScore = xn[0];
            if (autoExtendTail == true)
            {
            	tTailSequence = tTailSequence.concat("               ");
//                tTailSequence = tTailSequence.PadRight(15, ' ');
            	System.out.println(tTailSequence);
                for (int i = 1; i <= 14; i++)
                {
                    if (tTailSequence.substring(i, i+1).equals("T"))
                    {
                        xn[i] = 0.9 * xn[i - 1];
                    }
                    else
                    {
                        xn[i] = 0.6 * xn[i - 1];
                    }
                    tailScore += xn[i];
                }
            }
            if (autoExtendTail == false)
            {
                for (int i = 1; i <= min(15, tTailSequence.length())-1; i++)
                {
                    if ((tTailSequence.substring(i, i+1).equals("T")))
                    {
                        xn[i] = 0.9 * xn[i - 1];
                    }
                    else
                    {
                        xn[i] = 0.6 * xn[i - 1];
                    }
                    tailScore += xn[i];
                }
            }
        }
    }
    public static double getTailScore()
    {
        return tailScore;
    }
    public static boolean getShortLengthWarning()
    {
        return shortLengthWarning;
    }
}
