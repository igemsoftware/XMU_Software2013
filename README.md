We are XMU_Software 2013, a team aiming at scientific exploration. We are going to compete with software teams all over the globe for the 2013 iGEM awards.


The IDE we used is eclipse. The function of our software includes the prediction of promoter motifs, promoter strength, SD sequence in RBS sequence, and RBS strength, primer design, protein coding sequence optimization. We also integrated the program made by SUSTC Shenzhen-B: Transcriptional Terminator Efficiency Calculator (TTEC). And our software supports the SBOL language.

Specifically, Brick Worker can be separated into four parts:
1. Promoter Decoder includes the prediction of type and location of TFBS, which can be further divided into sigma factor binding sites and other TFBS, prediction of promoter strength and primer design.
2. RBS-Decoder can locate the SD sequence and measure the strength of RBS.
3. Synoproteiner can optimize protein coding sequence using two methods, both include GA. The first one is recommended and the second is designed for test. It can provide the static comparation between the original sequence and the optimized sequence.
4. Terminator-Decoder is adapted from TTEC, an excellent program originally designed by SUSTC Shenzhen-B 2012, thanks SUSTC Shenzhen-B for the permission to adapt their source code.

The source code introduction:
The SC is in the file "src/XMU_IGEM", you can import it into eclipse or go to /src/XMU_IGEM/src for independent compiling, the main class is "frame/MainFrame". But this program uses the source file in XMU_IGEM and you need to copy all files except src to the current file. We also use Derby data bank and SBOL data bank, which can be found in lib file.


Program structure
codon: sigma factors algorithm is in the class "SeqSimilarity" and the others is in the class "NotSigmaSimilarity". Primer design is in Primer.
frame: the source file of interface.
protein: the first algorithm of protein optimization is in the class "Algorithm" and the second one is under the class "Algorithm2".


Program running
You can run the process called ¡°runThis.bat¡± under "software(windows)" after you installing JDK1.7.0 and setting the path of JAVA_HOME.

If you have any question, connect us freely!
http://2013.igem.org/Team:XMU_Software#