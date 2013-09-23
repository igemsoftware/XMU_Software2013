package ttec;

public class ErrorDisplay
{
	public static String Warning;
    public static String Help;
    public static void errorDisplayConstructor()
    {

        if (OverallManager.errorState == 1)
        {
            Warning = "Please enter the T tail sequence.";
            Help = "";
        }
        if (OverallManager.errorState == 2)
        {
            Warning = "No potential stem-loop sequence";
            Help = "";
        }
        if (OverallManager.errorState == 3)
        {
            Warning = "The sequence is too short";
            Help = "";
        }
        if (OverallManager.errorState == 4)
        {
            Warning = "The sequence is too long";
            Help = "";
        }
        if (OverallManager.errorState == 5)
        {
            Warning = "The sequence is too short";
            Help = "";
        }
        if (OverallManager.errorState == 6)
        {
            Warning = "Please enter a correct sequence";
            Help = "";
        }
        if (OverallManager.errorState == 0)
        {
            Warning = "";
            Help = "";
        }
    }
}
