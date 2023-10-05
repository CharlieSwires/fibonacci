package fibonacci;

import java.math.BigInteger;
import java.time.LocalTime;

public class Fibonacci {
	public static void main(String[] args) {
		assert(calculateFibonacci(0)==0);
		assert(calculateFibonacciBI(0).equals(0));
		assert(calculateFibonacciI(0).equals(0));
		assert(calculateFibonacciITO(0,1L).equals(0));
		assert(calculateFibonacci(1)==1);
		assert(calculateFibonacciBI(1).equals(1));
		assert(calculateFibonacciI(1).equals(1));
		assert(calculateFibonacciITO(1,1L).equals(1));
		assert(calculateFibonacci(2)==1);
		assert(calculateFibonacciBI(2).equals(1));
		assert(calculateFibonacciI(2).equals(1));
		assert(calculateFibonacciITO(2,1L).equals(1));
		assert(calculateFibonacci(3)==2);
		assert(calculateFibonacciBI(3).equals(2));
		assert(calculateFibonacciI(3).equals(2));
		assert(calculateFibonacciITO(3,1L).equals(2));
		int n = 10; // Change this to the desired Fibonacci number you want to find
		long fibonacciNumber = calculateFibonacci(n);
		System.out.println("The " + n + "th Fibonacci number is: " + fibonacciNumber);
		n = 100; // Change this to the desired Fibonacci number you want to find
		BigInteger fibonacciNumberBI = calculateFibonacciBI(n);
		System.out.println("The " + n + "th Fibonacci number is: " + fibonacciNumberBI);
		n = 100; // Change this to the desired Fibonacci number you want to find
		fibonacciNumberBI = calculateFibonacciI(n);
		System.out.println("The " + n + "th Fibonacci number is: " + fibonacciNumberBI);
		n = 2368149; // Change this to the desired Fibonacci number you want to find
		fibonacciNumberBI = calculateFibonacciITO(n,1L*60L);
		System.out.println("The " + n + "th Fibonacci number is: " + fibonacciNumberBI);
	}

	public static long calculateFibonacci(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Input must be a non-negative integer.");
		}

		if (n <= 1) {
			return n; // Base cases: Fibonacci of 0 is 0, and Fibonacci of 1 is 1
		} else {
			return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
		}
	}

	public static BigInteger calculateFibonacciBI(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Input must be a non-negative integer.");
		}

		if (n <= 1) {
			return BigInteger.valueOf(n); // Base cases: Fibonacci of 0 is 0, and Fibonacci of 1 is 1
		} else {
			return recursiveFibonacci(BigInteger.ZERO, BigInteger.ONE, n);
		}
	}

	private static BigInteger recursiveFibonacci(BigInteger fibNMinus2, BigInteger fibNMinus1, int n) {
		if (n == 0) {
			return fibNMinus2;
		} else if (n == 1) {
			return fibNMinus1;
		} else {
			return recursiveFibonacci(fibNMinus1, fibNMinus2.add(fibNMinus1), n - 1);
		}
	}

	public static BigInteger calculateFibonacciI(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Input must be a non-negative integer.");
		}

		if (n <= 1) {
			return BigInteger.valueOf(n); // Base cases: Fibonacci of 0 is 0, and Fibonacci of 1 is 1
		} else {
			BigInteger fibNMinus2 = BigInteger.ZERO;
			BigInteger fibNMinus1 = BigInteger.ONE;
			BigInteger fibonacci = BigInteger.ZERO;

			for (int i = 2; i <= n; i++) {
				fibonacci = fibNMinus1.add(fibNMinus2);
				fibNMinus2 = fibNMinus1;
				fibNMinus1 = fibonacci;
			}

			return fibonacci;
		}
	}
	public static BigInteger calculateFibonacciITO(int n, long timeout) {
		if (n < 0) {
			throw new IllegalArgumentException("Input must be a non-negative integer.");
		}
		Fibonacci f = null;
		MyTimer t = null;
		f = new Fibonacci();
		t = f.new MyTimer();

		t.start();
		t.go(timeout);

		if (n <= 1) {
			t.iteration(n);			
			return BigInteger.valueOf(n); // Base cases: Fibonacci of 0 is 0, and Fibonacci of 1 is 1
		} else {
			BigInteger fibNMinus2 = BigInteger.ZERO;
			BigInteger fibNMinus1 = BigInteger.ONE;
			BigInteger fibonacci = BigInteger.ZERO;

			for (int i = 2; i <= n; i++) {
				t.iteration(i);
				fibonacci = fibNMinus1.add(fibNMinus2);
				fibNMinus2 = fibNMinus1;
				fibNMinus1 = fibonacci;
			}
			t.stopt();

			return fibonacci;
		}
	}
	
	class MyTimer extends Thread{
		boolean started = false;
		long s = 0;
		int i = 0;
		public void go(long seconds) {
			s = seconds;
			started = true;
		}
		public void iteration(int iter) {
			i = iter;
		}
		public void stopt() {
			started = false;
		}
		public boolean getStarted() {
			return started;
		}
		public void run() {
			while(!started) {
				try {
					MyTimer.sleep(s);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			LocalTime startTime = LocalTime.now();
			while(started) {
				try {
					if (LocalTime.now().minusSeconds(s).compareTo(startTime) == 1) {
						started = false;
						System.out.println("Took too long only "+s+" seconds allowed. It reached: "+i+" iterations.");
						System.exit(0);
					}
					MyTimer.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
