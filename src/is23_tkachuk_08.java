import java.io.*;
import java.util.Comparator;
import java.util.TreeSet;

public class is23_tkachuk_08 {

    private int w;
    private int n;
    private int[] ws;
    private int[] cs;


    public is23_tkachuk_08(int w, int n, int[] ws, int[] cs) {
        this.w = w;
        this.n = n;
        this.ws = ws;
        this.cs = cs;
    }

    public static void main(String[] args) throws IOException {

        //Long time = System.currentTimeMillis();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if (args.length > 0) {
            in = new BufferedReader(new FileReader(new File(args[0])));
        }

        String[] line = in.readLine().split(" ");
        int w = Integer.parseInt(line[0]);
        int n = Integer.parseInt(line[1]);

        int[] ws = new int[n];
        int[] cs = new int[n];

        for (int i = 0; i < n; i++) {
            line = in.readLine().split(" ");
            cs[i] = Integer.parseInt(line[0]);
            ws[i] = Integer.parseInt(line[1]);
        }
        in.close();

        String ans = new is23_tkachuk_08(w, n, ws, cs).solve();
        System.out.println(ans);
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("is23_tkachuk_08_output.txt")));
        out.write(ans);
        out.close();

        //Long time1 = System.currentTimeMillis();
        //System.out.println((time1 - time) / 1e3);
    }

    private String solve() {
        int[] max = new int[w + 1];
        for (int i = 1; i < max.length; i++) {
            max[i] = -1;
        }

// for 500/2000_000 test: 0.22 sec
        TreeSet<Integer> t = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? 1 : o1 > o2 ? -1 : 0;
            }
        });
        TreeSet<Integer> buf = (TreeSet<Integer>) t.clone();

        t.add(0);

        for (int i = 0; i < n; i++) {
            buf.clear();
            for (Integer q : t) {
                if (q + ws[i] <= w) {
                    if (max[q + ws[i]] == -1)
                        buf.add(q + ws[i]);
                    max[q + ws[i]] = Math.max(max[q + ws[i]], max[q] + cs[i]);
                }
            }
            t.addAll(buf);
        }

// for 500/2000_000 test: 2.2 sec

//        for (int i = 0; i < n; i++) {
//            for (int j = w; j >= ws[i]; j--) {
//                if (max[j - ws[i]] >= 0)
//                    max[j] = Math.max(max[j], max[j - ws[i]] + cs[i]);
//            }
//        }

        int maxx = max[w];
        for (int i = 0; i < w; i++)
            maxx = Math.max(maxx, max[i]);
        return maxx + "";
    }
}
