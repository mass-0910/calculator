package calculator;

import java.util.ArrayList;

public class Form {

	String form;
	int pos;
	ArrayList<String> splittedForm = new ArrayList<String>();
	ArrayList<Double> entity = new ArrayList<Double>();
	double entitynum = 0;

	Form() {
		pos = 0;
	}

	public void init(String form) {
		this.form = form;
		pos = 0;
		splittedForm.clear();
		entity.clear();
		entitynum = 0.0;
	}

	public void split() {
		String bag = "";
		String operator = "\\+|-|\\*|/|\\(|\\)|%|\\^|!|C|P|sin|cos|tan|abs|log|sqrt|cbrt|pow|exp|asin|acos|atan|deg|rad|PI|E|\\{|\\}";
		while (pos < form.length()) {
			space();
			bag = pack(bag);
			if (bag.matches("^[0-9]+$")) {
				for (;;) {
					if (pos == form.length()) {
						splittedForm.add("number");
						entity.add(Double.valueOf(bag));
						break;
					}
					bag = pack(bag);
					if (!bag.matches("^[0-9]+[.]?[0-9]*$")) {
						bag = bag.substring(0, bag.length() - 1);
						pos--;
						splittedForm.add("number");
						entity.add(Double.valueOf(bag));
						bag = "";
						break;
					}
				}
			}
			if (bag.matches(operator)) {
				splittedForm.add(bag);
				entity.add(Double.valueOf(0));
				bag = "";
			}
		}
	}

	private void space() {
		while (form.charAt(pos) == ' ') {
			if (pos == form.length() - 1) {
				break;
			} else {
				pos++;
			}
		}
	}

	private String pack(String bag) {
		bag += String.valueOf(form.charAt(pos));
		pos++;
		return bag;
	}

	public String calc() {
		pos = 0;
		return String.valueOf(expr());
	}

	private double expr() {
		System.out.println("expr");
		double x = term();
		for (;;) {
			switch (peek()) {
			case "+":
				System.out.println("" + (pos - 1) + " -> " + pos);
				x += term();
				continue;
			case "-":
				System.out.println("" + (pos - 1) + " -> " + pos);
				x -= term();
				continue;
			default:
				System.out.println("" + (pos - 1) + " -> " + pos);
			}
			pos--;
			break;
		}
		System.out.println("expr : " + x);
		return x;
	}

	private double term() {
		System.out.println(" term");
		Double x;
		switch (peek()) {
		case "-":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = -number();
			break;
		case "+":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = number();
			break;
		case "sin":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.sin(number());
			break;
		case "cos":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.cos(number());
			break;
		case "tan":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.tan(number());
			break;
		case "asin":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.asin(number());
			break;
		case "acos":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.acos(number());
			break;
		case "atan":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.atan(number());
			break;
		case "abs":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.abs(number());
			break;
		case "deg":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.toDegrees(number());
			break;
		case "rad":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.toRadians(number());
			break;
		case "log":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			if (peek().equals("{")) {
				Double base = expr();
				peek();
				x = Math.log(number()) / Math.log(base);
			} else {
				pos--;
				x = Math.log(number());
			}
			break;
		case "sqrt":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.sqrt(number());
			break;
		case "cbrt":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.cbrt(number());
			break;
		case "exp":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = Math.exp(number());
			break;
		case "pow":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			if (peek().equals("{")) {
				Double pownum = expr();
				peek();
				x = Math.pow(number(), pownum);
			} else {
				pos--;
				x = Math.pow(number(), 2);
			}
			break;
		default:
			System.out.println(" " + (pos - 1) + " -> " + pos);
			pos--;
			System.out.println(" " + (pos + 1) + " -> " + pos);
			x = number();
			break;
		}
		switch (peek()) {
		case "*":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x *= term();
			break;
		case "/":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x /= term();
			break;
		case "%":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x %= term();
			break;
		default:
			System.out.println(" " + (pos - 1) + " -> " + pos);
			pos--;
			break;
		}
		System.out.println(" term : " + x);
		return x;
	}

	private double number() {
		System.out.println("  number");
		Double x;
		switch (peek()) {
		case "(":
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = expr();
			peek();
			break;
		case "number":
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = entitynum;
			break;
		case "PI":
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = Math.PI;
			break;
		case "E":
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = Math.E;
			break;
		default:
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = 0.0;
		}
        switch (peek()) {
        case "^":
			System.out.println("  " + (pos - 1) + " -> " + pos);
            x = Math.pow(x, number());
            break;
        case "!":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = factorial((int)x.doubleValue());
            break;
        case "C":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = Double.valueOf(combination((int)x.doubleValue(), (int)number(), true));
            break;
        case "P":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = permutation((int)x.doubleValue(), (int)number());
            break;
        default:
            System.out.println("  " + (pos - 1) + " -> " + pos);
            pos--;
        }
		System.out.println("  number : " + x);
		return x;
	}

	private String peek() {
        if (pos >= splittedForm.size()) {
            pos++;
            entitynum = 0.0;
            return "";
        }
		String ret = splittedForm.get(pos);
		if (ret.equals("number")) {
			entitynum = entity.get(pos).doubleValue();
		}
		pos++;
		return ret;
	}

	public void splitOut() {
		for (int i = 0; i < splittedForm.size(); i++) {
			System.out.print(splittedForm.get(i) + ",");
		}
		System.out.println();
		for (int i = 0; i < splittedForm.size(); i++) {
			System.out.print(entity.get(i) + ",");
		}
		System.out.println();
	}

    private double factorial(int n) {
        if (n < 0) return 0;
        if (n == 0) return 1;
        double ans = n;
        for(int t = n - 1; t > 0; t--){
            ans *= t;
        }
        return ans;
    }

    private double permutation(int n, int r) {
        if (n < 0 || r < 0) return 0;
        if (n == 0) return 1;
        if (n < r) return 0;
        double ans = n;
        for(int t = n - 1; t > n - r; t--){
            ans *= t;
        }
        return ans;
    }

    static Integer[][] dp;
    private static int combination(int n, int r, boolean init) {
        if (r == 0) {
            return 1;
        }
        if (init) {
            dp = new Integer[n + 1][r + 1];
            for (int i = 0; i <= n; i++) {
                dp[i][1] = i;
            }
            for (int i = 0; i <= r; i++) {
                dp[0][i] = 0;
            }
            if (r > n / 2) {
                r = n - r;
            }
        }
        return getInt(n - 1, r) + getInt(n - 1, r - 1);
    }

    private static int getInt(int n, int r) {
        if (n < 0 || r < 0) {
            return 0;
        }
        if (dp[n][r] != null) {
            return dp[n][r].intValue();
        }
        return combination(n, r, false);
    }

}