/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ms3test.csvimport.mahilum.model;

/**
 *
 * @author marrycute
 */
public class Columns {
    private String A;   // LastName
    private String B;   // FirstName
    private String C;   // Email
    private String D;   // Gender
    private String E;   // Base64 Image
    private String F;   // Bank Card
    private double G;   // Dollar Currency
    private boolean H;  // 
    private boolean I;  // Nullable Boolean
    private String J;   // Country
    
    public String getA() { return A; }
    public void setA(String A) { this.A = A; }    
    public String getB() { return B; }
    public void setB(String B) { this.B = B; }    
    public String getC() { return C; }
    public void setC(String C) { this.C = C; }    
    public String getD() { return D; }
    public void setD(String D) { this.D = D; }    
    public String getE() { return E; }
    public void setE(String E) { this.E = E; }    
    public String getF() { return F; }
    public void setF(String F) { this.F = F; }    
    public double getG() { return G; }
    public void setG(double G) { this.G = G; }    
    public boolean getH() { return H; }
    public void setH(boolean H) { this.H = H; }    
    public boolean getI() { return I; }
    public void setI(boolean I) { this.I = I; }    
    public String getJ() { return J; }
    public void setJ(String J) { this.J = J; }
    
    public void setAll(String A, String B, String C, String D, String E, String F, double G, boolean H, boolean I, String J){
        setA(A);
        setB(B);
        setC(C);
        setD(D);
        setE(E);
        setF(F);
        setG(G);
        setH(H);
        setI(I);
        setJ(J);
    }    
}
