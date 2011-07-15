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


package com.google.gwt.sample.showcase.client.content.lists

import com.google.gwt.core.client.GWT
import com.google.gwt.core.client.RunAsyncCallback
import com.google.gwt.i18n.client.Constants
import com.google.gwt.sample.showcase.client.ContentWidget
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.HTML
import com.google.gwt.user.client.ui.MultiWordSuggestOracle
import com.google.gwt.user.client.ui.SuggestBox
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.Widget

/**
 * Example file.
 */
object CwSuggestBox {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwSuggestBoxWords: Array[String]

    def cwSuggestBoxDescription: String

    def cwSuggestBoxName: String

    def cwSuggestBoxLabel: String
  }
}

@ShowcaseStyle(Array(
    ".gwt-SuggestBox", ".gwt-SuggestBoxPopup",
    "html>body .gwt-SuggestBoxPopup", "* html .gwt-SuggestBoxPopup"))
class CwSuggestBox(@ShowcaseData private val constants: CwSuggestBox.CwConstants)
      extends ContentWidget(constants) {

  def getDescription: String = constants.cwSuggestBoxDescription

  def getName: String = constants.cwSuggestBoxName

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  def onInitialize: Widget = {
    // Define the oracle that finds suggestions
    val oracle = new MultiWordSuggestOracle
    val words = constants.cwSuggestBoxWords
    words foreach { oracle.add(_) }

    // Create the suggest box
    val suggestBox = new SuggestBox(oracle)
    suggestBox.ensureDebugId("cwSuggestBox")
    val suggestPanel = new VerticalPanel
    suggestPanel.add(new HTML(constants.cwSuggestBoxLabel))
    suggestPanel.add(suggestBox)

    // Return the panel
    suggestPanel
  }

  protected[client] def asyncOnInitialize(callback: AsyncCallback[Widget]): Unit = {
    GWT.runAsync(new RunAsyncCallback {
      def onFailure(caught: Throwable): Unit = {
        callback.onFailure(caught)
      }

      def onSuccess: Unit = {
        callback.onSuccess(onInitialize)
      }
    })
  }
}
