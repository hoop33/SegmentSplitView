package com.grailbox.eclipse.segmentsplitview.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

/**
 * This view breaks an X12 segment into its parts, so you don't have to count
 * them.
 */
public class SegmentSplitView extends ViewPart {

  /**
   * The ID of the view as specified by the extension.
   */
  public static final String ID = "com.grailbox.eclipse.segmentsplitview.views.SegmentSplitView";

  private Label segmentText;
  private TableViewer viewer;
  private Clipboard clipboard;

  /**
   * The constructor.
   */
  public SegmentSplitView() {
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  public void createPartControl(Composite parent) {
    GridLayout layout = new GridLayout(1, true);
    parent.setLayout(layout);

    Composite segment = new Composite(parent, SWT.NONE);
    segment.setLayout(new GridLayout(2, false));
    segment.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    new Label(segment, SWT.LEFT).setText("Segment:");
    segmentText = new Label(segment, SWT.LEFT);
    segmentText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

    viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
        | SWT.BORDER | SWT.FULL_SELECTION);
    viewer.setContentProvider(new SegmentSplitContentProvider());
    viewer.setLabelProvider(new SegmentSplitLabelProvider());
    viewer.setInput(segmentText);

    Table table = viewer.getTable();
    table.setLayoutData(new GridData(GridData.FILL_BOTH));
    table.setLinesVisible(true);
    table.setHeaderVisible(true);
    TableColumn tc1 = new TableColumn(table, SWT.RIGHT);
    tc1.setText("Index");
    tc1.pack();
    TableColumn tc2 = new TableColumn(table, SWT.LEFT);
    tc2.setText("Value");
    tc2.pack();

    clipboard = new Clipboard(parent.getDisplay());
    createClipboardPollingThread(parent.getDisplay());
  }

  private void createClipboardPollingThread(final Display display) {
    Runnable runnable = new Runnable() {
      public void run() {
        for (;;) {
          try {
            display.asyncExec(new Runnable() {
              public void run() {
                try {
                  TransferData[] td = clipboard.getAvailableTypes();
                  for (int i = 0, n = td.length; i < n; i++) {
                    if (TextTransfer.getInstance().isSupportedType(td[i])) {
                      String string = (String) clipboard.getContents(TextTransfer
                          .getInstance());
                      if (string != null && string.length() > 0
                          && !string.equals(segmentText.getText())
                          && string.indexOf('*') != -1) {
                        segmentText.setText(string);
                        viewer.refresh();
                      }
                      break;
                    }
                  }
                } catch (SWTException e) {
                }
              }
            });
          } catch (SWTException e) {
          }
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
          }
        }
      }
    };
    new Thread(runnable).start();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  public void setFocus() {
    viewer.getControl().setFocus();
  }
}