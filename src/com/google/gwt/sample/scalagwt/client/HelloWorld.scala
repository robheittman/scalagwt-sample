package com.google.gwt.sample.scalagwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

class HelloWorld extends EntryPoint {

  def onModuleLoad() {
    val b = new Button("Click me", new ClickHandler() {
      def onClick(event: ClickEvent) {
        Window.alert("Hello, AJAX");
      }
    });

    RootPanel.get().add(b);
  }
}
