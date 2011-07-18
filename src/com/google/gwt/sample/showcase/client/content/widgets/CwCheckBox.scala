/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.sample.showcase.client.content.widgets;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.sample.showcase.client.ContentWidget;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

object CwCheckBox {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwCheckBoxCheckAll(): String

    def cwCheckBoxDays(): Array[String]

    def cwCheckBoxDescription(): String

    def cwCheckBoxName(): String
  }
}

/**
 * Example file.
 */
@ShowcaseStyle(value = Array(".gwt-CheckBox"))
class CwCheckBox(constants: CwCheckBox.CwConstants) extends ContentWidget(constants) {

  override def getDescription() = constants.cwCheckBoxDescription

  override def getName() = constants.cwCheckBoxName

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  override def onInitialize(): Widget = {
    // Create a vertical panel to align the check boxes
    val vPanel = new VerticalPanel()
    val label = new HTML(constants.cwCheckBoxCheckAll)
    label.ensureDebugId("cwCheckBox-label")
    vPanel.add(label)

    // Add a checkbox for each day of the week
    val daysOfWeek = constants.cwCheckBoxDays
    for (i <- 0 until daysOfWeek.length) {
      val day = daysOfWeek(i)
      val checkBox = new CheckBox(day)
      checkBox.ensureDebugId("cwCheckBox-" + day)
      // Disable the weekends
      if (i >= 5) {
        checkBox.setEnabled(false)
      }
      vPanel.add(checkBox)
    }

    // Return the panel of checkboxes
    return vPanel
  }

  override protected def asyncOnInitialize(callback: AsyncCallback[Widget]) = {
    /*
     * CheckBox is the first demo loaded, so go ahead and load it synchronously.
     */
    callback.onSuccess(onInitialize())
  }
}
