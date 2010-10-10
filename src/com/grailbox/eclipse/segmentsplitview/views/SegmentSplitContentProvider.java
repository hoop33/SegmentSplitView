package com.grailbox.eclipse.segmentsplitview.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Label;

import com.grailbox.eclipse.segmentsplitview.Element;

public class SegmentSplitContentProvider implements IStructuredContentProvider {
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  public void dispose() {
  }

  public Object[] getElements(Object parent) {
    String text = ((Label) parent).getText();
    int len = text.indexOf('~');
    text = len == -1 ? text : text.substring(0, len);
    String[] strings = text.split("\\*");
    Element[] elements = new Element[strings.length];
    for (int i = 0, n = strings.length; i < n; i++) {
      elements[i] = new Element(i, strings[i]);
    }
    return elements;
  }
}
