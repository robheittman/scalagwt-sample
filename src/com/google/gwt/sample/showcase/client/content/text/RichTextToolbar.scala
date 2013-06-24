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
import com.google.gwt.event.dom.client.ChangeEvent
import com.google.gwt.event.dom.client.ChangeHandler
import com.google.gwt.event.dom.client.ClickEvent
import com.google.gwt.event.dom.client.ClickHandler
import com.google.gwt.event.dom.client.KeyUpEvent
import com.google.gwt.event.dom.client.KeyUpHandler
import com.google.gwt.i18n.client.Constants
import com.google.gwt.resources.client.ClientBundle
import com.google.gwt.resources.client.ImageResource
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.Image
import com.google.gwt.user.client.ui.ListBox
import com.google.gwt.user.client.ui.Panel
import com.google.gwt.user.client.ui.PushButton
import com.google.gwt.user.client.ui.RichTextArea
import com.google.gwt.user.client.ui.ToggleButton
import com.google.gwt.user.client.ui.VerticalPanel
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.user.client.ui.IsWidget

object RichTextToolbar {
  

  private val fontSizesConstants = Array(
      RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
      RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
      RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
      RichTextArea.FontSize.XX_LARGE)

  /**
   * This {@link ClientBundle} is used for all the button icons. Using a bundle
   * allows all of these images to be packed into a single image, which saves a
   * lot of HTTP requests, drastically improving startup time.
   */
  trait Images extends ClientBundle {
    def bold(): ImageResource

    def createLink(): ImageResource

    def hr(): ImageResource

    def indent(): ImageResource

    def insertImage(): ImageResource

    def italic(): ImageResource

    def justifyCenter(): ImageResource

    def justifyLeft(): ImageResource

    def justifyRight(): ImageResource

    def ol(): ImageResource

    def outdent(): ImageResource

    def removeFormat(): ImageResource

    def removeLink(): ImageResource

    def strikeThrough(): ImageResource

    def subscript(): ImageResource

    def superscript(): ImageResource

    def ul(): ImageResource

    def underline(): ImageResource
  }

  /**
   * This {@link Constants} interface is used to make the toolbar's strings
   * internationalizable.
   */
  trait Strings extends Constants {
    def black(): String

    def blue(): String

    def bold(): String

    def color(): String

    def createLink(): String

    def font(): String

    def green(): String

    def hr(): String

    def indent(): String

    def insertImage(): String

    def italic(): String

    def justifyCenter(): String

    def justifyLeft(): String

    def justifyRight(): String

    def large(): String

    def medium(): String

    def normal(): String

    def ol(): String

    def outdent(): String

    def red(): String
    
    def removeFormat(): String
    
    def removeLink(): String

    def size(): String

    def small(): String

    def strikeThrough(): String

    def subscript(): String

    def superscript(): String

    def ul(): String

    def underline(): String

    def white(): String

    def xlarge(): String

    def xsmall(): String

    def xxlarge(): String

    def xxsmall(): String

    def yellow(): String
  }
}
/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
class RichTextToolbar(richText: RichTextArea) extends Composite {

  import RichTextToolbar._

  /**
   * We use an inner EventHandler class to avoid exposing event methods on the
   * RichTextToolbar itself.
   */
  class EventHandler extends ClickHandler with ChangeHandler with KeyUpHandler {
    def onChange(event: ChangeEvent) {
      val sender = event.getSource.asInstanceOf[Widget]
      if (sender == backColors) {
        basic.setBackColor(backColors.getValue(backColors.getSelectedIndex))
        backColors.setSelectedIndex(0)
      } else if (sender == foreColors) {
        basic.setForeColor(foreColors.getValue(foreColors.getSelectedIndex))
        foreColors.setSelectedIndex(0)
      } else if (sender == fonts) {
        basic.setFontName(fonts.getValue(fonts.getSelectedIndex))
        fonts.setSelectedIndex(0)
      } else if (sender == fontSizes) {
        basic.setFontSize(fontSizesConstants(fontSizes.getSelectedIndex - 1))
        fontSizes.setSelectedIndex(0)
      }
    }

    def onClick(event: ClickEvent) {
      val sender = event.getSource.asInstanceOf[Widget]
      if (sender == bold) {
        basic.toggleBold()
      } else if (sender == italic) {
        basic.toggleItalic()
      } else if (sender == underline) {
        basic.toggleUnderline()
      } else if (sender == subscript) {
        basic.toggleSubscript()
      } else if (sender == superscript) {
        basic.toggleSuperscript()
      } else if (sender == strikethrough) {
        extended.toggleStrikethrough()
      } else if (sender == indent) {
        extended.rightIndent()
      } else if (sender == outdent) {
        extended.leftIndent()
      } else if (sender == justifyLeft) {
        basic.setJustification(RichTextArea.Justification.LEFT)
      } else if (sender == justifyCenter) {
        basic.setJustification(RichTextArea.Justification.CENTER)
      } else if (sender == justifyRight) {
        basic.setJustification(RichTextArea.Justification.RIGHT)
      } else if (sender == insertImage) {
        val url = Window.prompt("Enter an image URL:", "http://")
        if (url != null) {
          extended.insertImage(url)
        }
      } else if (sender == createLink) {
        val url = Window.prompt("Enter a link URL:", "http://")
        if (url != null) {
          extended.createLink(url)
        }
      } else if (sender == removeLink) {
        extended.removeLink()
      } else if (sender == hr) {
        extended.insertHorizontalRule()
      } else if (sender == ol) {
        extended.insertOrderedList()
      } else if (sender == ul) {
        extended.insertUnorderedList()
      } else if (sender == removeFormat) {
        extended.removeFormat()
      } else if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus()
      }
    }

    def onKeyUp(event: KeyUpEvent) {
      val sender = event.getSource.asInstanceOf[Widget]
      if (sender == richText) {
        // We use the RichTextArea's onKeyUp event to update the toolbar status.
        // This will catch any cases where the user moves the cursur using the
        // keyboard, or uses one of the browser's built-in keyboard shortcuts.
        updateStatus()
      }
    }
  }

  private val images = GWT.create(classOf[Images]).asInstanceOf[Images]
  private val strings = GWT.create(classOf[Strings]).asInstanceOf[Strings]
  private val handler = new EventHandler()

  private val basic = richText.getBasicFormatter
  private val extended = richText.getExtendedFormatter

  private val outer = new VerticalPanel()
  private val topPanel = new HorizontalPanel()
  private val bottomPanel = new HorizontalPanel()
  private var bold: ToggleButton = null
  private var italic: ToggleButton = null
  private var underline: ToggleButton = null
  private var subscript: ToggleButton = null
  private var superscript: ToggleButton = null
  private var strikethrough: ToggleButton = null
  private var indent: PushButton = null
  private var outdent: PushButton = null
  private var justifyLeft: PushButton = null
  private var justifyCenter: PushButton = null
  private var justifyRight: PushButton = null
  private var hr: PushButton = null
  private var ol: PushButton = null
  private var ul: PushButton = null
  private var insertImage: PushButton = null
  private var createLink: PushButton = null
  private var removeLink: PushButton = null
  private var removeFormat: PushButton = null

  private var backColors: ListBox = null
  private var foreColors: ListBox = null
  private var fonts: ListBox = null
  private var fontSizes: ListBox = null

  outer.add(topPanel)
  outer.add(bottomPanel)
  topPanel.setWidth("100%")
  bottomPanel.setWidth("100%")

  initWidget(outer)
  setStyleName("gwt-RichTextToolbar")
  richText.addStyleName("hasRichTextToolbar")

  private def add[W <: Widget](p: Panel, w: W): W = { p.add(w) ; w }
  if (basic != null) {
    bold = add(topPanel, createToggleButton(images.bold, strings.bold))
    italic = add(topPanel, createToggleButton(images.italic, strings.italic))
    underline = add(topPanel, createToggleButton(images.underline, strings.underline))
    subscript = add(topPanel, createToggleButton(images.subscript, strings.subscript))
    superscript = add(topPanel, createToggleButton(images.superscript, strings.superscript))
    justifyLeft = add(topPanel, createPushButton(images.justifyLeft, strings.justifyLeft))
    justifyCenter = add(topPanel, createPushButton(images.justifyCenter, strings.justifyCenter))
    justifyRight = add(topPanel, createPushButton(images.justifyRight, strings.justifyRight))
  }
  if (extended != null) {
    strikethrough = add(topPanel, createToggleButton(images.strikeThrough, strings.strikeThrough))
    indent = add(topPanel, createPushButton(images.indent, strings.indent))
    outdent = add(topPanel, createPushButton(images.outdent, strings.outdent))
    hr = add(topPanel, createPushButton(images.hr, strings.hr))
    ol = add(topPanel, createPushButton(images.ol, strings.ol))
    ul = add(topPanel, createPushButton(images.ul, strings.ul))
    insertImage = add(topPanel, createPushButton(images.insertImage, strings.insertImage))
    createLink = add(topPanel, createPushButton(images.createLink, strings.createLink))
    removeLink = add(topPanel, createPushButton(images.removeLink, strings.removeLink))
    removeFormat = add(topPanel, createPushButton(images.removeFormat, strings.removeFormat))
  }
  if (basic != null) {
    backColors = add(bottomPanel, createColorList("Background"))
    foreColors = add(bottomPanel, createColorList("Foreground"))
    fonts = add(bottomPanel, createFontList())
    fontSizes = add(bottomPanel, createFontSizes())
    // We only use these handlers for updating status, so don't hook them up
    // unless at least basic editing is supported.
    richText.addKeyUpHandler(handler)
    richText.addClickHandler(handler)
  }

  private def createColorList(caption: String) = {
    val lb = new ListBox()
    lb.addChangeHandler(handler)
    lb.setVisibleItemCount(1)
    lb.addItem(caption)
    lb.addItem(strings.white, "white")
    lb.addItem(strings.black, "black")
    lb.addItem(strings.red, "red")
    lb.addItem(strings.green, "green")
    lb.addItem(strings.yellow, "yellow")
    lb.addItem(strings.blue, "blue")
    lb
  }

  private def createFontList() = {
    val lb = new ListBox()
    lb.addChangeHandler(handler)
    lb.setVisibleItemCount(1)
    lb.addItem(strings.font, "")
    lb.addItem(strings.normal, "")
    lb.addItem("Times New Roman", "Times New Roman")
    lb.addItem("Arial", "Arial")
    lb.addItem("Courier New", "Courier New")
    lb.addItem("Georgia", "Georgia")
    lb.addItem("Trebuchet", "Trebuchet")
    lb.addItem("Verdana", "Verdana")
    lb
  }

  private def createFontSizes() = {
    val lb = new ListBox()
    lb.addChangeHandler(handler)
    lb.setVisibleItemCount(1)
    lb.addItem(strings.size)
    lb.addItem(strings.xxsmall)
    lb.addItem(strings.xsmall)
    lb.addItem(strings.small)
    lb.addItem(strings.medium)
    lb.addItem(strings.large)
    lb.addItem(strings.xlarge)
    lb.addItem(strings.xxlarge)
    lb
  }

  private def createPushButton(img: ImageResource, tip: String) = {
    val pb = new PushButton(new Image(img))
    pb.addClickHandler(handler)
    pb.setTitle(tip)
    pb
  }

  private def createToggleButton(img: ImageResource, tip: String) = {
    val tb = new ToggleButton(new Image(img))
    tb.addClickHandler(handler)
    tb.setTitle(tip)
    tb
  }

  /**
   * Updates the status of all the stateful buttons.
   */
  private def updateStatus() {
    if (basic != null) {
      bold.setDown(basic.isBold)
      italic.setDown(basic.isItalic)
      underline.setDown(basic.isUnderlined)
      subscript.setDown(basic.isSubscript)
      superscript.setDown(basic.isSuperscript)
    }
    if (extended != null) {
      strikethrough.setDown(extended.isStrikethrough)
    }
  }
}
