package RD;

public class Rec {

	public double RecDados (double F1, double F2, double F3, double F4,
							double S1, double S2, double S3, double S4) {
	//CASO ADOTADO PARA ESTA QUESTÃO (F3 REPRESENTA A VELOCIDADE DO TIRO)
		//   F1     F3     F4
		// =====>O=====>O=====>
		//       |  F2  |
		//        ======

		double[] y = new double[] { F1, F2, F3, F4 };

		double[] v = new double[] { S1, S2, S3, S4 };

		double[][] A = new double[][] { { 1, -1, -1, 0 },
										{ 1, 0, 0, -1 }};

		Reconciliation rec = new Reconciliation(y, v, A);
		double[] _m = rec.getReconciledFlow();
		return _m[2];// Posicao 2 => F3 apos execucao da RD
	}

}
