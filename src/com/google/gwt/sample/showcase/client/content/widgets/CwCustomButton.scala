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
import com.google.gwt.sample.showcase.client.Showcase
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseData
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseSource
import com.google.gwt.sample.showcase.client.ShowcaseAnnotations.ShowcaseStyle
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.Image
import com.google.gwt.user.client.ui.PushButton
import com.google.gwt.user.client.ui.ToggleButton
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.Widget

object CwCustomButton {
  /**
   * The constants used in this Content Widget.
   */
  @ShowcaseSource
  trait CwConstants extends Constants with ContentWidget.CwConstants {
    def cwCustomButtonDescription(): String

    def cwCustomButtonName(): String

    def cwCustomButtonPush(): String

    def cwCustomButtonToggle(): String
  }
}

/**
 * Example file.
 */
@ShowcaseStyle(Array(".gwt-PushButton", ".gwt-ToggleButton"))
class CwCustomButton(constants: CwCustomButton.CwConstants) extends ContentWidget(constants) {

  override def getDescription() = constants.cwCustomButtonDescription

  override def getName() = constants.cwCustomButtonName

  /**
   * Initialize this example.
   */
  @ShowcaseSource
  override def onInitialize(): Widget = {
    // Create a panel to layout the widgets
    val vpanel = new VerticalPanel()
    val pushPanel = new HorizontalPanel()
    pushPanel.setSpacing(10)
    val togglePanel = new HorizontalPanel()
    togglePanel.setSpacing(10)

    // Combine all the panels
    vpanel.add(new HTML(constants.cwCustomButtonPush))
    vpanel.add(pushPanel)
    vpanel.add(new HTML("<br><br>" + constants.cwCustomButtonToggle))
    vpanel.add(togglePanel)

    // Add a normal PushButton
    val normalPushButton = new PushButton(
        new Image(Showcase.images.gwtLogo()))
    normalPushButton.ensureDebugId("cwCustomButton-push-normal")
    pushPanel.add(normalPushButton)

    // Add a disabled PushButton
    val disabledPushButton = new PushButton(
        new Image(Showcase.images.gwtLogo()))
    disabledPushButton.ensureDebugId("cwCustomButton-push-disabled")
    disabledPushButton.setEnabled(false)
    pushPanel.add(disabledPushButton)

    // Add a normal ToggleButton
    val normalToggleButton = new ToggleButton(
        new Image(Showcase.images.gwtLogo()))
    normalToggleButton.ensureDebugId("cwCustomButton-toggle-normal")
    togglePanel.add(normalToggleButton)

    // Add a disabled ToggleButton
    val disabledToggleButton = new ToggleButton(
        new Image(Showcase.images.gwtLogo()));
    disabledToggleButton.ensureDebugId("cwCustomButton-toggle-disabled")
    disabledToggleButton.setEnabled(false)
    togglePanel.add(disabledToggleButton)

    // Return the panel
    return vpanel
  }

  override def asyncOnInitialize(callback: AsyncCallback[Widget]) = {
    GWT.runAsync(new RunAsyncCallback() {
      def onFailure(caught: Throwable) = callback.onFailure(caught)

      def onSuccess() = callback.onSuccess(onInitialize())
    })
  }
}
