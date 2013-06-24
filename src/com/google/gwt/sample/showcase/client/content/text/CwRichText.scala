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
package com.google.gwt.sample.showcase.client.content.text

import com.google.gwt.core.client.GWT
import com.google.gwt.core.client.RunAsyncCallback
import com.google.gwt.i18n.client.Constants
import com.google.gwt.sample.showcase.client.ContentWidget
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.Grid
import com.google.gwt.user.client.ui.RichTextArea
import com.google.gwt.user.client.ui.Widget

object CwRichText {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwRichTextDescription(): String

    def cwRichTextName(): String
  }
}

/**
 * Example file.
 */
@ShowcaseStyle(Array(
    ".gwt-RichTextArea", ".hasRichTextToolbar", ".gwt-RichTextToolbar",
    ".cw-RichText"))
class CwRichText(constants: CwRichText.CwConstants) extends ContentWidget(constants) {

  override def getDescription() = constants.cwRichTextDescription

  override def getName() = constants.cwRichTextName

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  override def onInitialize(): Widget = {
    // Create the text area and toolbar
    val area = new RichTextArea()
    area.ensureDebugId("cwRichText-area")
    area.setSize("100%", "14em")
    val toolbar = new RichTextToolbar(area)
    toolbar.ensureDebugId("cwRichText-toolbar")
    toolbar.setWidth("100%")

    // Add the components to a panel
    val grid = new Grid(2, 1)
    grid.setStyleName("cw-RichText")
    grid.setWidget(0, 0, toolbar)
    grid.setWidget(1, 0, area)
    return grid
  }

  override def asyncOnInitialize(callback: AsyncCallback[Widget]) = {
    GWT.runAsync(new RunAsyncCallback() {
      def onFailure(caught: Throwable) = callback.onFailure(caught)

      def onSuccess() = callback.onSuccess(onInitialize())
    })
  }
}
