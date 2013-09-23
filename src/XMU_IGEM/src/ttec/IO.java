package ttec;

public class IO
{
	public static String Score;
    public static String efficiency;
    public static String structure;
    public static String terminatorSequence;
    public static String Warning;
    public static boolean Warn;

    public static void Algorithm_One(String inputsequence, boolean sbol, String path)
    {
        inputsequence = inputsequence.toUpperCase();
        AlgorithmOne.input(inputsequence, 1, 4, true, true, true, 1000, 1000, true, sbol, path, "");
        if (AlgorithmOne.Warn == false)
        {
            efficiency = String.format("%.1f", AlgorithmOne.terminatorEfficiency[0]);
            structure = AlgorithmOne.structure_string[0][0];
        }
        terminatorSequence = AlgorithmOne.terminatorSequence;
    }

    public static void Algorithm_Two(String inputsequence,boolean direction,boolean sbol,String path)
    {
        inputsequence = inputsequence.toUpperCase();
        
        AlgorithmOne.input(inputsequence, 1, 4, true, true, true, 1000, 1000, direction, sbol, path, "");
        Warn = AlgorithmOne.Warn;
        if (Warn == false)
        {
            AlgorithmTwo.Score(AlgorithmOne.aTail[0], AlgorithmOne.stemloop[0], AlgorithmOne.spacerSequence[0], AlgorithmOne.tTail[0]);
            Score = String.format("%.1f",AlgorithmTwo.dscore);
            efficiency = String.format("%.0f",AlgorithmTwo.terminatorefficiency);
            structure = AlgorithmTwo.Full_Structure;
        }
        else
        {
            Warning = "There is something wrong with your sequence!";
        }
        terminatorSequence = AlgorithmOne.terminatorSequence;
        System.out.println(terminatorSequence);
    }

    public static void Algorithm_Three(String inputsequence)
    {
        inputsequence = inputsequence.toUpperCase();
        AlgorithmOne.input(inputsequence, 1, 4, true, true, true, 1000, 1000, true, false, "", "");
        if (AlgorithmOne.Warn == false)
        {

        }
        else
        {
            ErrorDisplay.Warning = "Please input the correct sequence!";
        }
    }
}
