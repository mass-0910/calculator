package calculator;

import java.util.ArrayList;

public class Form {

	String form;
	int pos;
	ArrayList<String> splittedForm = new ArrayList<String>();
	ArrayList<Num> entity = new ArrayList<Num>();
	Num entitynum;

    private class Num {

        private int intVal;
        private double realVal;
        private boolean isInt;

        Num(int val) {
            intVal = val;
            isInt = true;
        }

        Num(double val) {
            realVal = val;
            isInt = false;
        }

        Num(String val) {
            try {
                intVal = Integer.valueOf(val);
                isInt = true;
            } catch (NumberFormatException e) {
                realVal = Double.valueOf(val);
                isInt = false;
            }
        }

        public int getIntVal() {
            return intVal;
        }

        public double getDoubleVal() {
            if (isInt) {
                return intVal;
            } else {
                return realVal;
            }
        }

        public Num add(Num n) {
            if (this.isInt && n.isInt) {
                return new Num(this.getIntVal() + n.getIntVal());
            } else {
                return new Num(this.getDoubleVal() + n.getDoubleVal());
            }
        }

        public Num sub(Num n) {
            if (this.isInt && n.isInt) {
                return new Num(this.getIntVal() - n.getIntVal());
            } else {
                return new Num(this.getDoubleVal() - n.getDoubleVal());
            }
        }

        public Num mul(Num n) {
            if (this.isInt && n.isInt) {
                return new Num(this.getIntVal() * n.getIntVal());
            } else {
                return new Num(this.getDoubleVal() * n.getDoubleVal());
            }
        }

        public Num div(Num n) {
            if (this.isInt && n.isInt) {
                if (n.getIntVal() == 0 && this.getIntVal() > 0) return new Num(Double.POSITIVE_INFINITY);
                if (n.getIntVal() == 0 && this.getIntVal() == 0) return new Num(0);
                if (n.getIntVal() == 0 && this.getIntVal() < 0) return new Num(Double.NEGATIVE_INFINITY);
                if (this.getIntVal() % n.getIntVal() == 0) {
                    return new Num(this.getIntVal() / n.getIntVal());
                } else {
                    return new Num(this.getDoubleVal() / n.getDoubleVal());
                }
            } else {
                return new Num(this.getDoubleVal() / n.getDoubleVal());
            }
        }

        public Num mod(Num n) {
            if (this.isInt && n.isInt) {
                if (n.getIntVal() == 0) return new Num(Double.NaN);
                return new Num(this.getIntVal() % n.getIntVal());
            } else if (this.isInt && !n.isInt){
                return new Num(this.getIntVal() % n.getDoubleVal());
            } else if (!this.isInt && n.isInt) {
                return new Num(this.getDoubleVal() % n.getIntVal());
            } else {
                return new Num(this.getDoubleVal() % n.getDoubleVal());
            }
        }

        public Num sqrt() {
            if (isInt) {
                int[] mod256 = {
                    0, 1, 129, 4, 132, 9, 137, 16, 144, 17, 145, 25, 153, 33, 161, 36, 164, 169, 41,
                    49, 177, 185, 57, 64, 193, 65, 196, 68, 73, 201, 81, 209, 217, 89, 225, 97, 100,
                    228, 249, 105, 233, 113, 241, 121
                };
                for (int m : mod256) {
                    if (this.intVal % 256 == m) break;
                    if (m == 121) return new Num(Math.sqrt(intVal));
                }
                for (int i = 1; i * i <= intVal; i++) {
                    if (i * i == intVal) {
                        return new Num(i);
                    }
                }
                return new Num(Math.sqrt(intVal));
            } else {
                return new Num(Math.sqrt(realVal));
            }
        }

        public Num pow(Num e) {
            if (this.isInt && e.isInt() && e.getIntVal() > 0) {
                int ans = this.intVal;
                for (int i = 1; i < e.getIntVal(); i++) {
                    ans *= this.intVal;
                }
                return new Num(ans);
            } else {
                return new Num(Math.pow(this.getDoubleVal(), e.getDoubleVal()));
            }
        }

        public boolean isInt() {
            return isInt;
        }

        @Override
        public String toString() {
            if (isInt) {
                return String.valueOf(intVal);
            } else {
                return String.valueOf(realVal);
            }
        }

    }

	Form() {
		pos = 0;
	}

	public void init(String form) {
		this.form = form;
		pos = 0;
		splittedForm.clear();
		entity.clear();
		entitynum = new Num(0);
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
						entity.add(new Num(bag));
						break;
					}
					bag = pack(bag);
					if (!bag.matches("^[0-9]+[.]?[0-9]*$")) {
						bag = bag.substring(0, bag.length() - 1);
						pos--;
						splittedForm.add("number");
						entity.add(new Num(bag));
						bag = "";
						break;
					}
				}
			}
			if (bag.matches(operator)) {
				splittedForm.add(bag);
				entity.add(new Num(0));
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
		return expr().toString();
	}

	private Num expr() {
		System.out.println("expr");
		Num x = term();
		for (;;) {
			switch (peek()) {
			case "+":
				System.out.println("" + (pos - 1) + " -> " + pos);
				x = x.add(term());
				continue;
			case "-":
				System.out.println("" + (pos - 1) + " -> " + pos);
				x = x.sub(term());
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

	private Num term() {
		System.out.println(" term");
		Num x;
		switch (peek()) {
		case "-":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = number().mul(new Num(-1));
			break;
		case "+":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = number();
			break;
		case "sin":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.sin(number().getDoubleVal()));
			break;
		case "cos":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.cos(number().getDoubleVal()));
			break;
		case "tan":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.tan(number().getDoubleVal()));
			break;
		case "asin":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.asin(number().getDoubleVal()));
			break;
		case "acos":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.acos(number().getDoubleVal()));
			break;
		case "atan":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.atan(number().getDoubleVal()));
			break;
		case "abs":
			System.out.println(" " + (pos - 1) + " -> " + pos);
            Num n = number();
            if (n.isInt()) {
                x = new Num(Math.abs(n.getIntVal()));
            } else {
                x = new Num(Math.abs(n.getDoubleVal()));
            }
			break;
		case "deg":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.toDegrees(number().getDoubleVal()));
			break;
		case "rad":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.toRadians(number().getDoubleVal()));
			break;
		case "log":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			if (peek().equals("{")) {
				Num base = expr();
				peek();
				x = new Num(Math.log(number().getDoubleVal()) / Math.log(base.getDoubleVal()));
			} else {
				pos--;
				x = new Num(Math.log(number().getDoubleVal()));
			}
			break;
		case "sqrt":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = number().sqrt();
			break;
		case "cbrt":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.cbrt(number().getDoubleVal()));
			break;
		case "exp":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = new Num(Math.exp(number().getDoubleVal()));
			break;
		case "pow":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			if (peek().equals("{")) {
				Num pownum = expr();
				peek();
				x = number().pow(pownum);
			} else {
				pos--;
				x = number().pow(new Num(2));
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
			x = x.mul(term());
			break;
		case "/":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = x.div(term());
			break;
		case "%":
			System.out.println(" " + (pos - 1) + " -> " + pos);
			x = x.mod(term());
			break;
		default:
			System.out.println(" " + (pos - 1) + " -> " + pos);
			pos--;
			break;
		}
		System.out.println(" term : " + x);
		return x;
	}

	private Num number() {
		System.out.println("  number");
		Num x;
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
			x = new Num(Math.PI);
			break;
		case "E":
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = new Num(Math.E);
			break;
		default:
			System.out.println("  " + (pos - 1) + " -> " + pos);
			x = new Num(0);
		}
        switch (peek()) {
        case "^":
			System.out.println("  " + (pos - 1) + " -> " + pos);
            x = x.pow(number());
            break;
        case "!":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = new Num(factorial((int)x.getDoubleVal()));
            break;
        case "C":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = new Num(combination((int)x.getDoubleVal(), (int)number().getDoubleVal(), true));
            break;
        case "P":
            System.out.println("  " + (pos - 1) + " -> " + pos);
            x = new Num(permutation((int)x.getDoubleVal(), (int)number().getDoubleVal()));
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
            entitynum = new Num(0);
            return "";
        }
		String ret = splittedForm.get(pos);
		if (ret.equals("number")) {
			entitynum = entity.get(pos);
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