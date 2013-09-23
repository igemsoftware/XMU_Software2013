package ttec;

import java.util.ArrayList;

public class ProtentialTTail
{
	public static ArrayList<Integer> protentialTtailPositionArray;
    public static Integer[] protentialTtailPosition;
    public static boolean noTTailWarning;
    public static final int stemloopMinSize = 11;
    public static void protentialTtailCalculation(String terminatorSequence, int minTTailSequenceLength)
    {
        protentialTtailPositionArray = new ArrayList<Integer>();
        for (int i = stemloopMinSize; i <= terminatorSequence.length() - minTTailSequenceLength; i++)
        {
        	String temp = terminatorSequence.substring(0, i+terminatorSequence.length() - minTTailSequenceLength - i + 1);
            i = temp.indexOf("T", i);
//            ("T", i, terminatorSequence.length() - minTTailSequenceLength - i + 1);
            if (i != -1)
            {
                int TCount = 0;
                if (i <= terminatorSequence.length() - 8)
                {
                    for (int t = i; t <= i + 7; t++)
                    {
                        if (terminatorSequence.substring(t, t+1).equals("T"))
                        {
                            TCount += 1;
                        }
                    }
                    if (TCount >= 4)
                    {
                        protentialTtailPositionArray.add(i);
                    }
                }
                else if ((i > terminatorSequence.length() - 8) && (i <= terminatorSequence.length() - 4))
                {
                    for (int t = i; t <= terminatorSequence.length() - 1; t++)
                    {
                        if (terminatorSequence.substring(t, t+1).equals("T"))
                        {
                            TCount += 1;
                        }
                    }
                    if (TCount >= 0.5 * (terminatorSequence.length() - i))
                    {
                        protentialTtailPositionArray.add(i);
                    }
                }

            }
            else
            {
                break;
            }
        }
        for (int j = protentialTtailPositionArray.size() - 1; j >= 1; j--)
        {
            if ((int)protentialTtailPositionArray.get(j) == (int)protentialTtailPositionArray.get(j-1) + 1)
            {
                protentialTtailPositionArray.remove(j);
            }
        }
        if (protentialTtailPositionArray.size() != 0)
        {
            noTTailWarning = false;
            protentialTtailPosition = new Integer[protentialTtailPositionArray.toArray().length];
            for(int i = 0 ; i < protentialTtailPositionArray.toArray().length ; i++)
            {
            	protentialTtailPosition[i] = (Integer)protentialTtailPositionArray.toArray()[i];
            }
        }
        else
        {
            noTTailWarning = true;
            protentialTtailPosition = null;
        }
    }

    public static boolean getNoTTailWarning()
    {
        return noTTailWarning;
    }
}
