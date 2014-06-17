package utils;

public class MyWordSense {
	
	private String ids;
	private Definition def;
	
	public MyWordSense() {
		ids = "";
		def = new Definition(1);
	}
	
	public String getIDS()            { return ids; }
	public void setIDS(String string) { this.ids = string; }

	public Definition getDef()        { return def; }
	public void setDef(String string) { def = new Definition(string); }
}