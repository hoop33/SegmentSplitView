package com.grailbox.eclipse.segmentsplitview;

public class Element {
  private int index;
  private String text;
  
  public Element(int index, String text) {
    this.index = index;
    this.text = text;
  }
  
  public int getIndex() {
    return index;
  }
  
  public String toString() {
    return text;
  }
}
