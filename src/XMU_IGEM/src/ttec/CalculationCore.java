package ttec;

public class CalculationCore
{
	public static final double gap = 6.0;
    public static final double misMatch = 3.5;
    public static final double gt = 1.3;
    public static final double at = -0.9;
    public static final double gc = -2.3;
    public static final double hugeint = 9999.0;
    public static final int minStemLoopSize = 11;
    public static final int maxStemLoopSize = 61;
    public static int minObjectNumber;
    public static int recrusionTrackNumber;
    public static String terminatorSequence;
    /*public static ArrayList qwe;
    ArrayList qwe = new ArrayList(14);
     public static int[] rty;
     rty = new int[1000];
     */

    public static int[] recrusionTrack;
    public static double[][] matrixArray;
    public static int[] minObjectState;









    public static void calculationCoreConstructor()
    {
        matrixCalculation();



    }

    public static double min(double a, double b, double c, double d)
    {
        double output;
        if (a > b)
        {
            output = b;
        }
        else
        {
            output = a;
        }
        if (output > c)
        {
            output = c;
        }
        if (output > d)
        {
            output = d;
        }
        return output;
    }

    public static void minObject(double a, double b, double c, double d)
    {

        minObjectState = new int[4];
        double mindouble = min(a, b, c, d);
        if (mindouble == a)
        {
            minObjectState[0] = 1;
        }
        if (mindouble == b)
        {
            minObjectState[1] = 1;
        }
        if (mindouble == c)
        {
            minObjectState[2] = 1;
        }
        if (mindouble == d)
        {
            minObjectState[3] = 1;
        }

        minObjectNumber = 0;
        for (int i = 0; i <= 3; i++)
        {
            if (minObjectState[i] == 1)
            {
                minObjectNumber += 1;
            }
        }
    }


    public static void matrixCalculation()
    {
        matrixArray = new double[terminatorSequence.length()][];
        for (int i = 0; i <= terminatorSequence.length() - 1; i++)
        {
            matrixArray[i] = new double[terminatorSequence.length()];
        }
        for (int i = 0; i <= terminatorSequence.length() - 1; i++)
        {
            matrixArray[i][i] = 6.0;
        }
        for (int i = 0; i < terminatorSequence.length() - 1; i++)
        {
            matrixArray[i][i + 1] = 3.5;
        }
        for (int j = 2; j <= terminatorSequence.length() - 1; j++)
        {
            for (int i = 0; i <= terminatorSequence.length() - 1 - j; i++)
            {
                matrixArray[i][i + j] = hairpin_score(i, i + j);
            }
        }
    }
    public static double getMatrix(int i, int j)
    {
        matrixCalculation();
        return matrixArray[i][j];
    }




    public static double loop_pen(int n)
    {
        if (n >= 3 && n <= 13)
        {
            return n - 2;
        }
        return hugeint;

    }

    public static double energy(int i, int j)
    {
        if (((terminatorSequence.substring(i, i+1).equals("G")) && (terminatorSequence.substring(j, j+1).equals("C"))) || ((terminatorSequence.substring(i, i+1).equals("C")) && (terminatorSequence.substring(j, j+1).equals("G"))))
        {
            return gc;
        }
        if (((terminatorSequence.substring(i, i+1).equals("A")) && (terminatorSequence.substring(j, j+1).equals("T"))) || ((terminatorSequence.substring(i, i+1).equals("T")) && (terminatorSequence.substring(j, j+1).equals("A"))))
        {
            return at;
        }
        if (((terminatorSequence.substring(i, i+1).equals("G")) && (terminatorSequence.substring(j, j+1).equals("T"))) || ((terminatorSequence.substring(i, i+1).equals("T")) && (terminatorSequence.substring(j, j+1).equals("G"))))
        {
            return gt;
        }
        return misMatch;
    }

    public static double hairpin_score(int i, int j)
    {

        return min(loop_pen(j - i + 1), energy(i, j) + matrixArray[i + 1][j - 1], gap + matrixArray[i + 1][j], gap + matrixArray[i][j - 1]);
    }





    public static String complementaryResidue(String input)
    {
        if (input.equals("A"))
        { return "T"; }
        if (input.equals("T"))
        { return "A"; }
        if (input.equals("G"))
        { return "C"; }
        if (input.equals("C"))
        { return "G"; }
        if (input.equals("U"))
        { return "A"; }
        return "0";
    }











    public static void traceBackCal(int i, int j, int x)
    {

        do
        {
            minObject(loop_pen(j - i + 1), energy(i, j) + matrixArray[i + 1][j - 1], gap + matrixArray[i + 1][j], gap + matrixArray[i][j - 1]);
            if (minObjectNumber == 1)
            {


                if (minObjectState[1] == 1)
                {
                    if (energy(i, j) == misMatch)
                    {
                        i++;
                        j--;
                    }
                    else
                    {
                        recrusionTrack[i + x * terminatorSequence.length()] = 1;
                        recrusionTrack[j + x * terminatorSequence.length()] = 1;
                        i++;
                        j--;
                    }
                }
                else if (minObjectState[2] == 1)
                {
                    i++;
                }
                else if (minObjectState[3] == 1)
                {
                    j--;
                }
                else if (minObjectState[0] == 1)
                {
                    i = j;
                }

            }
            else if (minObjectNumber == 2)
            {
                if ((minObjectState[0] == 1) && (minObjectState[1] == 1))
                {
                    recrusionTrackNumber += 1;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + (recrusionTrackNumber - 1) * terminatorSequence.length()] = 1;
                        recrusionTrack[j + (recrusionTrackNumber - 1) * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, recrusionTrackNumber - 1);
                    break;
                }
                else if ((minObjectState[0] == 1) && (minObjectState[2] == 1))
                {
                    recrusionTrackNumber += 1;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    traceBackCal(i + 1, j, recrusionTrackNumber - 1);
                    break;
                }
                else if ((minObjectState[0] == 1) && (minObjectState[3] == 1))
                {
                    recrusionTrackNumber += 1;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    traceBackCal(i, j - 1, recrusionTrackNumber - 1);
                    break;
                }
                else if ((minObjectState[1] == 1) && (minObjectState[2] == 1))
                {
                    recrusionTrackNumber += 1;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + x * terminatorSequence.length()] = 1;
                        recrusionTrack[j + x * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, x);
                    traceBackCal(i + 1, j, keep - 1);
                    break;
                }
                else if ((minObjectState[1] == 1) && (minObjectState[3] == 1))
                {
                    recrusionTrackNumber += 1;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + x * terminatorSequence.length()] = 1;
                        recrusionTrack[j + x * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, x);
                    traceBackCal(i, j - 1, keep - 1);
                    break;
                }
                else if ((minObjectState[2] == 1) && (minObjectState[3] == 1))
                {
                    recrusionTrackNumber += 1;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    traceBackCal(i + 1, j, x);
                    traceBackCal(i, j - 1, keep - 1);
                    break;
                }
            }
            else if (minObjectNumber == 3)
            {
                if (minObjectState[0] == 0)
                {
                    recrusionTrackNumber += 2;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 2);
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + x * terminatorSequence.length()] = 1;
                        recrusionTrack[j + x * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, x);
                    traceBackCal(i + 1, j, keep - 2);
                    traceBackCal(i, j - 1, keep - 1);
                    break;

                }
                else if (minObjectState[1] == 0)
                {

                    recrusionTrackNumber += 2;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 2);
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    traceBackCal(i + 1, j, keep - 2);
                    traceBackCal(i, j - 1, keep - 1);
                    break;
                }
                else if (minObjectState[2] == 0)
                {
                    recrusionTrackNumber += 2;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 2);
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + (recrusionTrackNumber - 2) * terminatorSequence.length()] = 1;
                        recrusionTrack[j + (recrusionTrackNumber - 2) * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, keep - 2);
                    traceBackCal(i, j - 1, keep - 1);
                    break;
                }
                else if (minObjectState[3] == 0)
                {
                    recrusionTrackNumber += 2;
                    int keep = recrusionTrackNumber;
                    copyTraceBack(x, recrusionTrackNumber - 2);
                    copyTraceBack(x, recrusionTrackNumber - 1);
                    if (energy(i, j) != misMatch)
                    {
                        recrusionTrack[i + (recrusionTrackNumber - 2) * terminatorSequence.length()] = 1;
                        recrusionTrack[j + (recrusionTrackNumber - 2) * terminatorSequence.length()] = 1;
                    }
                    traceBackCal(i + 1, j - 1, keep - 2);
                    traceBackCal(i + 1, j, keep - 1);
                    break;
                }
            }
            else if (minObjectNumber == 4)
            {
                recrusionTrackNumber += 3;
                int keep = recrusionTrackNumber;
                copyTraceBack(x, recrusionTrackNumber - 3);
                copyTraceBack(x, recrusionTrackNumber - 2);
                copyTraceBack(x, recrusionTrackNumber - 1);
                if (energy(i, j) != misMatch)
                {
                    recrusionTrack[i + (recrusionTrackNumber - 3) * terminatorSequence.length()] = 1;
                    recrusionTrack[j + (recrusionTrackNumber - 3) * terminatorSequence.length()] = 1;
                }
                traceBackCal(i + 1, j - 1, keep - 3);
                traceBackCal(i + 1, j, keep - 2);
                traceBackCal(i, j - 1, keep - 1);
                break;
            }
        } while (j - i > 1);
    }



    //end tracebackCal!
    public static void traceBack(int i, int j)
    {
        recrusionTrack = new int[20 * terminatorSequence.length()];
        recrusionTrackNumber = 1;
        traceBackCal(i + 1, j - 1, 0);
        for (int k = 0; k < recrusionTrackNumber; k++)
        {
            recrusionTrack[k * terminatorSequence.length() + i] = 1;
            recrusionTrack[k * terminatorSequence.length() + j] = 1;
        }
    }



    public static void copyTraceBack(int sourcePosition, int destinationPositon)
    {
        for (int i = 0; i < terminatorSequence.length(); i++)
        {
            recrusionTrack[destinationPositon * terminatorSequence.length() + i] = recrusionTrack[sourcePosition * terminatorSequence.length() + i];

        }
    }
}
