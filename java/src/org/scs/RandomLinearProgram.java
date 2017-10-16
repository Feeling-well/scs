package org.scs;

public class RandomLinearProgram extends ScsConeProgram {
    private double opt;

    public RandomLinearProgram(int m, int n, ScsData d, ScsSettings p, IScsConeSolver solver) {
        this.p = p;
        this.d = d;
        this.solver = solver;
        k = new ScsCone();
        k.setL(m);
        double[] z = Utils.generateRandomDoubleArray(m);
        double[] s = new double[m];
        double[] y = new double[m];
        for (int i = 0; i < m; i++) {
            s[i] = Math.max(z[i], 0);
            y[i] = s[i] - z[i];
        }

        ScsMatrix A = ScsMatrix.generateRandomMatrix(m, n);
        double[] x = Utils.generateRandomDoubleArray(n);
        double[] b = new double[m];
        A.accumByA(x, b);
        Utils.addScaledArray(b, s, 1);

        double[] c = new double[n];
        A.accumByAtrans(y, c);
        Utils.scaleArray(c, -1);
        d.setA(A);
        d.setB(b);
        d.setC(c);

        opt = Utils.ip(x, c);
    }

    public double getOpt() {
        return opt;
    }

    public void setSolver(IScsConeSolver solver) {
        this.solver = solver;
    }
}
