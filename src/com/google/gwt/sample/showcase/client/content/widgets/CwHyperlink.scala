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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.sample.showcase.client.ContentWidget;
import com.google.gwt.sample.showcase.client.ShowcaseConstants;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource;
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

object CwHyperlink {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwHyperlinkChoose(): String

    def cwHyperlinkDescription(): String

    def cwHyperlinkName(): String
  }
}

/**
 * Example file.
 */
@ShowcaseStyle(value = Array(".gwt-Hyperlink"))
class CwHyperlink(constants: CwHyperlink.CwConstants) extends ContentWidget(constants) {

  override def getDescription() = constants.cwHyperlinkDescription()

  override def getName() = constants.cwHyperlinkName()

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  override def onInitialize(): Widget = {
    // Add a label
    val vPanel = new VerticalPanel()
    vPanel.add(new HTML(constants.cwHyperlinkChoose()))
    vPanel.setSpacing(5)

    // Add a hyper link to each section in the Widgets category
    val allConstants = constants.asInstanceOf[ShowcaseConstants]
    vPanel.add(getHyperlink(classOf[CwCheckBox], allConstants.cwCheckBoxName))
    vPanel.add(getHyperlink(classOf[CwRadioButton], allConstants.cwRadioButtonName))
    vPanel.add(getHyperlink(classOf[CwBasicButton], allConstants.cwBasicButtonName))
    vPanel.add(getHyperlink(classOf[CwCustomButton], allConstants.cwCustomButtonName))
    vPanel.add(getHyperlink(classOf[CwFileUpload], allConstants.cwFileUploadName))
    vPanel.add(getHyperlink(classOf[CwDatePicker], allConstants.cwDatePickerName))

    // Return the panel
    return vPanel
  }

  override protected def asyncOnInitialize(callback: AsyncCallback[Widget]) = {
    GWT.runAsync(new RunAsyncCallback() {
      def onFailure(caught: Throwable) {
        callback.onFailure(caught)
      }

      def onSuccess() {
        callback.onSuccess(onInitialize())
      }
    })
  }

  /**
   * Get a {@link Hyperlink} to a section based on the name of the
   * {@link ContentWidget} example.
   * 
   * @param cwClass the {@link ContentWidget} class
   * @param name the name to display for the link
   * @return a {@link Hyperlink}
   */
  private def getHyperlink(cwClass: Class[_], name: String): Hyperlink = {
    // Get the class name of the content widget
    var className = cwClass.getName()
    className = className.substring(className.lastIndexOf('.') + 1)

    // Convert to a hyper link
    val link = new Hyperlink(name, className)
    link.ensureDebugId("cwHyperlink-" + className)
    return link
  }
}
