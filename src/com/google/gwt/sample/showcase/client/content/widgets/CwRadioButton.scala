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
package com.google.gwt.sample.showcase.client.content.widgets

import com.google.gwt.core.client.GWT
import com.google.gwt.core.client.RunAsyncCallback
import com.google.gwt.i18n.client.Constants
import com.google.gwt.sample.showcase.client.ContentWidget
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.HTML
import com.google.gwt.user.client.ui.RadioButton
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.Widget

object CwRadioButton {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwRadioButtonColors(): Array[String]

    def cwRadioButtonDescription(): String

    def cwRadioButtonName(): String

    def cwRadioButtonSelectColor(): String

    def cwRadioButtonSelectSport(): String

    def cwRadioButtonSports(): Array[String]
  }
}

/**
 * Example file.
 */
@ShowcaseStyle(Array(".gwt-RadioButton"))
class CwRadioButton(constants: CwRadioButton.CwConstants) extends ContentWidget(constants) {

  override def getDescription() = constants.cwRadioButtonDescription

  override def getName() = constants.cwRadioButtonName

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  override def onInitialize(): Widget = {
    // Create a vertical panel to align the radio buttons
    val vPanel = new VerticalPanel()
    vPanel.add(new HTML(constants.cwRadioButtonSelectColor))

    // Add some radio buttons to a group called 'color'
    val colors = constants.cwRadioButtonColors
    for (i <- 0 until colors.length) {
      val color = colors(i)
      val radioButton = new RadioButton("color", color)
      radioButton.ensureDebugId("cwRadioButton-color-" + color)
      if (i == 2) {
        radioButton.setEnabled(false)
      }
      vPanel.add(radioButton)
    }

    // Add a new header to select your favorite sport
    vPanel.add(new HTML("<br>" + constants.cwRadioButtonSelectSport))

    // Add some radio buttons to a group called 'sport'
    val sports = constants.cwRadioButtonSports
    for (i <- 0 until sports.length) {
      val sport = sports(i)
      val radioButton = new RadioButton("sport", sport)
      radioButton.ensureDebugId("cwRadioButton-sport-" + sport.replaceAll(" ", ""))
      if (i == 2) {
        radioButton.setValue(true)
      }
      vPanel.add(radioButton)
    }

    return vPanel
  }

  override def asyncOnInitialize(callback: AsyncCallback[Widget]) = {
    GWT.runAsync(new RunAsyncCallback() {
      def onFailure(caught: Throwable) = callback.onFailure(caught)

      def onSuccess() = callback.onSuccess(onInitialize())
    })
  }
}
