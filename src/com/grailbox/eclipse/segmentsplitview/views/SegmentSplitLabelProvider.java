package com.grailbox.eclipse.segmentsplitview.views;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.grailbox.eclipse.segmentsplitview.Element;

public class SegmentSplitLabelProvider implements ITableLabelProvider {
  public void addListener(ILabelProviderListener arg0) {
  }

  public void dispose() {
  }

  public boolean isLabelProperty(Object arg0, String arg1) {
    return false;
  }

  public void removeListener(ILabelProviderListener arg0) {
  }

  public Image getColumnImage(Object arg0, int arg1) {
    return null;
  }

  public String getColumnText(Object object, int index) {
    String text = "";
    Element element = (Element) object;
    switch (index) {
    case 0:
      text = String.valueOf(element.getIndex());
      break;
    case 1:
      text = element.toString();
    }
    return text;
  }
}
