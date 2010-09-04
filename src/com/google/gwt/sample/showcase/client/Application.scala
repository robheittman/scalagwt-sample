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


package com.google.gwt.sample.showcase.client


import com.google.gwt.core.client.GWT
import com.google.gwt.event.logical.shared.HasSelectionHandlers
import com.google.gwt.event.logical.shared.ResizeEvent
import com.google.gwt.event.logical.shared.ResizeHandler
import com.google.gwt.event.logical.shared.SelectionHandler
import com.google.gwt.event.shared.HandlerRegistration
import com.google.gwt.i18n.client.LocaleInfo
import com.google.gwt.resources.client.ImageResource
import com.google.gwt.user.client.Window
import com.google.gwt.user.client.ui.Composite
import com.google.gwt.user.client.ui.DecoratorPanel
import com.google.gwt.user.client.ui.FlexTable
import com.google.gwt.user.client.ui.FlowPanel
import com.google.gwt.user.client.ui.Grid
import com.google.gwt.user.client.ui.HTML
import com.google.gwt.user.client.ui.HasHorizontalAlignment
import com.google.gwt.user.client.ui.HasVerticalAlignment
import com.google.gwt.user.client.ui.HorizontalPanel
import com.google.gwt.user.client.ui.SimplePanel
import com.google.gwt.user.client.ui.Tree
import com.google.gwt.user.client.ui.TreeItem
import com.google.gwt.user.client.ui.Widget
import com.google.gwt.resources.client.ClientBundle.Source


/**
 * <p>
 * A generic application that includes a title bar, main menu, content area, and
 * some external links at the top.
 * </p>
 * <h3>CSS Style Rules</h3>
 *
 * <ul class="css">
 *
 * <li>.Application  { Applied to the entire Application } </li>
 *
 * <li>.Application-top  { The top portion of the Application } </li>
 *
 * <li>.Application-title  { The title widget } </li>
 *
 * <li>.Application-links  { The main external links } </li>
 *
 * <li>.Application-options  { The options widget } </li>
 *
 * <li>.Application-menu  { The main menu } </li>
 *
 * <li>.Application-content-wrapper  { The scrollable element around the content } </li>
 *
 * </ul>
 */
object Application {
   /**
    * Images used in the  { @link Application }.
    */
   trait ApplicationImages extends Tree.Resources {
      /**
       * An image indicating a leaf.
       *
       * @return a prototype of this image
       */
      @Source(Array("noimage.png"))
      def treeLeaf: ImageResource
   }

   /**
    * The base style name.
    */
   val DEFAULT_STYLE_NAME = "Application"
}
class Application extends Composite with ResizeHandler with HasSelectionHandlers[TreeItem] {
   import Application._

   /**
    * The panel that contains the menu and content.
    */
   private[this] val bottomPanel = new HorizontalPanel

   /**
    * The decorator around the content.
    */
   private[this] val contentDecorator = new DecoratorPanel

   /**
    * The main wrapper around the content and content title.
    */
   private[this] val contentLayout = new Grid(2,1)

   /**
    * The wrapper around the content.
    */
   private[this] val contentWrapper = new SimplePanel

   /**
    * The panel that holds the main links.
    */
   private[this] val linksPanel = new HorizontalPanel

   /**
    * The main menu.
    */
   private[this] val mainMenu = new Tree(GWT.create(classOf[ApplicationImages]).asInstanceOf[ApplicationImages])

   /**
    * The last known width of the window.
    */
   private[this] var windowWidth: Int = -1

   /**
    * The panel that contains the title widget and links.
    */
   private[this] val topPanel = new FlexTable

   // Setup the main layout widget
   private[this] val layout = new FlowPanel
   initWidget(layout)

   // Setup the top panel with the title and links
   createTopPanel
   layout.add(topPanel)

   // Add the main menu
   bottomPanel.setWidth("100%")
   bottomPanel.setSpacing(0)
   bottomPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP)
   layout.add(bottomPanel)
   createMainMenu
   bottomPanel.add(mainMenu)

   // Setup the content layout
   contentLayout.setCellPadding(0)
   contentLayout.setCellSpacing(0)

   contentDecorator.setWidget(contentLayout)
   contentDecorator.addStyleName(DEFAULT_STYLE_NAME + "-content-decorator")
   bottomPanel.add(contentDecorator)
   if (LocaleInfo.getCurrentLocale.isRTL) {
      bottomPanel.setCellHorizontalAlignment(contentDecorator, HasHorizontalAlignment.ALIGN_LEFT)
      contentDecorator.getElement.setAttribute("align", "LEFT")
   }
   else {
      bottomPanel.setCellHorizontalAlignment(contentDecorator, HasHorizontalAlignment.ALIGN_RIGHT)
      contentDecorator.getElement.setAttribute("align", "RIGHT")
   }
   private[this] val formatter = contentLayout.getCellFormatter

   // Add the content title
   setContentTitle(new HTML("Content"))
   formatter.setStyleName(0, 0, DEFAULT_STYLE_NAME + "-content-title")

   // Add the content wrapper
   contentLayout.setWidget(1, 0, contentWrapper)
   formatter.setStyleName(1, 0, DEFAULT_STYLE_NAME + "-content-wrapper")
   setContent(null)

   // Add a window resize handler
   Window.addResizeHandler(this)

   /**
    * Add a link to the top of the page.
    *
    * @param link the widget to add to the mainLinks
    */
   def addLink(link: Widget): Unit = {
      if (linksPanel.getWidgetCount > 0) {
         linksPanel.add(new HTML("&nbsp;|&nbsp;"))
      }
      linksPanel.add(link)
   }

   def addSelectionHandler(handler: SelectionHandler[TreeItem]): HandlerRegistration =
      mainMenu.addSelectionHandler(handler)

   /**
    * @return the {@link Widget} in the content area
    */
   def getContent: Widget = contentWrapper.getWidget

   /**
    * @return the content title widget
    */
   def getContentTitle: Widget = contentLayout.getWidget(0, 0)

   /**
    * @return the main menu.
    */
   def getMainMenu: Tree = mainMenu

   /**
    * @return the {@link Widget} used as the title
    */
   def getTitleWidget: Widget = topPanel.getWidget(0, 0)

   def onResize(event: ResizeEvent): Unit = onWindowResized(event.getWidth, event.getHeight)

   def onWindowResized(width: Int, height: Int): Unit = {
      if (width == windowWidth || width < 1) {
         return
      }
      windowWidth = width
      onWindowResizedImpl(width)
   }

   /**
    * Set the {@link Widget} to display in the content area.
    *
    * @param content the content widget
    */
   def setContent(content: Widget): Unit = {
      if (content == null) {
         contentWrapper.setWidget(new HTML("&nbsp;"))
      } else {
         contentWrapper.setWidget(content)
      }
   }

   /**
    * Set the title of the content area.
    *
    * @param title the content area title
    */
   def setContentTitle(title: Widget): Unit = contentLayout.setWidget(0, 0, title)

   /**
    * Set the {@link Widget} to use as options, which appear to the right of the
    * title bar.
    *
    * @param options the options widget
    */
   def setOptionsWidget(options: Widget): Unit = topPanel.setWidget(1, 1, options)

   /**
    * Set the {@link Widget} to use as the title bar.
    *
    * @param title the title widget
    */
   def setTitleWidget(title: Widget): Unit = topPanel.setWidget(1, 0, title)

   protected override def onLoad: Unit = {
      super.onLoad
      onWindowResized(Window.getClientWidth, Window.getClientHeight)
   }

   protected override def onUnload: Unit = {
      super.onUnload
      windowWidth = -1
   }

   protected def onWindowResizedImpl(width: Int): Unit = {
      val menuWidth = mainMenu.getOffsetWidth
      val contentWidth = math.max(width - menuWidth - 30, 1)
      val contentWidthInner = math.max(contentWidth - 10, 1)
      bottomPanel.setCellWidth(mainMenu, menuWidth + "px")
      bottomPanel.setCellWidth(contentDecorator, contentWidth + "px")
      contentLayout.getCellFormatter.setWidth(0, 0, contentWidthInner + "px")
      contentLayout.getCellFormatter.setWidth(1, 0, contentWidthInner + "px")
   }

   /**
    * Create the main menu.
    */
   private def createMainMenu: Unit = {
      // Setup the main menu
      mainMenu.setAnimationEnabled(true)
      mainMenu.addStyleName(DEFAULT_STYLE_NAME + "-menu")
      mainMenu
   }

   /**
    * Create the panel at the top of the page that contains the title and links.
    */
   private def createTopPanel: Unit = {
      val isRTL = LocaleInfo.getCurrentLocale.isRTL
      topPanel.setCellPadding(0)
      topPanel.setCellSpacing(0)
      topPanel.setStyleName(DEFAULT_STYLE_NAME + "-top")
      var formatter = topPanel.getFlexCellFormatter

      // Setup the links cell
      topPanel.setWidget(0, 0, linksPanel)
      formatter.setStyleName(0, 0, DEFAULT_STYLE_NAME + "-links")
      if (isRTL) {
         formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT)
      }
      else {
         formatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT)
      }
      formatter.setColSpan(0, 0, 2)

      // Setup the title cell
      setTitleWidget(null)
      formatter.setStyleName(1, 0, DEFAULT_STYLE_NAME + "-title")

      // Setup the options cell
      setOptionsWidget(null)
      formatter.setStyleName(1, 1, DEFAULT_STYLE_NAME + "-options")
      if (isRTL) {
         formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_LEFT)
      }
      else {
         formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT)
      }

      // Align the content to the top
      topPanel.getRowFormatter.setVerticalAlign(0, HasVerticalAlignment.ALIGN_TOP)
      topPanel.getRowFormatter.setVerticalAlign(1, HasVerticalAlignment.ALIGN_TOP)
   }
}

