package function;

public class Function {


    public Function()
    {}

    public static Double calculateFunction(Double x,Double y)
    {
        Double exp1=Math.exp(-(Math.pow(x,2)+Math.pow((y+1),2)));   //e^(−(x^2+(y+1)^2))
        Double exp2=Math.exp(-(Math.pow(x,2)+Math.pow(y,2)));       //e^(−(x^2+y^2))
        Double exp3=Math.exp(-(Math.pow((x+2),2)+Math.pow(y,2)));   //e^(−((x+2)^2+y^2))
        Double res = 3*Math.pow((1-x),2)*exp1-7*(x/5-Math.pow(x,3)-Math.pow(y,5))*exp2-1/3*exp3;
                    //3*(1-x)^2*exp1-7*(x/5 − x^3 − y^5 )*exp2-1/3*exp3
        return res;
    }

    public static Double calculateFunctionPlane(Double y)
    {
        return calculateFunction(0.0,y);
    }
}



//3(1 −x)^2*e^(−(x^ 2+(y+1)^2))− 7( x/5 − x^3 − y^5 )e^(−(x^2+y^2))−1/3*e^(−((x+2)^2+y^2 ))

//3*(1-x)^2*exp- 7( x/5 − x^3 − y^5 )*exp2-1/3*exp3