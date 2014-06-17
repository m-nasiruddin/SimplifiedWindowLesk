package lesk;


public class SimplifiedWindowLesk {
	public static void main(String argv[]) throws Exception {
		
		String configFile = "";
		double startTime;

		try {
			configFile = argv[0];
		} catch (Exception e) {
			System.err.println("usage :\n\tLeskFenetres fichierConfig.xml index_file");
			System.exit(1);
		}
		Context context = new Context(configFile);
		startTime = System.currentTimeMillis();
		context.analyse();
		System.out.println(context + "," + ((System.currentTimeMillis() - startTime) / 1000.0));
		context.writeResult();
	}
}