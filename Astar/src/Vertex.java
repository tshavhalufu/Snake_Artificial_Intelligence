import javafx.util.Pair;

import java.util.LinkedList;

public class Vertex implements Comparable<Vertex> {

    int yValue;
    int xValue;
    Vertex parent ;
    //distance from start
    int GCost;
    // distance from End
    int HCost;
    //GCost + HCost
    int FCost;
    boolean inTree = false;

    Vertex(int xValue,int yValue){
        this.xValue = xValue;
        this.yValue = yValue;

    }
    //    public void  addAdjacency(Vertex v){
//        adjacencies.add(v);
//    }
//    public LinkedList<Vertex> getAdjacencies(){
//        return adjacencies;
//    }
    public void setParent(Vertex parentValue){
        this.parent = parentValue;
    }
    public Vertex getParent(){
        return this.parent;
    }
    public void setInTree(boolean inTree){
        this.inTree = inTree;
    }
    public boolean getInTree(){
        return this.inTree;
    }
    public void setGCost(int G){
        this.GCost = G;
    }
    public int getGCost(){
       return this.GCost;
    }
    public void setHCost(int H){
        this.HCost = H;
    }
    public int getHCost(){
        return this.HCost;
    }
//    public void setFCost(int F){
//        this.FCost = F;
//    }
    public int getFCost(){
        return (this.getGCost() + this.getHCost());
    }

    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex) obj;
        return this.xValue==v.xValue && this.yValue==v.yValue;
    }

    @Override
    public String toString() {
        return xValue +","+yValue;
    }

    @Override
    public int compareTo(Vertex o){
        if(this.getFCost() == o.getFCost()){
            if(this.getGCost() == o.getGCost()){
                if(this.getHCost() < o.getHCost()){
                    return -1;
                }
                else if(this.getHCost() > o.getHCost()){
                    return 1;
                }
            }
            else if(this.getGCost() < o.getGCost()){
                return  -1;
            }
            else if(this.getGCost() > o.getGCost()){
                return 1;
            }
        }
        else if(this.getFCost() < o.getFCost()){
            return -1;
        }
        else if(this.getFCost() > o.getFCost()){
            return 1;
        }
        return 0;
    }

//    public boolean MSmall(Vertex Start,Vertex End){
//        boolean results = false;
//
//        if(Start.MCost > End.MCost){
//            results = true;
//        }
//        if(Start.MCost == End.MCost && (Start.GCost > End.GCost)){
//            results = true;
//        }
//     return results;
//    }

}