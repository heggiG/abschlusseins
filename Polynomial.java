package assignement2.math;

public class Polynomial {
	
	private int[] coefficients;
	
	Polynomial(int[] coefficients) {
		setCoefficients(coefficients);
	}
	
	public double evaluate(double x) {
		double ans = 0;
		for(int i = 0; i < coefficients.length; i++) {
			ans += (coefficients[i] * (i + 1)) * Math.pow(x, i);
		}
		return ans;
	}
	
	public Polynomial add(Polynomial q) {
		int[] arr;
		if(this.coefficients.length >= q.getCoefficients().length) {
			arr = new int[this.coefficients.length];
		} else {
			arr = new int[q.getCoefficients().length];
		}
		for(int i = 0; i < arr.length; i++) {
			if(i < this.coefficients.length) {
				arr[i] += this.coefficients[i];
			}
			if(i < q.getCoefficients().length) {
				arr[i] += q.getCoefficients()[i]; 
			}
			
		}
		return new Polynomial(arr);
	}

	public Polynomial getDerivative() {
		int[] deriv = new int[this.coefficients.length - 1];
		for(int i = 0; i < deriv.length; i++) {
			deriv[i] = this.coefficients[i + 1] * (i + 1);
		}
		return new Polynomial(deriv);
	}
	
	public void setCoefficients(int[] polynomial) {
		this.coefficients = polynomial;
	}
	
	public int[] getCoefficients() {
		return this.coefficients;
	}

	/*
	 * toString wird überschrieben um ausgabe in die Konsole zu erleichtern
	 * Ausgabe der Koeffizienten mit Leerzeichen um lesbarkeit zu verbessern
	 */
	@Override
	public String toString() {
		String a = "";
		for(int i = 0; i < this.coefficients.length; i++) {
			a += this.coefficients[i];
			a += " ";
		}
		return a;
	}
}
