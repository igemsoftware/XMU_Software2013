package ttec;

import java.util.ArrayList;

public class Stemloop
{
	public static Integer[] hairpinScorePositionArray;
    public static Integer[] ttailPositionArray;

    public static boolean arraySizeWarning;
    public static boolean stemloopMinSizeWarning;
    public static final int stemloopMinSize = 11;
    public static ArrayList hairpinScore;
    public static ArrayList hairpinScorePosition;
    public static ArrayList ttailPosition;
    public static double[] tophairpinScore;
    public static int[] tophairpinScorePosition;
    public static int[] topttailPosition;


    public static void stemloopConstructor(String terminatorSequence, boolean spacer, int top, double upbounded)
    {
        hairpinScore = new ArrayList(0);
        hairpinScorePosition = new ArrayList(0);
        ttailPosition = new ArrayList(0);
        tophairpinScorePosition = new int[2 * top];
        topttailPosition = new int[top];
        tophairpinScore = new double[top];
        int stemloopMinSizeErrorState = 1;
        if (spacer == true)
        {
            for (int t = 0; t <= ProtentialTTail.protentialTtailPosition.length - 1; t++)   //须保证protentialTTailPosition不是空数组
            {
                if (ProtentialTTail.protentialTtailPosition[t] - 3 - stemloopMinSize + 1 >= 0)  //当所有的t都不能使得protentialTTailPosition[t]-3之前有至少stemloopMinSize个字符的时候警告
                {
                    stemloopMinSizeErrorState = 0;
                    for (int j = ProtentialTTail.protentialTtailPosition[t] - 1; j >= ProtentialTTail.protentialTtailPosition[t] - 3; j--)
                    {
                        boolean spacerNoT=true;
                        if (ProtentialTTail.protentialTtailPosition[t] - j == 3)
                        {
                            if (terminatorSequence.charAt(j+1) == 'T')
                            {
                                spacerNoT = false;
                            }
                        }
                        if (spacerNoT)
                        {
                            for (int i = 0; i <= j - stemloopMinSize - 1; i++)
                            {
                            	String temp = terminatorSequence.substring(0, i+ j - stemloopMinSize - i);
                                i = temp.indexOf(CalculationCore.complementaryResidue(terminatorSequence.substring(j, j+1)), i);
                                if (i != -1)
                                {
                                    double hairpinscoreToLength = CalculationCore.getMatrix(i, j) / (j - i + 1);
                                    hairpinScore.add(hairpinscoreToLength);//actually hairpinScore has only hairpinScoreToLengths
                                    hairpinScorePosition.add(i);
                                    hairpinScorePosition.add(j);
                                    ttailPosition.add(ProtentialTTail.protentialTtailPosition[t]);
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            hairpinScorePositionArray = new Integer[hairpinScorePosition.size()];
            ttailPositionArray = new Integer[ttailPosition.size()];
            if (stemloopMinSizeErrorState == 0)
            {
                stemloopMinSizeWarning = false;

//                hairpinScorePositionArray = (Integer[])hairpinScorePosition.toArray();
                for(int i = 0 ; i < hairpinScorePosition.toArray().length ; i++)
                {
                	hairpinScorePositionArray[i] = (Integer)hairpinScorePosition.toArray()[i];
                }

//                ttailPositionArray = (Integer[])ttailPosition.toArray();
                
                for(int i = 0 ; i < ttailPosition.toArray().length ; i++)
                {
                	ttailPositionArray[i] = (Integer)ttailPosition.toArray()[i];
                }
                Double[] arr = new Double[hairpinScore.toArray().length];
                for(int i = 0 ; i < hairpinScore.toArray().length ; i++)
                {
                	arr[i] = (Double)hairpinScore.toArray()[i];
                }
                ArrayManipulation.arrayManipulationConstructor(arr, top, upbounded);
                
                arraySizeWarning = ArrayManipulation.getArraySizeWarning();
                
                if (arraySizeWarning == false)
                {
                    tophairpinScore = ArrayManipulation.sortedArray;
                    for (int i = 0; i <= top - 1; i++)
                    {
                        tophairpinScorePosition[2 * i] = hairpinScorePositionArray[2 * (ArrayManipulation.sortedArrayPosition[i])];
                        tophairpinScorePosition[2 * i + 1] = hairpinScorePositionArray[2 * (ArrayManipulation.sortedArrayPosition[i]) + 1];
                        topttailPosition[i] = ttailPositionArray[ArrayManipulation.sortedArrayPosition[i]];
                    }
                }
            }
            else
            {
                stemloopMinSizeWarning = true;
            }
        }










        else
        {
            for (int t = 0; t <= ProtentialTTail.protentialTtailPosition.length - 1; t++)   //须保证protentialTTailPosition不是空数组
            {
                if (ProtentialTTail.protentialTtailPosition[t] - 1 - stemloopMinSize + 1 >= 0)  //当所有的t都不能使得protentialTTailPosition[t]-3之前有至少stemloopMinSize个字符的时候警告
                {
                    stemloopMinSizeErrorState = 0;
                    for (int j = ProtentialTTail.protentialTtailPosition[t] - 1; j >= ProtentialTTail.protentialTtailPosition[t] - 3; j--)
                    {
                            for (int i = 0; i <= j - stemloopMinSize - 1; i++)
                            {
                            	String temp1 = terminatorSequence.substring(0, i+ j - stemloopMinSize - i);
                                i = temp1.indexOf(CalculationCore.complementaryResidue(terminatorSequence.substring(j, j+1)), i);
                                if (i != -1)
                                {
                                    double hairpinscoreToLength = CalculationCore.getMatrix(i, j) / (j - i + 1);
                                    hairpinScore.add(hairpinscoreToLength);//actually hairpinScore has only hairpinScoreToLengths
                                    hairpinScorePosition.add(i);
                                    hairpinScorePosition.add(j);
                                    ttailPosition.add(ProtentialTTail.protentialTtailPosition[t]);
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                }
            }
            hairpinScorePositionArray = new Integer[hairpinScorePosition.size()];
            ttailPositionArray = new Integer[ttailPosition.size()];
            if (stemloopMinSizeErrorState == 0)
            {
                stemloopMinSizeWarning = false;

                Integer[] temp = new Integer[hairpinScorePosition.toArray().length]; 
                for(int i = 0 ; i < hairpinScorePosition.toArray().length ; i++)
                {
                	temp[i] = (Integer) hairpinScorePosition.toArray()[i];
                }
                hairpinScorePositionArray = temp;

//                ttailPositionArray = (Integer[])ttailPosition.toArray();
                Integer[] temp1 = new Integer[ttailPosition.toArray().length]; 
                for(int i = 0 ; i < ttailPosition.toArray().length ; i++)
                {
                	temp1[i] = (Integer) ttailPosition.toArray()[i];
                }
                ttailPositionArray = temp1;
                
                Double[] temp2 = new Double[hairpinScore.toArray().length];
                for(int i = 0 ; i < hairpinScore.toArray().length ; i++)
                {
                	temp2[i] = (Double)hairpinScore.toArray()[i];
                }
                ArrayManipulation.arrayManipulationConstructor(temp2, top, upbounded);
                arraySizeWarning = ArrayManipulation.getArraySizeWarning();
                if (arraySizeWarning == false)
                {


                    tophairpinScore = ArrayManipulation.sortedArray;
                    for (int i = 0; i <= top - 1; i++)
                    {
                        tophairpinScorePosition[2 * i] = hairpinScorePositionArray[2 * (ArrayManipulation.sortedArrayPosition[i])];
                        tophairpinScorePosition[2 * i + 1] = hairpinScorePositionArray[2 * (ArrayManipulation.sortedArrayPosition[i]) + 1];
                        topttailPosition[i] = ttailPositionArray[ArrayManipulation.sortedArrayPosition[i]];
                    }
                }
            }
            else
            {
                stemloopMinSizeWarning = true;
            }

        }
    }

    public static boolean getStemLoopMinSizeWarning()
    {
        return stemloopMinSizeWarning;
    }

    public static boolean getArraySizeWarning()
    {
        return arraySizeWarning;
    }
}
