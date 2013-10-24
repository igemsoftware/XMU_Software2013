package frame;

public class TransferExe {
	public static void main(String[] args) {
		  new TransferExe();
		  }
		  
	public TransferExe()
	{
		openExe();
	}
		  //调用其他的可执行文件，例如：自己制作的exe，或是 下载 安装的软件.
		  public static void openExe() {
		  Runtime rn = Runtime.getRuntime();
		  Process p = null;
		  try {
			  p = rn.exec("./ttec_sustc/TTEC.exe");
		  } catch (Exception e) {
//		  System.out.println("Error exec!");
		  }
		  }
}
