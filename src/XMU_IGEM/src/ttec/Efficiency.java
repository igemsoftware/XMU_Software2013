package ttec;

public class Efficiency
{
	public static double dscore;
    public static double terminatorEfficiency;
    public static void dscoreCal(double ttailscore, double hairpinscore,int LH)
    {
        dscore = 0;
        dscore = ttailscore * 18.16 - hairpinscore * 96.59 / LH - 116.87;
    }
    public static double terminatorEfficiencyCal(double ttailscore, double hairpinscore,int LH)
    {
        terminatorEfficiency = 0;
        dscoreCal(ttailscore, hairpinscore,LH);
        if (dscore < -6.96)
        {

            terminatorEfficiency = 0;
        }
        if ((dscore >= -6.96) && (dscore < 11.30))
        {
            terminatorEfficiency = (2.61 * dscore + 15.68) / 100;
        }
        if ((dscore >= 11.30) && (dscore < 16.09))
        {
            terminatorEfficiency = (2.51 * dscore + 17.65) / 100;
        }
        if ((dscore >= 16.09) && (dscore < 19.42))
        {
            terminatorEfficiency = (2.34 * dscore + 20.16) / 100;
        }
        if ((dscore >= 19.42) && (dscore < 23.77))
        {
            terminatorEfficiency = (1.92 * dscore + 29.17) / 100;
        }
        if ((dscore >= 23.77) && (dscore < 29.42))
        {
            terminatorEfficiency = (-0.0788 * dscore * dscore + 5.6087 * dscore - 16.7805) / 100;
        }
        if ((dscore >= 29.42) && (dscore < 34.35))
        {
            terminatorEfficiency = (-0.3693 * dscore * dscore + 24.5650 * dscore - 323.0521) / 100;
        }
        if ((dscore >= 34.35) && (dscore < 50))
        {
            terminatorEfficiency = (-0.0219 * dscore * dscore + 2.1631 * dscore + 36.4871) / 100;
        }

        if (dscore >= 50)
        {
            terminatorEfficiency = 0.9;
        }
        return terminatorEfficiency;

    }
}
