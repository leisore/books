 5 down vote
	

I ran into a similar problem in Linux, except it was "ps -ef | grep someprocess".
At least with "ls" you have a language-independent (albeit slower) Java replacement. Eg.:

File f = new File("C:\\");
String[] files = f.listFiles(new File("/home/tihamer"));
for (String file : files) {
    if (file.matches(.*some.*)) { System.out.println(file); }
}

With "ps", it's a bit harder, because Java doesn't seem to have an API for it.

I've heard that Sigar might be able to help us: https://support.hyperic.com/display/SIGAR/Home

The simplest solution, however, (as pointed out by Kaj) is to execute the piped command as a string array. Here is the full code:

try {
    String line;
    String[] cmd = { "/bin/sh", "-c", "ps -ef | grep export" };
    Process p = Runtime.getRuntime().exec(cmd);
    BufferedReader in =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((line = in.readLine()) != null) {
        System.out.println(line); 
    }
    in.close();
} catch (Exception ex) {
    ex.printStackTrace();
}

As to why the String array works with pipe, while a single string does not... it's one of the mysteries of the universe (especially if you haven't read the source code). I suspect that it's because when exec is given a single string, it parses it first (in a way that we don't like). In contrast, when exec is given a string array, it simply passes it on to the operating system without parsing it.

Actually, if we take time out of busy day and look at the source code (at http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/lang/Runtime.java#Runtime.exec%28java.lang.String%2Cjava.lang.String[]%2Cjava.io.File%29), we find that is exactly what is happening:

public Process  [More ...] exec(String command, String[] envp, File dir) 
          throws IOException {
    if (command.length() == 0)
        throw new IllegalArgumentException("Empty command");
    StringTokenizer st = new StringTokenizer(command);
    String[] cmdarray = new String[st.countTokens()];
    for (int i = 0; st.hasMoreTokens(); i++)
        cmdarray[i] = st.nextToken();
    return exec(cmdarray, envp, dir);
}

netstat -anp
Not all processes could be identified