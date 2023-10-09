//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.alu;

public class ALU {
    public ALU() {
    }

    public String integerRepresentation(String number, int length) {
        StringBuilder result = new StringBuilder();
        String tmpNum;
        boolean isMinus;
        if (number.charAt(0) == '-') {
            isMinus = true;
            tmpNum = number.substring(1);
        } else {
            isMinus = false;
            tmpNum = number;
        }

        for(int n = Integer.parseInt(tmpNum); n >= 1; n = (n - n % 2) / 2) {
            result.insert(0, n % 2);
        }

        if (isMinus) {
            result = new StringBuilder(this.oneAdder(this.negation(result.toString())).substring(1, result.length() + 1));
        }

        while(result.length() < length) {
            if (isMinus) {
                result.insert(0, "1");
            } else {
                result.insert(0, "0");
            }
        }

        return result.toString();
    }

    public String floatRepresentation(String number, int eLength, int sLength) {
        StringBuilder result = new StringBuilder();
        String[] strs = number.split("\\.");
        int n;
        if (strs[0].charAt(0) != '-') {
            result.insert(0, "0");
            n = Integer.parseInt(strs[0]);
        } else {
            result.insert(0, "1");
            n = Integer.parseInt(strs[0].substring(1));
        }

        boolean isZero = true;
        String[] var8 = strs;
        int var9 = strs.length;

        int e;
        String exponent;
        for(e = 0; e < var9; ++e) {
            exponent = var8[e];
            if (Integer.parseInt(exponent) != 0) {
                isZero = false;
                break;
            }
        }

        if (isZero) {
            while(result.length() < 1 + eLength + sLength) {
                result.append("0");
            }

            return result.toString();
        } else {
            StringBuilder beforeDot = new StringBuilder();
            if (n != 0) {
                while(n >= 1) {
                    beforeDot.insert(0, n % 2);
                    n = (n - n % 2) / 2;
                }
            }

            StringBuilder afterDot = new StringBuilder();
            if (strs.length > 1) {
                float m = (float)Integer.parseInt(strs[1]) * (float)Math.pow(10.0, -strs[1].length());
                if (m == 0.0F) {
                    afterDot = new StringBuilder(this.allZeroWithLength(eLength + sLength + 1));
                } else {
                    do {
                        if ((m *= 2.0F) >= 1.0F) {
                            --m;
                            afterDot.append("1");
                        } else {
                            afterDot.append("0");
                        }
                    } while(m != 1.0F && beforeDot.length() + afterDot.length() <= eLength + sLength + 1 + 1);
                }
            }

            int bias = (int)Math.pow(2.0, eLength - 1) - 1;
            if (beforeDot.toString().equals("")) {
                e = this.normalize(afterDot.toString());
                if (bias - e < 0) {
                    System.out.println(bias - e);
                    System.out.println(afterDot);
                    afterDot = new StringBuilder(afterDot.substring(this.normalize(afterDot.toString()) - 1));

                    while(afterDot.length() < sLength) {
                        afterDot.append("0");
                    }

                    return result + this.allZeroWithLength(eLength) + afterDot;
                }

                exponent = this.integerRepresentation(bias - e + "", eLength);
            } else {
                e = beforeDot.length() - 1;
                exponent = this.integerRepresentation(bias + e + "", eLength);
            }

            if (strs.length > 1) {
                if (beforeDot.toString().equals("")) {
                    afterDot = new StringBuilder(this.leftShift(afterDot.toString(), e));
                    result.append(exponent).append(afterDot);
                } else {
                    result.append(exponent).append(beforeDot.substring(1)).append(afterDot);
                }
            } else {
                result.append(exponent).append(beforeDot.toString().equals("") ? "" : beforeDot.substring(1));
            }

            if (result.length() > 1 + eLength + sLength) {
                result = new StringBuilder(result.substring(0, 1 + eLength + sLength));
            }

            if (exponent.equals(this.allOneWithLength(exponent.length()))) {
                if (result.substring(1 + eLength, result.length()).equals(this.allZeroWithLength(1 + eLength + sLength))) {
                    return result.charAt(0) == '0' ? "+Inf" : "-Inf";
                } else {
                    return "NaN";
                }
            } else {
                return result.toString();
            }
        }
    }

    public String ieee754(String number, int length) {
        if (length == 32) {
            return this.floatRepresentation(number, 8, 23);
        } else {
            return length == 64 ? this.floatRepresentation(number, 11, 52) : "";
        }
    }

    public String integerTrueValue(String operand) {
        int num = 0;

        for(int i = 0; i < operand.length(); ++i) {
            if (i == 0) {
                num = (int)((double)num - (double)(operand.charAt(i) - 48) * Math.pow(2.0, operand.length() - 1));
            } else {
                num = (int)((double)num + (double)(operand.charAt(i) - 48) * Math.pow(2.0, operand.length() - 1 - i));
            }
        }

        return String.valueOf(num);
    }

    public String floatTrueValue(String operand, int eLength, int sLength) {
        boolean isMinus = operand.charAt(0) == '1';

        String exponent = operand.substring(1, 1 + eLength);
        String tailNum = operand.substring(1 + eLength);
        if (exponent.equals(this.allOneWithLength(eLength))) {
            if (tailNum.equals(this.allZeroWithLength(sLength))) {
                return operand.charAt(0) == '0' ? "+Inf" : "-Inf";
            } else {
                return "NaN";
            }
        } else {
            String bias = this.integerRepresentation(String.valueOf((int)Math.pow(2.0, eLength - 1) - 1), eLength);
            if (exponent.equals(this.allZeroWithLength(eLength))) {
                if (tailNum.equals(this.allZeroWithLength(sLength))) {
                    return "0";
                } else {
                    String exp = this.integerSubtraction(this.allZeroWithLength(eLength - 1) + "1", bias, eLength).substring(1);
                    tailNum = "0" + tailNum;
                    double result = 0.0;
                    int dotPos = tailNum.length();

                    for(int i = dotPos - 1; i >= 0; --i) {
                        result += Math.pow(2.0, -i) * (double)(tailNum.charAt(i) - 48);
                    }

                    result *= Math.pow(2.0, Integer.parseInt(this.integerTrueValue(exp)));
                    return isMinus ? "-" + result : String.valueOf(result);
                }
            } else {
                exponent = this.adder(exponent, this.negation(bias), '1', eLength).substring(1);
                tailNum = "1" + tailNum;
                int dotPos = 1;
                if (exponent.charAt(0) == '0') {
                    dotPos += Integer.parseInt(this.integerTrueValue(exponent));
                }

                float result;
                for(result = 0.0F; tailNum.length() < dotPos; tailNum = tailNum + "0") {
                }

                int i;
                for(i = dotPos - 1; i >= 0; --i) {
                    result += (float)(Math.pow(2.0, dotPos - 1 - i) * (double)(tailNum.charAt(i) - 48));
                }

                for(i = dotPos; i < sLength; ++i) {
                    result += (float)(Math.pow(2.0, dotPos - 1 - i) * (double)(tailNum.charAt(i) - 48));
                }

                return isMinus ? "-" + result : String.valueOf(result);
            }
        }
    }

    public String leftShift(String operand, int n) {
        StringBuilder result = new StringBuilder(operand.substring(n));

        while(result.length() < operand.length()) {
            result.append("0");
        }

        return result.toString();
    }

    public String negation(String operand) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < operand.length(); ++i) {
            if (operand.charAt(i) == '0') {
                result.append("1");
            } else {
                result.append("0");
            }
        }

        return result.toString();
    }

    public String oneAdder(String operand) {
        StringBuilder result = new StringBuilder();
        char ci = '1';

        for(int i = 0; i < operand.length(); ++i) {
            char si = this.xorGate(operand.charAt(operand.length() - i - 1), ci);
            result.insert(0, si);
            ci = this.andGate(ci, operand.charAt(operand.length() - i - 1));
        }

        if (ci == '1') {
            result.insert(0, "1");
        } else {
            result.insert(0, "0");
        }

        return result.toString();
    }

    public String adder(String operand1, String operand2, char c, int length) {
        StringBuilder tmpO1 = new StringBuilder(operand1);
        StringBuilder tmpO2 = new StringBuilder(operand2);

        while(tmpO1.length() < length) {
            if (tmpO1.charAt(0) == '1') {
                tmpO1.insert(0, "1");
            } else {
                tmpO1.insert(0, "0");
            }
        }

        while(tmpO2.length() < length) {
            if (tmpO2.charAt(0) == '1') {
                tmpO2.insert(0, "1");
            } else {
                tmpO2.insert(0, "0");
            }
        }

        StringBuilder result = new StringBuilder();
        char ci = c;
        int i = 0;

        do {
            String ts = this.claAdder(tmpO1.substring(length - i - 4, length - i), tmpO2.substring(length - i - 4, length - i), ci);
            result.insert(0, ts.substring(1, 5));
            ci = ts.charAt(0);
            i += 4;
        } while(i <= length - 4);

        while(result.length() < length) {
            if (result.charAt(0) == '1') {
                result.insert(0, "1");
            } else {
                result.insert(0, "0");
            }
        }

        boolean isOverflow = result.charAt(0) != tmpO1.charAt(0) && result.charAt(0) != tmpO2.charAt(0) && tmpO1.charAt(0) == tmpO2.charAt(0);

        if (isOverflow) {
            result.insert(0, "1");
        } else {
            result.insert(0, "0");
        }

        return result.toString();
    }

    public String fullAdder(char x, char y, char c) {
        char si = this.xorGate(this.xorGate(x, y), c);
        char ci = this.orGate(this.orGate(this.andGate(x, c), this.andGate(y, c)), this.andGate(x, y));
        return ci + "" + si;
    }

    public String claAdder(String operand1, String operand2, char c) {
        char[] p = new char[5];
        char[] g = new char[5];

        for(int i = 1; i <= 4; ++i) {
            p[i] = this.orGate(operand1.charAt(4 - i), operand2.charAt(4 - i));
            g[i] = this.andGate(operand1.charAt(4 - i), operand2.charAt(4 - i));
        }

        char[] ci = new char[]{c, this.orGate(g[1], this.andGate(p[1], c)), this.orGate(this.orGate(g[2], this.andGate(p[2], g[1])), this.andGate(c, this.andGate(p[2], p[1]))), this.orGate(this.orGate(this.orGate(g[3], this.andGate(p[3], g[2])), this.andGate(g[1], this.andGate(p[3], p[2]))), this.andGate(c, this.andGate(p[3], this.andGate(p[2], p[1])))), this.orGate(this.orGate(this.orGate(this.orGate(g[4], this.andGate(p[4], g[3])), this.andGate(g[2], this.andGate(p[4], p[3]))), this.andGate(g[1], this.andGate(p[4], this.andGate(p[3], p[2])))), this.andGate(c, this.andGate(p[4], this.andGate(p[3], this.andGate(p[2], p[1])))))};
        String result = "";

        for(int i = 1; i <= 4; ++i) {
            result = this.fullAdder(operand1.charAt(4 - i), operand2.charAt(4 - i), ci[i - 1]).charAt(1) + result;
        }

        return ci[4] + result;
    }

    public String integerSubtraction(String operand1, String operand2, int length) {
        return this.adder(operand1, this.negation(operand2), '1', length);
    }

    public char andGate(char a, char b) {
        return a == '1' && b == '1' ? '1' : '0';
    }

    public char xorGate(char a, char b) {
        return a - b == 0 ? '0' : '1';
    }

    private String allZeroWithLength(int n) {
        StringBuilder result = new StringBuilder();

        while(result.length() < n) {
            result.append("0");
        }

        return result.toString();
    }

    private String allOneWithLength(int n) {
        StringBuilder result = new StringBuilder();

        while(result.length() < n) {
            result.append("1");
        }

        return result.toString();
    }

    private char orGate(char a, char b) {
        return a != '1' && b != '1' ? '0' : '1';
    }

    public int normalize(String num) {
        int i = 0;

        for(char c = '0'; i < num.length(); ++i) {
            if (num.charAt(i) != c) {
                ++i;
                break;
            }

            c = num.charAt(i);
        }

        return i;
    }
}
